# Contact-Manager

The goal of the project is to provide a set of APIs s that can be used to store and retrieve Contact data over HTTP.

### Build
Execute the following command from the parent directory to build the jar file:
```
mvn clean install
```
### Application Run
From the parent directory, execute the following command to start the application:
```
java -jar -Dspring.profiles.active=local target/contact-manager-0.0.1-SNAPSHOT.jar
```

## Application Run from IDE(Intellij)
Run ContactManagerApplication.java  with VM options  -Dspring.profiles.active=local
 

####API Details(all URLS are mentioned assuming you run application locally)

* Exposes an easy to use swagger url to get started with the APIs. http://localhost:8080/swagger-ui/index.html.
* Exposes POST API to create contact. Swagger link: http://localhost:8080/swagger-ui/index.html#/contact-controller/createContact 
* Exposes GET API to get Contact for a given contactId. Swagger Link: http://localhost:8080/swagger-ui/index.html#/contact-controller/getContact
* Exposes GET API to get Contacts for a bunch of contactIds. Swagger Link: http://localhost:8080/swagger-ui/index.html#/contact-controller/getContacts
* All above mentioned URLs will work only when the application is running using the steps mentioned above.

##
##Local Swagger Endpoint URL
http://localhost:8080/swagger-ui/index.html#/contact-controller/createContact

##### Sample payload for create contact using swgger
POST
````json
{
  "firstName": "abhinav",
  "lastName": "string",
  "contactDetail": {
    "mobileNumber": [
      "+44208012121"
    ],
    "address": {
      "firstLineOfAddress": "Bank Street",
      "secondLineOfAddress": "",
      "postcode": "E14 5JP"
    }
  }
}
````
##### Sample curl command for creating contact
````shell script
curl -X 'POST' \
  'http://localhost:8080/api/contact/' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
        "firstName": "abhinav",
        "lastName": "string",
        "contactDetail": {
          "mobileNumber": [
            "+44208012121"
          ],
          "address": {
            "firstLineOfAddress": "Bank Street",
            "secondLineOfAddress": "",
            "postcode": "E14 5JP"
          }
        }
      }'
````

##### Sample curl command for retrieving a contact
returns 404 for any contactId that is not created.
````shell script
curl -X 'GET' \
  'http://localhost:8080/api/contact/1' \
  -H 'accept: application/json'

````

##### Sample curl command for retrieving contacts
This will return an empty response if no contact found for passed contactIds
````shell script
curl -X 'GET' \
  'http://localhost:8080/api/contact/?contactIds=1&contactIds=2&contactIds=3' \
  -H 'accept: application/json'
````

####Test coverage report

jacoco plugin used to make sure tests cover at-least 90% lines of source code.


