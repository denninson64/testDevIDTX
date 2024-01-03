# Make a microservice in Spring Boot:

## REST API endpoint details:

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