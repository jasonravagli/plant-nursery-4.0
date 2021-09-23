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

### Compile
dire come compilare caso sql e cassandra ?

### Run
#### with docker
`docker-compose up`
dire come creare i due container cassandra e my sql

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


## Domain Model SQL version
<img src="https://github.com/jasonravagli/plant-nursery-4.0/blob/main/DomainModel.png" height="836" width="1180">

## Entity-Relationship diagram SQL
Schema of our database (tables are auto-generated using the JPA specification):


