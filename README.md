# Snorlax
[![Build Status](https://travis-ci.com/MarcinAman/Snorlax.svg?branch=master)](https://travis-ci.com/MarcinAman/Snorlax)

### Requirements
- Java 8
- npm

### How to setup?
- In intellij `import project` and then select `pom.xml`, next tick all profiles. Intellij will download dependencies and you should have 1 Springboot running profile. If not create it yourself using the `dev` spring profile
- In terminal type `npm install` to install frontend dependencies

### Running
- Backend and frontend are separated
- Run backend in intellij using spring (not maven) profile (dev) since on maven debugger probably wont work correctly
- To run frontend type `npm start` or create yourself a node config in intellij

### Others
- Project is using H2 database so everything that you want to save HAS TO be included in `/src/main/resources/config/liquibase/changelog` otherwise it wont be visible on next boot. Liquidbase accepts .xml and .sql but you have to add them to `master.xml`
- Feel free to add other libraries but if something related to this lib isn't working you will have to fix it

Other questions / requests / wishes and free food / alcohol please send to Jakub Sroka or Marcin Aman via facebook / slack
