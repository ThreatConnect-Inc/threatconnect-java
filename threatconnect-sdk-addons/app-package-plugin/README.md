# App Packager Plugin for ThreatConnect

## Introduction
This maven plugin allows for your Java applications to be packaged as a ThreatConnect application during the build process.

## Maven 
Add the following entries to your pom file (git clone not required):
<br/>
```xml

   <properties>
		<threatconnect-sdk.version>2.16.1-SNAPSHOT</threatconnect-sdk.version>
	</properties>

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-assembly-plugin</artifactId>
				<version>2.6</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>single</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<descriptorRefs>
						<descriptorRef>jar-with-dependencies</descriptorRef>
					</descriptorRefs>
				</configuration>
			</plugin>
			<plugin>
				<groupId>com.threatconnect.sdk.addons</groupId>
				<artifactId>app-package-plugin</artifactId>
				<version>${threatconnect-sdk.version}</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>java-package</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<classifier>jar-with-dependencies</classifier>
				</configuration>
			</plugin>
		</plugins>
	</build>

```

## Contact

If you have any questions, bugs, or requests please contact support@threatconnect.com

