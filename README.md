# jjstudio

## Set up
1. Set up the project directory on your local machine, like:
```
    .
    ├── ...
    ├── jjstudio                    # Top-level/root
    │   ├── jjstudio-java           # Spring Boot App --> https://github.com/jj-web-design-studio/jjstudio-backend-java.git
    │   ├── jjstudio-frontend       # React App       --> https://github.com/jj-web-design-studio/jjstudio-frontend-react
    │   ├── docker-compose.yml      # docker-compose for local dev only
    │   └── ...                     # etc.
    └── ...
```
2. Compile Java code
```bash
cd /jjstudio-java
./gradlew build
```
3. Create docker-compose.yml with the following contents:
```
version: '2'

services:
  redis-server:
    image: 'redis'
    container_name: jjstudio_jedis
    ports: 
      - 6379:6379
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
    volumes:
      - ./jjstudio-java/build/libs:/app/libs
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
```
4. Create Docker volume to persist DB data
```bash
cd ../jjstudio
docker volume create mongodbdata
```
5. Run all containers to start full-stack web app
```bash
docker-compose up
```
6. Verify that JJ Studio is up and running by visiting [localhost:3000](http://localhost:3000) in your browser.
