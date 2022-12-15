# Hong Kong Public Holidays Data

## About
Hong Kong Public Holidays Data from https://data.gov.hk/en-data/dataset/hk-effostatisticcal

Public API: https://api.data.gov.hk/v1/historical-archive/get-file?url=http%3A%2F%2Fwww.1823.gov.hk%2Fcommon%2Fical%2Fen.json&time=20220701-0912

![](home.png)

## Contents
- [API Documentation](#api-documentation)
- [MySQL Database](#mysql-database)
- [Sequence Diagram](#sequence-diagram)
- [Requirements](#requirements)
- [How to Use](#how-to-use)
- [Diary/Summary](#diarysummary)
- [Implementation Explanation](#implementation-explanation)

## API Documentation
Swagger is a set of open source tools for writing REST-based APIs. 
URL: http://localhost:8080/swagger-ui.html

![](routes.png)

## MySQL Database

![](schema.png)

![](holiday_table.png)

Connection to database -
I used JDBC to connect the front end to the database.

```
spring.datasource.url=jdbc:mysql://localhost:3306/holiday
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.show_sql=true
server.error.include-message=always
```

## Sequence Diagram
In the below diagram, when a request is received from the client, based on REST API, 
corresponding controller acts upon the request. The controller receives the request 
payload and converts it to a POJO(Plain Old Java Object) with the help of JAXB and 
sends it to the service layer to handle any business logic. Here we make the connection
to our database with the help of hibernate JDBC and executes the required command 
based on the type of request. Finally, JPA Hibernate runs the SQL command on the 
database and responds back with the result.

![](sequence_diagram.png)

## Requirements
1. IDE - IntelliJ, Eclipse, Spring Tool Suite, or other
2. Web Browser - Google Chrome - 60 or later, Firefox 50 or later
3. Database - MySQL
4. Spring Boot with Maven for dependency injection

## How To Use

1. Import the project into your local system and import holiday folder inside the main into any IDE as an existing Maven project
2. Update application.properties file with your SQL user credentials. Create a schema named `holiday` in your local system
```
spring.datasource.url=jdbc:mysql://localhost:3306/holiday
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.show_sql=true
server.error.include-message=always
```
3. First, ensure you are in the holiday directory. Run project as a Spring Boot Application using the following command, depending on your OS:
- Mac `./mvnw clean spring-boot:run`

- Windows (Powershell) `.\mvnw clean spring-boot:run`

- Windows (Command Prompt) `mvnw clean spring-boot:run` 
4. Go to `http://localhost:8080/` in your browser to test the application
5. There are a total of 4 tests. HolidayControllerTest requires the app to be running

## Diary/Summary

Day 1: Setup, configuring the MySQL database and creating the spring boot app. Created router to load all holidays and filter holiday by dates.
Created basic index.html, style.css, script.js. Created the landing page using Bootstrap.
The `createTableRow` method in the JS file creates the table rows. It receives all the holidays and
for each of the holidays, an ER entity structure is created. This will create all the entries for the table.

Day 2: Created Get Data button, added event listener for onclick which gets holiday data from the public API and makes fetch call to local server to save in db.
Since the API included a lot of data which is not for our use case, I 
had to filter out everything except for the holiday events data. The data structure of the API is also different
to what we want in our database, so that is the purpose of `HolidayRequest.java`. I utilised Hibernate to map `Holiday.java` with the
database. For example, `id` is mapped to the primary key. When the user presses the Get Data button,
the UI will send all the data from the API to the backend. In the `saveAllHolidays` method in `HolidayService.java`, the request data that is received 
needs to be converted back to the table structure. So in the `convertHolidayRequestToDomainObject` method, we are formatting the date since the API format is 
different, so we have to convert it into the form the database takes. Once this saving is done, it sends back the response to the UI.
Created Search button, added event listener for onclick which filters holiday data with date filter condition using select query.

Day 3: Added Unit Tests. 

## Implementation Explanation

- ```HolidayApplication.java``` This will initiate the Tomcat server and make it run on the port 8080. Swagger API (using Open API) documentation is added to provide more info about the routers.


- ```HolidayController.java``` There are three routers for this application.
1. GET https://localhost:8080/holiday/

To load all the holiday data present in the Holiday table. This router produces the 
data in JSON format. When the UI makes fetch or AJAX request to current API, the 
corresponding endpoint gets triggered. It sends the request to service layer 
HolidayService for handling business logic. From service layer, it directly hits
HolidayRepository which is a JPA implementation file to load all the holiday data.
2. POST https://localhost:8080/holiday/

When the user clicks on Get Data button on UI, it first makes an API call to public 
API provided https://data.gov.hk/en-data/dataset/hk-effo-statistic-cal and response
data is sent as payload data to the server for persisting in MySQL database. Once 
the server requires the HolidayRequest payload, it sends it to the service layer to
convert request data to domain Object for storing in database.
In the service layer, saveAllHolidays receives the request and sends it to 
convertHolidayRequestToDomainObject to make it as persistable domain object, then 
it makes a call to database via HolidayRepository to store all the holidays data in 
Holiday Table.

3. GET https://localhost:8080/holiday/filterbyDate?startDate=2022-12-01&endDate=2022-12-1

This loads all the holiday data present in the Holiday table within the date range. 
This router produces the data in JSON format. When the UI makes a fetch or AJAX 
request to current API, then corresponding endpoint gets triggered. It sends the 
request to service layer HolidayService for handling business logic. 
From service layer, first it parses date from string format to date format then it
directly hits HolidayRepository which is a JPA implementation file to load all the
holiday data. 

- ```HolidayRepository.java```

This runs a SELECT query over all the records in Holiday table and filters according to the date range and sends it back as the response

- ```HolidayServiceTest.java```

Creates the request data and calls the functional method. The functional method 
should return the data back in the correct form. If the requested data matches
the expected output, the test will pass.

- ```HolidayControllerTest.java```

A couple of integration tests. Created a uri, then making a HTTP request to the uri.
It should send back a response and this should check if the data has been loaded
properly.