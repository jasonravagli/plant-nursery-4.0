# Plant Nursery 4.0
A plant nursery is a place where plants are cared for and grown with two main purposes: conservation biology or commercial use. The latter is the most common scenario, with companies that grow and sell plants to retailers, other nurseries or a combination of both.

## This project
The project takes its name from the selected case of study:Plant Nursery 4.0, namely the computerization of a plant nursery industry through theapplication of the Internet of Things (IoT) principles and technologies.

[report.pdf](https://github.com/jasonravagli/plant-nursery-4.0/blob/main/documentation/report.pdf) explain all the project in details.

## Goal
Since Jakarta EE has been widely studied during the course of Software Architectures and Methodologies while Jakarta NoSQL is still in a prototypal stage, the goal of this project will focus on the features offered by Jakarta NoSQL and their comparison with JPA, assuming Jakarta EE is known.

The final goal is to provide two back end application that compare the SQL and NoSQL solutions.  
+ A Domain Model of our vision about a plant nursery 4.0 is more explained into the documentation.

## Requirements
+ JDK 8
+ Docker, Docker Compose

## Usage

### Docker Container MySql
To create a docker container for the SQL database we used MySql by running the following commands:
+ `docker volume create mysql-volumeName`
+ `docker run --name=mysql -p3306:3306 -v mysql-volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password -d mysql/mysql-server:8.0`
 
So we need to start the container and create a user for MySql database:
+ `docker start mysql`
+ `docker exec -it mysql bash`

Now we are in the mysql bash program and we run the following commands:
+ `mysql -u root -p`
+ `update mysql.user set host = '%' where user='root';`

### Docker Container Cassanra
For the Cassandra container we have a dockerfile into the folder docker so we need to go in the directory and run the following commands:
+ `docker build . -t cassandra-plant-nursery`
+ `docker run --name cassandra -p9042:9042 -d cassandra-plant-nursery`

### Run
The fisrt step for all the two project is to create a docker container with commands shown before.

This guide is for Eclipse IDE using WildFly as application server.
#### SQL Version
For this version was used the [tutorial](https://github.com/jasonravagli/plant-nursery-4.0/blob/main/documentation/SwamLab21-1_JavaEEinAction.pdf) given on the course.

#### NoSQL Version
In this case the Maven integration of Eclipse don't build correctly the project.
So there are two simple step to run the code:
+ Build the project as a maven build in Eclipse:
+ + Select `Run` -> `Run Configurations`
+ + Insert into parameter Goals:`clean package`
+ + The build is `{project directory}\target\{project name}.war`

+ Copy the war file into the application server in the directory: `{WildFly directory}\standalone\deployments`
+ Run the application server into Eclipse

### Endpoints
The base url is: `{server-url}/plant-nursery/rest`  
There are seven different endpoints that are the same for the SQL and NoSQL version:
+ GrowthPlaceEndpoint: crud of groth places
+ LoginEndpoint: find a user, log in
+ MeasuramentEndpoint: seve a measuramente if sensor exist, retrive measurament by plant or growth place or sensor
+ PlantEndpoint: crud plants, need a species on the database
+ PositionEndpoint: get a free position in a nursery, get all position by a growth place; update a position with a plant, a growth place and a list of sensor
+ SensorEndpoint: crud sensor
+ SpeciesEndpoint: crud species

### Endpoint Tests
All the endpoint are tested with the Postman App on Windows OS and Mac Os.
In Postman Collection folder are saved JSON file in format Collection v2.1 of Postman App (learn more at https://blog.postman.com/travelogue-of-postman-collection-format-v2/).
Is possible to import this file on Postman App to replicate our experiments using different parameters.


## Our Vision

### Deployment Diagram
<img src="https://github.com/jasonravagli/plant-nursery-4.0/blob/main/img/deployment-diagram.png">

### Use Cases
<img src="https://github.com/jasonravagli/plant-nursery-4.0/blob/main/img/use-cases-diagram.png">

### Domain Model SQL
<img src="https://github.com/jasonravagli/plant-nursery-4.0/blob/main/img/domain-model.png">

### Package Diagram
The software is subdivided into packages, each with different responsibility:
<img src="https://github.com/jasonravagli/plant-nursery-4.0/blob/main/img/packages-diagram.png">
For the nosql version the package are the same but are reimplemented using the NoSQL database Cassandra. Only REST and DTOs are the same.

