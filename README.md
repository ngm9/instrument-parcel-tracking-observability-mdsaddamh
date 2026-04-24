# Task Overview

You are working with a logistics startup that exposes parcel tracking APIs to multiple e-commerce platforms. The `tracking-service` accepts tracking updates, stores them, publishes events, and relies on a downstream `carrier-service` to enrich the data. As traffic has grown and more services have been added, it has become very difficult to debug production issues because there is no clear end-to-end visibility across HTTP calls, database writes, and message processing.

Operations teams need to quickly understand how a single parcel update flows through the system, which components are slow, and where failures occur. Proper distributed systems architecture and observability are critical so that incidents can be diagnosed quickly, performance bottlenecks can be identified, and changes can be rolled out with confidence.

## Service & Infrastructure Access

| Resource      | Connection                                                      |
|---------------|-----------------------------------------------------------------|
| PostgreSQL    | host `<DROPLET_IP>`, port `5432`, db `trackingdb`, user `tracking`, password `tracking` |
| Kafka Broker  | `<DROPLET_IP>:9092`                                             |
| Redis         | `<DROPLET_IP>:6379` (no auth)                                   |
| RabbitMQ      | `<DROPLET_IP>:5672` (AMQP), `<DROPLET_IP>:15672` (Management UI), guest/guest |
| Jaeger UI     | `http://<DROPLET_IP>:16686`                                     |
| OTel Collector| gRPC `<DROPLET_IP>:4317`, HTTP `<DROPLET_IP>:4318`              |

Internally, services communicate over the Docker network. Kafka is reachable at `kafka:29092` from within containers. PostgreSQL at `postgres:5432`, Redis at `redis:6379`, RabbitMQ at `rabbitmq:5672`.

You can connect to PostgreSQL using any client you prefer (psql, DBeaver, DataGrip). The `parcel_tracking` table is pre-seeded with 10 records.

## Objectives

- Implement the end-to-end tracking flow: accept a tracking update via the REST API, persist it in PostgreSQL, and publish the event to Kafka.
- Ensure that each request can be uniquely identified by a correlation ID that appears consistently in all log entries for that request.
- Provide metrics that allow basic insight into request rates, latency, and failures for the main tracking endpoints.
- Create a fault-tolerant interaction with the downstream `carrier-service` so that its failures do not immediately compromise the entire tracking API.

## How to Verify

- Verify that a tracking update request results in a persisted record, an emitted event, and a subsequent ability to retrieve the updated state via the read API.
- Trigger several tracking updates and confirm that log entries for each request share the same correlation ID and can be clearly followed.
- Confirm that basic metrics (such as request counts, success/failure rates, and latency) are available and change as you exercise the APIs under different scenarios.
- Simulate downstream instability in the `carrier-service` integration and confirm that the system degrades gracefully while still remaining observable and explainable.

## Helpful Tips

- Consider how to generate and propagate a correlation ID from the edge of a request through internal calls so that you can reason about a single request in logs.
- Think about how to structure your logging so that it remains useful when multiple service instances are handling requests concurrently.
- Review how you might design outbound service calls to avoid cascading failures when a dependency becomes slow or unreliable.
- Analyze how correlation IDs and metrics can complement each other to provide a complete picture of system behavior.
