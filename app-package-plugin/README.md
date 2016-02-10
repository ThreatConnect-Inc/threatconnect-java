# App Packager Plugin for ThreatConnect

## Introduction
This maven plugin allows for your Java applications to be packaged as a ThreatConnect application during the build process.

## Maven 
Add the following entries to your pom file (git clone not required):
<br/>
```xml

   <properties>
		<threatconnect-sdk.version>3.0</threatconnect-sdk.version>
	</properties>

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
				<groupId>com.threatconnect.sdk</groupId>
				<artifactId>app-package-plugin</artifactId>
				<version>${threatconnect-sdk.version}</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>app-package</goal>
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

