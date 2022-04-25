# OpenNMS Technical Challenge

## Intro

This solution takes into consideration an anomaly in the test material where one of the example input/expected outputs is wrong.

- For example:

Given:
```
trap-type-oid-prefix: 
 - .1.3.6.1.6.3.1.1.5 
 - .1.3.6.1.2.1.10.166.3
 - .1.3.6.1.4.1.9.9.117.2 
 - .1.3.6.1.2.1.10.32.0.1 
 - .1.3.6.1.2.1.14.16.2.2 
 - .1.3.6.1.4.1.9.10.137.0.1
```

and OID Input: ```.1.3.6.1.4.1.9.9.117```

The expected response is: ```.1.3.6.1.4.1.9.9.117: true```

This does not start with any of the trap type OID prefixes. Rather, it is missing a `.2` in the closest case to return a successful filter.
In the current description I would have expected this to be :
```.1.3.6.1.4.1.9.9.117: false```

This implementation takes this into consideration.

## Installation


- Download dependencies
```
./mvnw install
```

- Run tests
```
./mvnw test
```

- Run the application
```
./mvnw spring-boot:run
```

## User Guide

- Once the application starts up you will be prompted to provide the OID to filter against.
- If you provide a valid OID then it will return with `: true` concatenated to the end.
- If you provide an invalid OID then it will return with `: false` concatented to the end.
- If you do not provide an OID then it will print a message and finish.