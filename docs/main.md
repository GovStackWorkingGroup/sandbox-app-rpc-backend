# RPC backend application

Backend application for store static data produced from Frontend applications using browser memory as use case database.

## Supported endpoints:
* Swagger Link in Application: `/swagger-ui/index.html`
* Swagger export JSON format: `/v3/api-docs`
* Swagger export YAML format: `/v3/api-docs.yaml`

## Database
* Database console link: `/h2-console/`

## Health endpoints
* General health: `/actuator/health`
* Liveness health: `/actuator/health/liveness`
* Readiness health: `/actuator/health/readiness`

## Navigation
1. [Implementation](./../)
2. [Main](./main.md)
3. [Env vars](./env-vars.md)
4. [Docker](./docker.md)
5. [Helm](./helm-install.md)
6. [Kubernetes](./kubernetes-access.md)
7. [API Endpoints](./api.md)