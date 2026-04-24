#!/usr/bin/env bash
set -e

cd /root/task

echo "=== Pulling images ==="
docker-compose pull

echo "=== Starting services ==="
docker-compose up -d

echo "=== Waiting for PostgreSQL ==="
for i in $(seq 1 30); do
  if docker-compose exec -T postgres pg_isready -U tracking -d trackingdb >/dev/null 2>&1; then
    echo "PostgreSQL is ready"
    break
  fi
  echo "Waiting for PostgreSQL... ($i/30)"
  sleep 2
done

echo "=== Initializing database ==="
docker-compose exec -T postgres psql -U tracking -d trackingdb < /root/task/init_database.sql 2>/dev/null || echo "No init_database.sql or already initialized"

echo "=== Waiting for Kafka ==="
for i in $(seq 1 30); do
  if docker-compose exec -T kafka kafka-topics --bootstrap-server localhost:9092 --list >/dev/null 2>&1; then
    echo "Kafka is ready"
    break
  fi
  echo "Waiting for Kafka... ($i/30)"
  sleep 2
done

echo "=== Waiting for Redis ==="
for i in $(seq 1 15); do
  if docker-compose exec -T redis redis-cli ping 2>/dev/null | grep -q PONG; then
    echo "Redis is ready"
    break
  fi
  echo "Waiting for Redis... ($i/15)"
  sleep 2
done

echo "=== Waiting for RabbitMQ ==="
for i in $(seq 1 20); do
  if docker-compose exec -T rabbitmq rabbitmqctl status >/dev/null 2>&1; then
    echo "RabbitMQ is ready"
    break
  fi
  echo "Waiting for RabbitMQ... ($i/20)"
  sleep 2
done

echo "=== All services started ==="
docker-compose ps
