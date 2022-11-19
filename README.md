## 
ROBOT apocalypse

## Prerequisites
Ensure you have this installed before proceeding further
- Java 8
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
java -jar ./target/app-1.0.0.jar
```

### Accessing Application
Cpmponent         | URL                                      | Credentials
---               | ---                                      | ---
Frontend          |  http://localhost:8181                   | `demo/demo`
Swagger (API Ref) |  http://localhost:8181/swagger-ui.html   | 
Redoc (API Ref)   |  http://localhost:8181/redoc/index.html  |
Swagger Spec      |  http://localhost:8181/api-docs          |


**To get an authentication token** 
```bash
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"username": "demo", "password": "demo" }' 'http://localhost:8181/session'
```
