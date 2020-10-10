# jjstudio

## Set Up
1. Create a local MongoDB instance
```bash
brew install mongodb-community@4.2.8
```
2. Start your local MongoDB instance
```bash
bin/startLocalMongoDB.sh
```
3. Start the Mongo Shell and add a new user
```bash
mongo
> db.createUser({ user: "root", pwd: "root", roles: [], mechanisms: ["SCRAM-SHA-1"] })
```
4. Start your Spring Boot app
```bash
bin/start.sh
```
5. Your Spring Boot app should now be running and connected to your local MongoDB instance. Verify this by testing queries found in [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) .
