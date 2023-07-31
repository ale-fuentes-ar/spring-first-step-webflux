# SPRING : First Step with WebFlux
![](https://img.shields.io/badge/by-Alejandro.Fuentes-informational?style=flat&logoColor=white&color=cdcdcd)

Hi guys, in this project I do a simple task ussing technology WebFlux.

<a href="https://github.com/ale-fuentes-ar/spring-first-step-webflux">
  <img align="center" src="https://github-readme-stats.vercel.app/api/pin/?username=ale-fuentes-ar&repo=spring-first-step-webflux&title_color=ffffff&text_color=c9cacc&icon_color=2bbc8a&bg_color=1d1f21" />
</a>

### 🔧 Technologies & Tools

Some technology that I do ussing in this project:

![](https://img.shields.io/badge/OS-Windows-informational?style=flat&logo=windows&logoColor=white&color=0078d4)
![](https://img.shields.io/badge/Project_Manager-gradle-informational?style=flat&logo=Gradle&logoColor=white&color=02303a)
![](https://img.shields.io/badge/Editor-Intellij_IDEA-informational?style=flat&logo=intellij-idea&logoColor=white&color=000000)
![](https://img.shields.io/badge/Code-Java-informational?style=flat&logo=java&logoColor=white&color=cdcdcd)
![](https://img.shields.io/badge/Code-SpringBoot-informational?style=flat&logo=springboot&logoColor=white&color=6db33f)
![](https://img.shields.io/badge/Container-Docker-informational?style=flat&logo=docker&logoColor=white&color=2496ed)
![](https://img.shields.io/badge/Data_Base-postgreSql-informational?style=flat&logo=Postgresql&logoColor=white&color=4169e1)



> ### Postgresql in container
> 
> The file `df-product-db.yaml` was created for install instance of Postgresql database
> for to start, you can execute in CLI
> ```shell
> docker-compose -f df-product-db.yaml up --build -d
> ```


> **NOTE**: make sure to start Docker Desktop before execute 

## Docker compose

For up all project in containers, create a file `docker-compose.yaml` in same root path where was clone the project, and put this content:

```shell
version: "3"
services:

  # this is DB
  product-db:
    image: postgres:11
    container_name: product-db
    restart: always
    networks:
      - wf-product
    environment:
      - POSTGRES_DB=product
      - POSTGRES_USER=admin
      - POSTGRES_PASSWORD=root
    ports:
      - 5432:5432

  # this is our project
  product-api:
    build: './spring-first-step-webflux'
    container_name: product-api
    depends_on:
      - product-db
    networks:
      - wf-product
    environment:
      - DB_HOST=product-db
      - DB_PORT=5432
      - DB_NAME=product
      - DB_USER=admin
      - DB_PASS=root
    ports:
      - 8081:8081

networks:
  wf-product:
    driver: bridge

```

Next run it

```shell
docker-compose up --build -d
```

## Testing with Postman

In this file [collection Potsman][link-postman-collection] can be get to collections for testing in [Postman][link-postman]


<!-- links and references -->
[link-postman-collection]:miscelaneas/First-Tutorial-WebFlux.postman_collection.json
[link-postman]:https://www.postman.com/