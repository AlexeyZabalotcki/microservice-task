# Currency-client microservice

### Overview:

1. Service that get requests from client and receives exchange rates from currency microservice via reactive Feign Client.
2. Stores the currency microservice requests for the findById method in the cache.
3. It stores incoming requests with subscription and processes them again by schedule.
4. Parses the JWT token for extract emails.
5. Sends messages to the Kafka topic to mail-sender microservice.


### Technologies:

1. Spring Boot (WebFlux, AOP)
2. Spring Cloud (Config client, Eureka client, Feign client)
3. Reactive Cassandra, Reactive Redis
4. Mapstruct
5. Kafka Producer
