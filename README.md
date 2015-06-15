# Java SDK for ThreatConnect

## Introduction
This SDK provides a high-level abstraction layer of the ThreatConnect&trade; RESTful API and can be used to read and write entities within ThreatConnect&trade; (indicators, threats, victims, etc.).

> This README provides a quick-start on the Java SDK. For detailed documentation, visit the "Java Development" section of the ThreatConnect&trade; SDK documentation:<br/>https://threatconnect-inc.github.io/threatconnect-app-engine/java/sdk/start/

## Maven 
Add the following entries to your pom file (git clone not required):
<br/>
```xml

   <properties>
        <threatconnect-sdk.version>2.0.3</threatconnect-sdk.version>
    </properties>


<!-- repository entry -->
  <repositories>
      <repository>
          <id>threatconnect-java-mvn-repo</id>
         <url>https://raw.github.com/ThreatConnect-Inc/threatconnect-java/mvn-repo-${threatconnect-sdk.version}/</url>
           <snapshots>
              <enabled>true</enabled>
              <updatePolicy>always</updatePolicy>
          </snapshots>
      </repository>
  </repositories>


<!-- sdk dependency -->
    <dependencies>
        <dependency>
            <groupId>com.threatconnect.sdk</groupId>
            <artifactId>threatconnect-sdk</artifactId>
            <version>${threatconnect-sdk.version}</version>
         </dependency>
    </dependencies>

```


## Java SDK Architecture Overview
The Java SDK divides operations into read and write. It provides adapters for each entity exposed by the ThreatConnect&trade; API.  Thus, you'll find classes like `TagReaderAdapter` and `FileIndicatorwriterAdapter`.  `ReaderAdapterFactory` and `WriterAdapterFactory` can instantiate all available readers and writers. Entities are represented by basic DTO classes: `Address`, `Document`, `Threat`, etc.

>  For the Java SDK to communicate with the ThreatConnect&trade; platform, API access must be enabled and an API user must be created.  

## API Configuration

To connect to the API using the SDK, create a Configuration object with one of the following constructors:

```
   public Configuration(String tcApiUrl, String tcApiAccessID, String tcApiUserSecretKey, String defaultOwner);
   public Configuration(String tcApiUrl, String tcApiAccessID, String tcApiUserSecretKey, String defaultOwner, Integer resultLimit);
```

Pass the `Configuration` object when creating a new `Connection` (see examples below).
 

## Optional File Configuration

An alternative method is to use a configuration file with predefined properties read in by the no-arg `Connection` constructor.

A configuration file can be made available to the Java SDK at runtime via the property `threatconnect.api.config=config.properties` with the following format:

    connection.tcApiUrl=<URL to ThreatConnect instance>
    connection.tcApiAccessId=<API user's API access ID>
    connection.tcApiUserSecretKey=<API user's API secret>
    connection.tcDefaultOwner=<API user's API secret>
    connection.tcResultLimit=<API user's API secret>


## Example: Working with Addresses
The following example retrieves a list of address indicators:

    // instantiate Connection object, which configures URLs and requires
    // threatconnect.api.config property to be set to the location of config file
    Connection conn = new Connection();

    AbstractIndicatorReaderAdapter<Address> reader = ReaderAdapterFactory.createAddressIndicatorReader(conn);

    IterableResponse<Address> addresses = reader.getAll();

To retrieve a single address by Id:

    // note that the unique identifier is different for every entity type.
    // for addresses, the unique identifier is the IP address
    Address address = reader.getById(addresses.get(0).getIp());

To update an address:

    AbstractIndicatorWriterAdapter<Address> writer = WriterAdapterFactory.createAddressIndicatorWriter(conn);
    address.setDescription("new description");
    writer.update(address);

To create an Address:

    Address newAddress = new Address();
    newAddress.setDescription("New address");
    ApiEntitySingleResponse<Address, ?> response = writer.create(newAddress);
    if (response.isSuccess()) {
      newAddress = response.getItem();
    }

To delete an Address:

    writer.delete(newAddress.getIp())

## Example Working With Groups and Associations (i.e. pivoting)
ThreatConnect&trade; provides the ability to establish associations between entities to better describe events, threats, etc.  For example, a single incident may be associated with
several indicators, victims, and e-mails.

The following example finds the Threats associated with a host indicator:

    AbstractIndicatorReaderAdapter<Host> reader = ReaderAdapterFactory.createHostIndicatorReader(conn);

    IterableResponse<Threat> threats = reader.getAssociatedGroupThreats("www.bad.com");

