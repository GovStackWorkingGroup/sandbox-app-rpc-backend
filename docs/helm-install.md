# Helm Installation

## Debug chart

Helm Upgrade command for Govstack sandbox
```
    helm upgrade rpc-backend ./helm --install --create-namespace --namespace rpc-backend --dry-run --debug
```

## Install chart

```
    helm upgrade rpc-backend ./helm --install --create-namespace --namespace rpc-backend
```

## Install chart with persisted database state

Note: The DB state will not be flushed on every pod restart
```
    helm upgrade --set rpc_backend.dbPersist.flushStorageOnInit=false rpc-backend ./helm --install --create-namespace --namespace rpc-backend
```

## Install chart with no persisted database state

Useful for development purposes
Note: The DB state will be flushed on every pod restart
```
    helm upgrade --set rpc_backend.dbPersist.flushStorageOnInit=true rpc-backend ./helm --install --create-namespace --namespace rpc-backend
```

## Uninstall chart

Uninstall chart
```
    helm uninstall rpc-backend --namespace rpc-backend
```