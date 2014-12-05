NOTES
===

# Source Structure (src/main/java/com/cyber2/api/lib):

    |-> client
        |-> reader    (Classes for reading from API, see ReaderAdapterFactory )
        |-> writer    (Classes for writing to API, see WriterAdapterFactory )
        |-> response  (Wrapper for collection based writes, see WriteListResponse )
    |-> config        (Configuration classes to load URLs and connection credentials)
    |-> conn          (Connection classes to manage interaction with API)
    |-> examples      (Example classes using the API)
    |-> exception     (Exception classes)
    |-> server        (Effectively a copy of ThreatConnect API entity library with un-needed dependencies excluded)
    |-> util          (Utility package)

# Resources
    | -> src/main/resources  (holds configuration files for connection, urls, and log4j2

# Distribution Zip File (target/tc-api-lib-0.5.0-bin.zip)

    |-> lib           (Distribution library)
    |-> third-party   (Third Party dependencies)
    |-> examples      (Examples)
    |-> doc           (Quick start guide in PDF/HTML format)


# Open Questions
- Class com.cyber2.api.lib.examples.groups.GroupExample has some test examples. Would this be how developers use the API???

- Are there any utility classes we can add to make it more useful? (i.e. export to csv, export to json)



# Status
1. Implement API for GET/POST/PUT/DELETE (Complete)
2. Create Quick Start Developer Guide (DRAFT Complete)
3. Prepare Examples covering difference operations (Complete)
4. Package jar, javadoc, examples, dependencies, guide, and source??? in downloadable zip with README ( Complete )

