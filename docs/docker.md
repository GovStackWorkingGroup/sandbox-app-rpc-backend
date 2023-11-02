# DOCKER
## Building docker image:
`./gradlew bootBuildImage`

## Docker Run:
`docker run --name  prcBackend -p 15000:8080 -d rpcbackend:0.0.1`