To associate an indicator with a threat:

    AbstractIndicatorWriterAdapter<Host> writer = WriterAdapterFactory.createHostIndicatorWriter(conn);

    writer.associateGroupThreat("www.evenbadder.com", threats.get(0).getId());


Given an association it is possible to 'pivot' to find less-obvious connections between events:

    Threat threat; // assume retrieved previously

    AbstractGroupReaderAdapter<Threat> threatReader = ReaderAdapterFactory.createThreatGroupReader(conn);

    IterableResponse<Address> addresses = threatReader.getAssociatedIndicatorsAddresses(threat.getId());

    AbstractIndicatorReaderAdapter<Address> indicatorReader = ReaderAdapterFactory.createAddressindicatorReader(conn);

    IterableResponse<Threat> otherThreats = reader.getAssociatedGroupThreats(addresses.get(0).getIp());


## Commonly Used Components
###Indicators

Indicators can be of several different types: Address, EmailAddress, File, Host, Url.

Bulk indicator reporting methods are also available including: bulk indicator report
status, and bulk indicator report download in json and csv formats.  See `AbstractIndicatorReaderAdapter.getBulkStatus()`, `AbstractIndicatorReaderAdapter.getBulkIndicatorJson()`, and `AbstractIndicatorReaderAdapter.getBulkIndicatorCsv()`.

**Adapter**:
    
+ `ReaderAdapterFactory.getIndicatorReader(Indicator.Type type, Connection conn)`
Note: there are also factory methods for individual indicator types (ex. `ReaderAdapterFactory.createAddressIndicatorReader(Connection conn)`)

+ `WriterAdapterFactory.getIndicatorWriter(Indicator.Type type, Connection conn)`
Note: there are also factory methods for individual indicator types (ex. `WriterAdapterFactory.createAddressIndicatorWriter(Connection conn)`)

**Entity**:

+ There is a base `Indicator` type that is extended by `Address`, `File`, `EmailAddress`, `Host`, and `Url`


###Documents
Documents encompass an actual document and the meta-information associated with it (file name, created date, etc.).  The Documents API exposes two unique methods
to download and upload a document as an octet stream via `DocumentReaderAdapter.downloadFile()` and `DocumentWriterAdapter.uploadFile()`.

**Adapter**:

+ `ReaderAdapterFactory.getDocumentReaderAdapter()`
+ `WriterAdapterFactory.getDocumentWriterAdapter()`

**Entity**:

+ The `Document` type contains meta-information about a document.

###Associations
Association types express realtionships between entities and are Adversaries, Documents, Emails, Incidents, Signatures, and Threats.  Note that indicator adapters implement the interface of all associate adapters through composition, so it is often not necessary to actually use group adapters directly.

**Adapter**:

+ `AttributeAssociateReadable` & `AttributeAssociateWriteable` - Access to attributes and attribute security labels
+ `GroupAssociateReadable` & `GroupAssociateWriteable` - Access to groups, adversaries, emails, incidents, signatures, and threats
+ `IndicatorAssociateReadable` & `IndicatorAssociateWriteable` - Access to indicators (of all types)
+ `OwnerAssociateReadable` & `OwnerAssociateWriteable` - Access to owners
+ `SecurityLabelReadable` & `SecurityLabelWriteable` - Access to entity security labels
+ `TagAssociateReadable` & `TagAssociateWriteable` - Access to tags
+ `VictimAssociateReadable` & `VictimeAssociateWriteable` - Access to Victims

**Entity**

+ Most entities are available through an association (e.g. from a threat you can retrieve an indicator, and from an incident you can get a security label).


## Source Structure

**Location: src/main/java/com/cyber2/api/lib**

    |-> client
        |-> reader    (Classes for reading from API, see ReaderAdapterFactory )
        |-> writer    (Classes for writing to API, see WriterAdapterFactory )
        |-> response  (Wrapper for collection based writes, see WriteListResponse )
    |-> config        (Configuration classes to load URLs and connection credentials)
    |-> conn          (Connection classes to manage interaction with API)
    |-> examples      (Example classes using the API)
    |-> exception     (Exception classes)
    |-> server        (ThreatConnect API entity library)
    |-> util          (Utility package)

## Resources
    | -> src/main/resources  (holds configuration files for connection, urls, and log4j2

## Distribution Zip File
**target/threatconnect-sdk-&lt;version&gt;.zip**

    |-> lib           (Distribution library)
    |-> third-party   (Third Party dependencies)
    |-> examples      (Examples)
    |-> doc           (Quick start guide in PDF/HTML format)


## Contact

If you have any questions, bugs, or requests please contact support@threatconnect.com

