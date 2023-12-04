# API endpoints

There are two main user roles in the application:

| Role | Purpose                                            | Default       |
|------|----------------------------------------------------|---------------|
| App  | To create another App or User accounts             | app_admin     |
| User | To use the application for data storage/retrieval  | user{1 to 10} |

* Default users are defined and can be altered in [Migration](./../src/main/resources/db/migration/V2_0__create_init_data.sql)
* For passwords encoding "BCryptPasswordEncoder" is used, so any "bcrypt" tool can be used to generate passwords for the migration. Or request the default init password from GovStack support.

## App Role Endpoints

| Endpoint                   | Description             | Usage                                        |
|----------------------------|-------------------------|----------------------------------------------|
| /api/v1/auth/token         | Authentication endpoint | To obtain JWT token for user with "APP" role |
| /api/v1/user/              | Get logged user info    | To obtain data of the logged user            |
| /api/v1/user/register/app  | Register App            | To register new user with "App" role         |
| /api/v1/user/register/user | Register User           | To register new user with "User" role        |

## User Role Endpoints

| Endpoint                  | Description             | Usage                                                                |
|---------------------------|-------------------------|----------------------------------------------------------------------|
| /api/v1/auth/token        | Authentication endpoint | To obtain JWT token for user with "USER" role                        |
| /api/v1/user/             | Get logged user info    | To obtain data of the logged user                                    |
| /api/v1/rpc-data/{action} | Store and get data      | To store and obtain data from the application based on user + tenant |

* Note that application that uses the rpc backend is considered as tenant. When developing multiple applications that use the rpc backend, they all should use different tenant keys, but all of them can use one and the same users.

For more information and tryout of endpoints please access swagger UI at:

* Swagger Link in Application: `/swagger-ui/index.html`
* Swagger export JSON format: `/v3/api-docs`
* Swagger export YAML format: `/v3/api-docs.yaml`