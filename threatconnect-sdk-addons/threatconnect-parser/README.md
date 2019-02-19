# Java Parser for ThreatConnect

## Introduction
This parser extends the functionality of the core SDK to allow for easier parsing and saving the of data by separating the logic. The data model contained within can be fully built out and then passed into a SaveService which will traverse the list of objects and save all associated data.

## Maven 
Add the following entries to your pom file (git clone not required):
<br/>
```xml

   <properties>
        <threatconnect-sdk.version>2.11.1</threatconnect-sdk.version>
    </properties>

<!-- sdk dependency -->
    <dependencies>
        <dependency>
            <groupId>com.threatconnect.sdk.addons</groupId>
            <artifactId>threatconnect-parser</artifactId>
            <version>${threatconnect-sdk.version}</version>
         </dependency>
    </dependencies>

```


## Source Structure

**Location: src/main/java/com/threatconnect/sdk/parser**

    |-> model         (An enhanced data model that allows for relating data before saving)
    |-> service
        |-> save      (Contains the service classes that are used for saving the data model)
        |-> writer    (Use the core sdk to define how to write groups and indicators to the server)
    |-> util          (Utility package)

## Resources
    | -> src/main/resources  


## Contact

If you have any questions, bugs, or requests please contact support@threatconnect.com

