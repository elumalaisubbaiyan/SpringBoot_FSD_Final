## Application Overview
 - This is a Spring Restful services for Task Management
 - Applicaiton is built using Spring Boot framework with embedded tomcat

## Software Requirements 
Maven
JDK 1.8
 
 
## Running the application locally
- mvn clean install
- After successfull maven build, run the FSDBootApplication.java to start the application

http://localhost:8090/fsdservices/tasks

## Testing the Restful services

Use POSTMAN Rest client

- To get the list of all tasks and its details

GET http://localhost:8090/fsdservices/tasks

- To get the details of a specific task

GET http://localhost:8090/fsdservices/tasks/1

- To add a new task

POST http://localhost:8090/fsdservices/tasks

- To update an existing task

PUT http://localhost:8090/fsdservices/tasks/1
