## Description
Project was created with Spring Boot Initializer, then the Spring part was discarded as unneded.

Kotlin, gradle and some nice libraries come and bring you a command line utility that has help:
```
Usage: runner [OPTIONS]

Options:
  --path TEXT      The relative or absolute path to the folder where the data
                   is
  --operation INT  Select mode of operation 1. All workflows with instances 2.
                   Workflows with running instances 3. Contractors assigned to
                   running instances
  -h, --help       Show this message and exit
```

Tested the actual extraction of data and storage. 
Ran out of time for testing the actual display logic and making sure it works correctly.

## Assumptions
* invalid data is currently discarded
* passed path is scanned for files, assuming the filename to have the extension `data`
* data will have consistent fields for the forseeable future
* adding new data types should be simple
