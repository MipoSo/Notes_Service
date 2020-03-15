# How to build and run the sample

## How to build the sample

From this directory, run

```
mvn clean install
```

## How to run the sample

From the target directory, run
```
java -jar target/polsourcenotesms-1.0-SNAPSHOT.jar
```

## How to test the sample

We will use the cURL command line tool for testing. You can use your preferred HTTP or REST client too.


1.) Adding two new notes to the catalog 

``` 
curl --location --request POST 'http://localhost:8080/notes/create' \
--header 'Content-Type: application/json' \
--data-raw '{
	"title" : "fifth",
	"content" : "This is my fifth note"
}'
```

```
curl --location --request POST 'http://localhost:8080/notes/create' \
--header 'Content-Type: application/json' \
--data-raw '{
	"title" : "third",
	"content" : "This is my third note"
}'
```

You should able to see following output. 

```
 HTTP/1.1 201 Created
```


2.) Get details of a book by providing the book id. 

```
curl -v  -X GET  http://localhost:8080/catalog/1
```
You should able to see following output.

```
{"id":1,"name":"Java","author":"SUN"}
```


3.) Delete a book 

```
curl -v  -X DELETE  http://localhost:8080/catalog/1
```

You should able to see following output.
``` 
 HTTP/1.1 202 Accepted
``` 

4.) Try to get the details of non-existing book

``` 
curl -v  -X GET  http://localhost:8080/catalog/1
 ```
 
 You should able to see following output.
 
```  
 HTTP/1.1 404 Not Found
 Specific book does not exists
 ``` 
