# Routing Application

## Prerequisities:
- installed and properly configured Java JDK v 11
- installed and properly configured Maven
- network enabled/configured to allow maven to collect artifacts from repository
- network enabled/configured to get country list from specified URL

## Running the application
- get sources (git clone https://github.com/ktollinger/routing.git)
- build and run application:
```
mvn spring-boot:run
```
or (to use different port than the default one):
```
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8085
```
- application then bounds itself to the port localhost:8080
- only published url is http://localhost:8080/routing/

## Notes
- It was not specified what route should application choose if there
  are multiple routes with the same cost (number of border crossing).
  Application now select the (pseudo-)random one. Pseudo- baceuse, it
  uses HashSet as underlying collection and countries in next (tree)
  level are processed in the order given by caa3 id hash code.
