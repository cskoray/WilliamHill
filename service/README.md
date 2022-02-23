# William Hill Interview Coding Assignment
 
## Clean build whole project from command line
```
./gradlew clean build
```

## Check that it is running
```
http://localhost:9099/swagger-ui.html
```

## CRUD
```
Please refer to Swagger url stated above
```

## Sample Match score update

```
Below UUID are only representative

curl --location --request PATCH 'localhost:9099/api/v1/game/score' \
--header 'Content-Type: application/json' \
--data-raw '{
  "gameKey": "d49ef92d-0576-4773-8310-f611e9780b03",
  "playerKey": "69f99034-d7eb-40db-81db-f54eb5fe31a5",
  "scoreMinute": 10,
  "teamKey": "e7eee55b-619b-4a2d-8e6e-520d0d6809d2"
}'
```

The operating manual will be rendered in HTML and the output can be located in the build/operating-manual directory.

## Mysql - Docker
```
version: '3.3'

services:
   db:
     image: mysql:8
     container_name: mysql_container
     network_mode: bridge
     command: mysqld --default-authentication-plugin=mysql_native_password
     volumes:
       - my-db:/var/lib/mysql
     restart: always
     environment:
       MYSQL_ROOT_PASSWORD: password
       MYSQL_DATABASE: scoreboard
     ports:
     # <Port exposed> : < MySQL Port running inside container>
      - '3306:3306'
     expose:
     # Opens port 3306 on the container
      - '3306'
volumes:
    my-db:
```