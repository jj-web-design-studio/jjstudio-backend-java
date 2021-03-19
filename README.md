# jjstudio

## Stand Alone Set Up (Non-dockerized)
0. Clone this repository
```bash
git clone https://github.com/justinchung/jjstudio-java.git
```
1. Create a local MongoDB instance
```bash
brew install mongodb-community@4.2.8
```
2. Start your local MongoDB instance
```bash
bin/mongo_local.sh
```
3. Start the Mongo Shell and add a new user
```bash
mongo
> db.createUser({ user: "root", pwd: "root", roles: [], mechanisms: ["SCRAM-SHA-1"] })
```
4. Start your Spring Boot app
```bash
bin/spring_boot_local.sh
```
5. Your Spring Boot app should now be running and connected to your local MongoDB instance. Verify this by testing queries found in [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) .

## Set up with JJ Studio React App
0. Clone this repository
```bash
git clone https://github.com/justinchung/jjstudio-java.git
```
1. Compile Java code
```bash
./gradlew build -x test
mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
```
2. Build the Docker image
```bash
docker build --build-arg DEPENDENCY=build/dependency -t springio/gs-spring-boot-docker .
```
3. Create docker-compose.yml with the following contents:
version: '2'
services:
  redis-server:
    image: 'redis'
  spring:
    container_name: jjstudio_spring
    depends_on:
      - mongo
    build:
      dockerfile: Dockerfile
      context: ./jjstudio-java
    ports:
      - 8080:8080
    hostname: spring
  react:
    container_name: jjstudio_react
    build:
      dockerfile: Dockerfile
      context: ./jjstudio-frontend
    volumes:
      - /app/node_modules
      - ./jjstudio-frontend:/app
    ports:
      - 3000:3000
    hostname: react
  mongo:
    container_name: jjstudio_mongo
    image: mongo
    restart: always
    ports:
      - 27017:27017
    hostname: mongo
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: root
    volumes:
      - mongodbdata:/data/db

volumes:
  mongodbdata:

4. Run all containers to start full-stack web app
```bash
cd ../path/to/docker-compose.yml
docker-compose up
```
