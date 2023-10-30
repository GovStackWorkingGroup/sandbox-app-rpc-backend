# Kubernetes Access:

## Port forward to service or specific pod
- pod: rpc-backend-0
- service: rpc-backend-service

## Access swagger:
```
http://localhost:{port-from-port-forward}/swagger-ui/index.html
```

## Swagger Api doc JSON:
```
http://localhost:{port-from-port-forward}/v3/api-docs
```

## Swagger Api doc YAML:
```
http://localhost:{port-from-port-forward}/v3/api-docs.yaml
```

## H2 Console for DB ui:
```
http://localhost:{port-from-port-forward}/h2-console
Use: "jdbc:h2:./dbdata/rpcdata" JDBC URL or modified use your modified URL
```


## Health Check URL-s:
```
http://localhost:{port-from-port-forward}/actuator/health

http://localhost:{port-from-port-forward}/actuator/health/liveness

http://localhost:{port-from-port-forward}/actuator/health/readiness
```