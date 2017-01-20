# Charlotte Traffic Accidents Collector Web Service

The primary purpose of this web service is to provide traffic accident information for the [Charlotte Traffic Accidents] (https://secure-hamlet-7600.herokuapp.com) Angular 2 application.

Information is supplied directly from the Charlotte Mecklenburg Police Department (CMPD) by the [CMPDAccidents SOAP API] (http://maps.cmpd.org/datafeeds/gisservice.asmx?op=CMPDAccidents).

This code base adds to the CMPDAccidents SOAP API XML in the following ways:
+ CMPDAccidents SOAP API XML responses are converted to JSON which is returned in the response
+ CORS response headers are returned in the response
+ Traffic accident information is cached in order to reduce the number of hits against the CMPDAccidents SOAP API

More information about the CMPD 911 Computer Aided Dispatch (CAD) System is available [here] (http://maps.cmpd.org/trafficaccidents/).

## Using this web service 

This web service is currently deployed on Heroku and can be accessed in a web browser using this link [https://floating-reef-16359.herokuapp.com/accidents] (https://floating-reef-16359.herokuapp.com/accidents).

Results are cached for 3 minutes and will only change if the CMPD publishes new traffic accident information!

## Development stack

This project was developed using Java 8, Spring Boot, Spring WS, and Ehcache.

## Prerequisites

This project is dependent on the Java classes associated with the CMPDAccidents SOAP API [WSDL] (http://maps.cmpd.org/datafeeds/gisservice.asmx?wsdl). The source for these classes is included.


## Build

Run `mvn clean install` to build the project and run the supplied unit tests. The build artifacts will be stored in the `target/` directory. 


