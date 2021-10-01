# Plant Nursery 4.0
A plant nursery is a place where plants are cared for and grown with two main purposes: conservation biology or commercial use. The latter is the most common scenario, with companies that grow and sell plants to retailers, other nurseries or a combination of both.

## This project
The project takes its name from the selected case of study:Plant Nursery 4.0, namely the computerization of a plant nursery industry through theapplication of the Internet of Things (IoT) principles and technologies.

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
???

### Endpoints
The base url is: `{server-url}/plant-nursery/rest`  
There are seven different endpoints that are the same for the SQL and NoSQL version:
+ GrowthPlaceEndpoint:
+ LoginEndpoint:
+ MeasuramentEndpoint:
+ PlantEndpoint:
+ PositionEndpoint:
+ SensorEndpoint:
+ SpeciesEndpoint:

### Endpoint Tests
All the endpoint are tested with the Postman App on Windows 10 OS and (Mac Os???).
In Postman Collection folder are saved JSON file in format Collection v2.1 of Postman App (learn more at https://blog.postman.com/travelogue-of-postman-collection-format-v2/).
Is possible to import this file on Postman App to replicate our experiments using different parameters.

## Domain Model SQL version
<img src="https://github.com/jasonravagli/plant-nursery-4.0/blob/main/DomainModel.png" height="836" width="1180">

## Entity-Relationship diagram SQL
Schema of our database (tables are auto-generated using the JPA specification):


