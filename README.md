Multi-tenancy demo
-----------------------------

This is a demonstration how to deal with multiple predefined tenants. In this example all tenants are known and
type-safely encoded in an enum.

A more dynamic/general approach can be found [here](https://github.com/wmeints/spring-multi-tenant-demo) - where also
the basic idea for this project was taken from.

The project brings its creation scripts for example databases hosted by a local docker installation so that the system
can be tested with a few simple commands. As this test is easy to do, no additional unit test have been added, yet.


## Usage

### Prerequsites
* JDK version 8
* Docker CE

### Create example databases
From the project's main folder, execute:
```
cd docker
./run-db-containers.sh
```

### Start the webservice
From the project's main folder, execute:
```
mvn clean spring-boot:run
```

### Send the requets
Send the a request to each of the following URLs using a tool of your choice like ***cURL*** (used below), ***HTTPie***
***Postman***):
```
curl http://localhost:8085/tenant_de/getDesc/1
curl http://localhost:8085/tenant_en/getDesc/1
```

Depending on the tenant, you get the *description* from the corresponding database.
