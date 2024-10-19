# Reference Library Management System with RESTful API

## Requirements:

- `addBook(Book book)`: Adds a new book to the library
- `removeBook(String isbn)`: Removes a book from the library by ISBN
- `findBookByISBN(String isbn)`: Returns a book by its ISBN
- `findBooksByAuthor(String author)`: Returns a list of books by a given author
- `borrowBook(String isbn)`: Decreases the available copies of a book by 1
- `returnBook(String isbn)`: Increases the available copies of a book by 1


## Guides
To run source code you should run following commands on your terminal(unix based):

- Open you command-line interface(osx Terminal or iTerm2 equivalent app)
- Go to root folder of the source code that you checked out from github(or downloaded)

### Prerequisites
Make sure you have installed (jdk17+) and maven, so you could run _mvn_ and _java_ commands on your terminal

### Running the tests:
- run `mvn test` command to see results for the implemented tests

### Running the app:
- `mvn install`
- ```export JAR_FILE= `find target/ -name "*.jar"` ```
- `java -jar ${JAR_FILE}`

Once you ran above commands you should see spring logs which indicates application started and running on port 8080

Alternatively you can use favorite IDE to run tests and application.

### APIs and responses

#### Add Book:

Sample Request
```
POST http://localhost:8080/book
Content-Type: application/json

{
"isbn": "5",
"author": "a1",
"title": "a-a",
"publicationYear": 2022
}
```
Success Response:

```
HTTP/1.1 201 
Location: /book/5
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:00:14 GMT

{
  "message": "ADDED"
}
```
Failure Response

```
HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:00:40 GMT
Connection: close

{
  "message": "Book already exists"
}
```

#### Find Book by ISBN:

Request:
```
GET http://localhost:8080/book?isbn=5
Accept: application/json
```
Success Response:

```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:02:08 GMT

{
  "books": [
    {
      "isbn": "5",
      "title": "a-a",
      "author": "a1",
      "publicationYear": 2022,
      "availableCopies": 1
    }
  ]
}
```
Failure Response
```
HTTP/1.1 404 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:03:14 GMT

{
  "message": "Book not found with ISBN: 3"
}
```

#### Find Book by Author Name

```
GET http://localhost:8080/book?author=a8
Accept: application/json
```
Success Response
```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:04:43 GMT

{
  "books": [
    {
      "isbn": "5",
      "title": "a-a",
      "author": "a1",
      "publicationYear": 2022,
      "availableCopies": 1
    }
  ]
}
```

Failure Response

```
HTTP/1.1 404 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:05:33 GMT

{
  "message": "Book not found with Author: a8"
}
```

#### Delete Book

```DELETE localhost:8080/book/1```

Success Response
```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:10:25 GMT

{
  "message": "REMOVED"
}
```

Failure Response

```
HTTP/1.1 404 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:10:37 GMT

{
  "message": "Book not found with ISBN: 3"
}
```

#### Return Book

```
PATCH http://localhost:8080/book/return/5 
```

Success Response
```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:13:37 GMT

{
  "message": "INCREMENTED"
}
```

Failure Response

```
HTTP/1.1 404 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:13:57 GMT

{
  "message": "Book not found with ISBN: 5"
}
```

#### Borrow Book

```
PATCH http://localhost:8080/book/borrow/5 
```

Success Response
```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:14:39 GMT

{
  "message": "DECREMENTED"
}
```

Failure Response

```
HTTP/1.1 404 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 18 Oct 2024 15:15:11 GMT

{
  "message": "Book not found with ISBN: 5"
}
```

> Alternatively if you're using intelliJ IDEA you can use built-in http client with [API.http](API.http) file
### Assumptions

- No DB requirement mentioned, so app uses its local state, which means
  keeps track of the data in a list so if you stop the application, you'll lose the data
  or having lots of data is going to cause a memory exception
- Response type: No response requirement mentioned, so I did not include resource's latest state to response
- Add Book: Not mentioned what if user wanted to add already existed book, shall we combine the available copies

### Optimizations

ConcurrentLruCache has been used with limit of `3`, that means last retrieved 3 books are going to be in cache
