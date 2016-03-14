# Java SDK for ThreatConnect
Click on any of the projects above to view more detailed information.

## threatconnect-sdk
This SDK provides a high-level abstraction layer of the ThreatConnect&trade; RESTful API and can be used to read and write entities within ThreatConnect&trade; (indicators, threats, victims, etc.).

### Maven
```
<dependency>
  <groupId>com.threatconnect.sdk</groupId>
  <artifactId>threatconnect-sdk</artifactId>
  <version>2.5</version>
</dependency>
```

> This README provides a quick-start on the Java SDK. For detailed documentation, visit the "Java Development" section of the ThreatConnect&trade; SDK documentation:<br/>https://threatconnect-inc.github.io/tc-exchange/java/sdk/start/

## threatconnect-parser
This parser extends the functionality of the core SDK to allow for easier parsing and saving the of data by separating the logic. The data model contained within can be fully built out and then passed into a SaveService which will traverse the list of objects and save all associated data.

## app-package-plugin
This maven plugin allows for your Java applications to be packaged as a ThreatConnect application during the build process.

## Contact

If you have any questions, bugs, or requests please contact support@threatconnect.com

