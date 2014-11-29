NOTES
===

# Structure (com.cyber2.api.lib):

    |-> client
        |-> reader    (Classes for reading from API, see ReaderAdapterFactory )
        |-> writer    (Classes for writing to API, see WriterAdapterFactory )
        |-> response  (Wrapper for collection based writes, see WriteListResponse )
    |-> config        (Configuration classes to load URLs and connection credentials)
    |-> conn          (Connection classes to manage interaction with API)
    |-> examples      (Example classes using the API, this code would live outside the library)
    |-> exception     (Exception classes)
    |-> server        (Effectively a copy of ThreatConnect API entity library with un-needed dependencies excluded)
    |-> util          (Utility package)

    src/main/resources  (holds configuration files for connection, urls, and log4j2

# Open Questions
- Class com.cyber2.api.lib.examples.groups.GroupExample has some test examples. Would this be how developers use the API???

- Are there any utility classes we can add to make it more useful? (i.e. export to csv, export to json)



# Java API Library Effort Outline
1. Implement API for GET/POST/PUT/DELETE (Code complete, need to test)
2. Create Quick Start Developer Guide (In progress)
3. Prepare Examples covering difference methods (In Progress)
4. Package jar, javadoc, examples, dependencies, guide, and source??? in downloadable zip with README (Pending)

