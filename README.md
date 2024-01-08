# Make a microservice in Spring Boot:

## REST API endpoint details:

### The structure and design of the project are based on good practices and common patterns used in the development of microservices with Spring Boot

> The principle of separation of responsibilities has been followed to maintain a more modular and easy to understand code. Each class and package has a specific function.
> 
> Choosing Java 17 is based on taking advantage of the latest features and improvements of the language. Using Java Streams in some parts of the code allows for more concise and readable data processing.
> 
> These decisions are made with the objective of improving the readability, maintainability and efficiency of the code, complying with best practices in the development of microservices.

- (First step) Initialize dummy album and photo data in an embedded H2 database via external REST calls
```
     -Method: GET
     -Resource endpoint: http://localhost:8080/init
     -Expected response code: 200
```

- Retrieve list of all albums, with associated photo records
```
    -Method: GET
    -Resource endpoint: http://localhost:8080/api/albums
    -Expected response code: 200
```

- Retrieve a single album
```
    -Method: GET
    -Resource endpoint: http://localhost:8080/api/albums/{id}
    -Expected response code: 200
```

- Retrieve a list of all photos
```
    -Method: GET
    -Resource endpoint: http://localhost:8080/api/photos
    -Expected response code: 200
```

- Retreive a list of photos for a given album
```
    -Method: GET
    -Resource endpoint: http://localhost:8080/api/photos/byAlbum/{id}
    -Expected response code: 200
```
- Retrieve a single photo
```
    -Method: GET
    -Resource endpoint: http://localhost:8080/photos/{id}
    -Expected response code: 200
```
- Enrichment without database
```
    -Method: GET
    -Resource endpoint: http://localhost:8080/enrichedAlbums
    -Expected response code: 200
```
- Unit and Integration Tests
```
At least one unit test per functionality and one integration test have been 
included to guarantee the quality of the code and the functionality of the 
microservice.
```