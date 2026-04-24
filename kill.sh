#!/usr/bin/env bash
set -e

cd /root/task

echo "=== Stopping and removing containers ==="
docker-compose down -v --rmi all --remove-orphans 2>/dev/null || true

echo "=== Removing any leftover containers ==="
docker ps -aq | xargs -r docker rm -f 2>/dev/null || true

echo "=== Removing all images ==="
docker images -q | xargs -r docker rmi -f 2>/dev/null || true

echo "=== Removing all volumes ==="
docker volume ls -q | xargs -r docker volume rm -f 2>/dev/null || true

echo "=== Pruning system ==="
docker system prune -a --volumes -f 2>/dev/null || true

echo "=== Removing task directory ==="
cd /
rm -rf /root/task

echo "=== Cleanup complete ==="
