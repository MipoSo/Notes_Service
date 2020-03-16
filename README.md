# Requirements to run the project
This is a maven project built with JDK 1.8 and [wso2/msf4j microservice framework.](https://github.com/wso2/msf4j)

[application.properties](https://github.com/MipoShow/Notes_Service/tree/master/src/main/resources) file should be updated with desired MySQL database information like database url, username and password.

## SQL Statement to create database

```
CREATE DATABASE notes_db
```


# How to build and run the service

## How to build the service

From this directory, run

```
mvn clean install
```

## How to run the service

From the target directory, run
```
java -jar target/polsourcenotesms-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## How to test the service

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
```
{
    "status": "Note Created",
    "statusCode": "00"
}
```

2.) Get details of a note by providing the note id. 

```
curl --location --request GET 'http://localhost:8080/notes/readnote/2'
```
You should able to see following output.

```
{
    "status": "Success",
    "statusCode": "00",
    "note": {
        "title": "second",
        "content": "This is my second note"
    }
}
```


3.) Delete a note 

```
curl --location --request POST 'http://localhost:8080/notes/delete' \
--header 'Content-Type: application/json' \
--data-raw '{
	"title" : "fifrth"
}'
```

You should able to see following output.
``` 
 HTTP/1.1 200 OK
``` 
```
{
    "status": "Deleted",
    "statusCode": "00"
}
```

4.) Try to get the details of non-existing book

``` 
curl -v  -X GET  http://localhost:8080/catalog/1
 ```
 
 You should able to see following output.
 
```  
 HTTP/1.1 404 Not Found
 ``` 
 ```
 {
    "status": "Not found",
    "statusCode": "01"
}
 ```

5.) Update existing note

```
curl --location --request POST 'http://localhost:8080/notes/update' \
--header 'Content-Type: application/json' \
--data-raw '{
	"oldTitle" : "first",
	"newTitle" : "fifrth",
	"content" : "This is my tenth note"
}'
```
You should see the following output.

```
 HTTP/1.1 200 OK
 ```
 ```
 {
    "status": "Note Updated",
    "statusCode": "00"
}
```

6.) Get all existing notes

```
curl --location --request GET 'http://localhost:8080/notes/allnotes'
```
You should see the following

```
{
    "status": "Success",
    "statusCode": "00",
    "note": [
        {
            "id": 2,
            "title": "second",
            "content": "This is my second note",
            "created": "Mar 15, 2020 10:13:40 PM",
            "modified": "Mar 15, 2020 10:13:40 PM"
        },
        {
            "id": 3,
            "title": "third",
            "content": "This is my third note",
            "created": "Mar 15, 2020 10:13:52 PM",
            "modified": "Mar 15, 2020 10:13:52 PM"
        },
        {
            "id": 4,
            "title": "fourth",
            "content": "This is my fourth note",
            "created": "Mar 15, 2020 10:14:08 PM",
            "modified": "Mar 15, 2020 10:16:49 PM"
        }
    ]
}
```
7.) Get all history of particular note
```
curl --location --request GET 'http://localhost:8080/notes/history/1'
```
You should see.

```
{
    "status": "Success",
    "statusCode": "00",
    "note": [
        {
            "id": 1,
            "title": "first",
            "content": "This is my first note",
            "created": "Mar 15, 2020 10:13:28 PM",
            "modified": "Mar 15, 2020 10:13:28 PM"
        },
        {
            "id": 1,
            "title": "fifrth",
            "content": "This is my tenth note",
            "created": "Mar 15, 2020 10:13:28 PM",
            "modified": "Mar 15, 2020 10:17:57 PM"
        },
        {
            "id": 1
        }
    ]
}
```

