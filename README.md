# Star Wars Resistance Social Network

## Description

Spring Boot powered API to manage the Resistance members items. Please see the project [statement01](https://github.com/guitsilva/rebels-api/blob/feature/auth/docs/statement01.pdf) and [statement02](https://github.com/guitsilva/rebels-api/blob/feature/auth/docs/statement02.pdf) (pt-BR) for details.

## How-To

### Requirements

- JDK v18
- Maven v3
- **Postgres v14 (see the `application.properties` file for details)**

### Installation

```shell
    $ git clone https://github.com/guitsilva/rebels-api
    
    $ cd rebels-api
    
    $ git switch feature/auth
    
    $ ./mvnw install
    
    $ java -jar target/rebels-api-1.1.0.jar
```

## Documentation

Please see the Postman [collection](https://github.com/guitsilva/rebels-api/blob/feature/auth/docs/rebels-api.postman_collection.json) file for a complete documentation of the endpoints.  

## License

This project is licensed under the terms of the MIT license - see the [LICENSE](https://github.com/guitsilva/rebels-api/blob/feature/auth/LICENSE) file for details.
