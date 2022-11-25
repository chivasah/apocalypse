## 
ROBOT apocalypse

## Prerequisites
Ensure you have this installed before proceeding further
- Java 11
- Maven 3.3.9+
- npm 5 or above,   
- Angular-cli 1.6.3

### Features
  * API documentation and Live Try-out links with Swagger
  * 
### Build Frontend 
```bash
# Navigate to PROJECT_FOLDER/webui (should contain package.json )
npm install
# build the project (this will put the files under dist folder)
ng build --prod --aot=true
```

### Build Backend
```bash
# Maven Build : Navigate to the root folder where pom.xml is present 
mvn clean install
```

### Start the API and WebUI server
```bash
# Start the server (8181)
# port and other configurations for API servere is in [./src/main/resources/application.properties](/src/main/resources/application.properties) file

# If you build with maven jar location will be 
java -jar ./target/apocalypse-0.0.2-SNAPSHOT.jar

```

### Accessing Application
Cpmponent         | URL                                      | Credentials
---               | ---                                      | ---
Frontend          |  http://localhost:8181                   | `demo/demo`
