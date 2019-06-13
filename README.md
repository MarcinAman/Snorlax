# Snorlax
[![Build Status](https://travis-ci.com/MarcinAman/Snorlax.svg?branch=master)](https://travis-ci.com/MarcinAman/Snorlax)

# NO LONGER MAINTAINED

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

### Deploying
Application can be deployed on bare metal or in Docker container.

#### Bare metal
In order to deploy application on bare metal you need to prepare a tomcat server and postgres database (also don't forget to update credentials and host in config)

Tomcat uses .war files to deploy applications. You can produce that file using command `./mvnw package -Pprod -DskipTests`. War file will be under `target` directory.
After creating executable war log into your server and in tomcat's UI unmount currently running. After that copy newly created .war to `$CATALINA_HOME/webapps`. Tomcat will automatically use it.

For more info please consult the [tomcat manual](http://tomcat.apache.org/tomcat-9.0-doc/index.html) and [postgres manual](https://www.postgresql.org/docs/9.3/tutorial-install.html)

#### Docker containers
In order to deploy application on docker you need to install latest version of this software.
Snorlax has build-in plugin to simplify docker deployments so if you want to produce a local image just type:
`./mvnw package -Pprod verify jib:dockerBuild`. 

After building an image you can test it locally. The easiest way is to install docker-compose and use command: `docker-compose -f src/main/docker/app.yml up`. After about 10 seconds application (with postgres database) should be running on port 8080.


At some point you probably want to push this image to docker repository so the account is required. For more info about external images tagging and docker-hub consult [this page](https://docs.docker.com/docker-hub/repos/)

For more information about it please consult the [deployment manual](https://www.jhipster.tech/docker-compose/) or [docker manual](https://docs.docker.com/install/)

### Others
- Project is using H2 database so everything that you want to save HAS TO be included in `/src/main/resources/config/liquibase/changelog` otherwise it wont be visible on next boot. Liquidbase accepts .xml and .sql but you have to add them to `master.xml`
- Feel free to add other libraries but if something related to this lib isn't working you will have to fix it

Other questions / requests / wishes and free food / alcohol please send to Jakub Sroka or Marcin Aman via facebook / slack
