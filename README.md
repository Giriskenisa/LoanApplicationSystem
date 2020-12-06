# Loan Application System

- Öncelikle docker-compose.yml dosyamızı çalıştıralım. Docker klasörün dizininde komutu çalıştırın. Komut docker-compose -f docker-compose.yml up -d. Bu komutla birlikte mongodb ve rabbitmq'yu çalıştırmış olacağız.

- Şimdi microservisimizi ayağa kaldıralım. eureka server, loan service, notification service, gateway server' ı ide'nizde maven project olarak açın. Sırasıya projeleri çalıştıralım.  Komutlar -> "mvn clean install" -> "mvn spring-boot:run".

- Client projesinin dizininde komutları sırasıyla çalıştırın. -> npm install, -> npm run ng serve

- Projeyi görebilmek için arayıcınızda http://localhost:4200 adresini açın.

# Tech Stack
* Java8 
* Spring Boot 
* Spring Cloud
* Eureka Server
* Actuator
* RabbitMQ 
* Docker
* Swagger
* Liquibase
* MongoDB 
* Spring Data
* Mockito 
* Lombok 
* Angular