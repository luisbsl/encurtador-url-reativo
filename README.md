# Encurtador de URL Reativo

API reativa utilizando Spring Webflux para encurtar URLs

## Stack
	* Java 8
	* Maven 3.6.1
	* Spring Boot 2.1.6
	* Redis 5.0.5
	* Docker 18.09.2
	* Docker compose 1.23.2
	
## Acesso online 

http://159.89.134.42:8085/encurtar - POST (ex: JSON { "urlOriginal": "https://www.google.com" })
http://159.89.134.42:8085/${chave} - GET

## Rodando os testes

```docker run -p 6379:6379 redis```

```mvn test``` 

## Rodando local

```docker run -p 6379:6379 redis```

```mvn clean -U package -Dmaven.test.skip=true```

```java -jar ./target/encurtador-url-reativo-1.0.0-SNAPSHOT.jar```

## Rodando com o Docker compose

```docker-compose build && docker-compose up```