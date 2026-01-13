# YetAnotherDistroChooser

This app was made to make the choise of distribution easier. I'm not going to advertise it too much, it's fairly small and simple project I initially made for uni, but I don't want to throw it to the bin yet. 

### Dependencies
This app wouldn't be possible without [Distrowatch](https://distrowatch.com/) and [Repology](https://repology.org/), go check them out! I also included links to other projects i found interesting.

### Building locally
* Clone this repository: 
```
git clone https://github.com/NikitzaInc/YetAnotherDistroChooser.git
```
* Create `.env` file and fill it with your values, example you can find in `origin.env`.
####
* Build the project:
```
docker compose up -d --build
```
* You can check your containers by `docker ps`.

### Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.5/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.5/reference/using/devtools.html)
* [Docker Compose Support](https://docs.spring.io/spring-boot/3.5.5/reference/features/dev-services.html#features.dev-services.docker-compose)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.5/reference/web/servlet.html)
