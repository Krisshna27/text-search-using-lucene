# Search Numbers

This applications enables users to search numbers in the uploaded file or any specific text supplied through request body.

When a text or file is sent through request, the content is indexed by Apache Lucene. 

Apache Lucene library is used to perform search. This library provides search based on generated index.

In the current implementation, the index is overwritten whenever a new search is performed.
Indexes will be stored at the path ./lucene/indexDir/

* By default, application limits to number based searches.
* RegexQueryParser is used to search by numbers.
* The file is not stored within application context after index creation.
* Static code analysis is done through sonarlint.

## Language & Frameworks

* Java 11
* Spring Boot 2.4.5
* Apache Lucene 8.2.2
* OpenAPI 3.0
* Redoc - Swagger documentation

## API

#### Number search through file upload
This api can support files of any type
```curl
curl --location --request POST 'localhost:8080/documents/files/search' \
--form 'file=@"<path-to-the-file>"'
```
#### Number search through request body
This api can support plain text, xml and html formats
```curl
curl --location --request POST 'http://localhost:8080/documents/text/search' \
--header 'Content-Type: text/plain' \
--data-raw 'daffasfs2434234sfdsfaf
adas82348
24343
sfdfsdfd 
safsfdsfdsfcx asdasdf
asfdafdf sfdadfsfdf
asdadsda adasdafsfdfs
dasdasd asdasdad adadswqddassdqefwfwafdc qwdasdefsa'
```

## Build It.. Run It..

Use the ```mvn clean install``` command to build binaries.


### Maven
Run the application using the command ```mvn spring-boot:run```


### Docker

To run in docker maven build should be done first.

Create docker image  : ```docker-compose build```

Run docker container   : ```docker-compose up -d```



<b>After the application is started, the API can be accessed at <a href="http://localhost:8080"> http://localhost:8080.</a></b>


## API Documentation

* OpenAPI 3.0 JSON can be accessed at http://localhost:8080/documentation
* ReDoc UI can be accessed at http://localhost:8080/redoc.html

## Further Extensions

### Within Application
1. Line numbers of the search occurences is not included in the search results as files
   are not stored within application context.
   But this can be implemented by storing the files temporarily and search the words within files using Java Files library<br>
2. Extend the search feature to support any field types other than numbers.
3. Error handling is done generically, more specific exceptions can be created to handle all corner cases.
   Corner cases not covered under current scope.
   - Supplying multipart request body to text search and viceversa.
   
### External to application
1. Store the files in databases like Apache Solr, Elastic where the search feature is provided out-of-box. 
   These databases uses Apache Lucene at the core. Specifically, Elastic provides REST api's on top of which 
   custom configurations can be done easily. 

<br/>
<br/>
<br/>

#### ```Love-It```?

Drop your feedback @ <a href= "krisshnakumar.m07@gmail.com">krisshnakumar.m07@gmail.com </a>
