# Forex Analyzer
Application based on Java Spring and Angular 2. 
It's supposed to provide helpful insights on stock and forex exchange fundamental analysis.

## Functionality
* Searching for price trends in stock or forex exchange data
* Displaying trends as charts
* Finding financial news related to trend turns (TODO)
* Getting feedback on which data is more relevant and 'learning' patterns from it (TODO)

## Running application (TODO test it)
* clone or download repository
* configure database and other settings in src/main/resources/private.properties (added to .gitignore)
* start your database
* navigate to forex-analyzer-back-end `cd forex-analyzer-back-end`
* start back-end application with `mvn spring-boot:run`
* navigate to forex-analyzer-front-end `cd ../forex-analyzer-front-end`
* build front-end project `npm install`
* start front-end application `npm start`
* navigate in browser to localhost:3000

## Coding tips
* Coding style tips: https://github.com/cxxr/better-java
* Structs: https://github.com/cxxr/better-java#structs
* Repositories: https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-four-jpa-criteria-queries/