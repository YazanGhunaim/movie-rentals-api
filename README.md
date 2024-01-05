# Movie Rental System

# Prerequisites
* Java (17)
* Gradle (8.2)
* Swift (5.9.2)
* iOS (17)
* Xcode (15.1)
* Postgres

# Getting Started
#### 1- Cloning the repository:
1- `git clone https://gitlab.fit.cvut.cz/ghunayaz/movie-rental-system.git`

2- `cd movie-rental-system/movies-api`
#### 2- Building the project:
* Server: `./gradlew build`
* Client: Build in Xcode and run on target simulator.
#### 3- Running the server:
`java -jar build/libs/movies-api-1.0-SNAPSHOT.jar`
#### 4- Viewing the documentaion:
After the server has been successfully run documentaion can be found at:

`http://localhost:8080/swagger-ui/index.html#/`

# Project Description

## Brief
This is a movie rental service, which includes an API (Server Part) and a mobile client to easily use and view the services offered by the API.

A registered user can view all the movies being offered by my online movie rental shop, and may wish to choose any number of movies to rent.

## API Usage
User can perform all CRUD operations over all 3 entities Customer,Movie and Genre, User can find all his desired movies by genre and/or availability status.

`IMPORT UsageCollection.json into postman to access collection and documentation.`
## Complex Query

### Find available movies withing a genre (Server):
This query helps users find movies that are currently available for rent within a specific genre. It requires filtering
the Movies entity based on genre and availability status.

## Complex Buisness Logic

### Renting Movies (Client):
Customers can select one or more movies to rent. Then will go over all movies/movie that he rented and add them one by one to his rentedmovies, updating his original rentedmovies list.
