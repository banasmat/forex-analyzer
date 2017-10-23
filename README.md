# Forex Analyzer
Application based on Java Spring and Angular, written using TDD approach.
It's supposed to provide helpful insights on stock and forex exchange fundamental and technical analysis.
Present implementation is quite simple but usage of strategy pattern allows to easily replace existing 'algorithms' with more suffisticated ones.
Back end provides a simple api which is partially consumed by Angular client. 
Some of admin actions have to be done manually.

## Functionality
* Searching for price trends in stock or forex data
* Displaying trends as charts
* Finding financial news that might have caused trend start or end

## Ideas for further development
* Collecting feedback from users: which news are more relevant for given trend
* Learning from feedback and improving news finding

## Running application
* clone or download repository
* configure database and other settings in src/main/resources/private.properties (added to .gitignore)
* start your database e.g. `mysqld`
* change directory to forex-analyzer-back-end `cd forex-analyzer-back-end`
* start back-end application with `mvn spring-boot:run`
* change directory to forex-analyzer-front-end `cd ../forex-analyzer-front-end`
* build front-end project `npm install`
* start front-end application `npm start`
* navigate in browser to localhost:3000

## Searching for trends
`POST localhost:8080/price-data-analysis`
example params:
* strategy: `HighLowAverage` (TrendFinderStrategy service name)
* symbol: `EURUSD`
* minDifference: `0.05` (min. difference in prices that is needed to detect a trend)
* start: `01-01-2015 00:00:00`
* end: `01-01-2017 00:00:00`

## Useful links
* Coding style tips: https://github.com/cxxr/better-java
* Structs: https://github.com/cxxr/better-java#structs
* Repositories: https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-four-jpa-criteria-queries/
