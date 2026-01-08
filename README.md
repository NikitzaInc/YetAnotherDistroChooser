# DistroChooser

### Author: Nikitza Inc (Nikita Ermilov) 5130904/30106

This app was made to make the choise of distribution easier. If you are looking for lightweight distro for your laptop, or stable and configurable system for your server, this service is for you! It allows filtering by the hardware, needed packages, personal preferrences and user experience. 

### Dependencies
This app wouldn't be possible without [Distrowatch](https://distrowatch.com/), go check them out! I also included links to other projects i found interesting.

### Building locally
* Clone this repository: 
```
git clone https://github.com/NikitzaInc/YetAnotherDistroChooser.git
```
* Set up a postgres database, create user. More about it [here](https://www.postgresql.org/docs/current/runtime.html).
```
mkdir /var/lib/postgres/data
initdb -D /path/to/data/directory
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
