JavaRaptors Backend
===================

https://jersey.java.net/documentation/latest/getting-started.html



mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2 \
-DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false \
-DgroupId=com.jr1 -DartifactId=jrest1 -Dpackage=com.jr1 \
-DarchetypeVersion=2.4.1 	


mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-webapp \
                -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false \
                -DgroupId=com.jr2 -DartifactId=simple-service-webapp -Dpackage=com.jr2 \
                -DarchetypeVersion=2.4.1




# Where is glassfish configuration?

/opt/glassfish3/glassfish/config$


# How to stop a domain in Glassfish (domain = web app)

asadmin stop-domain "domain name"
asadmin start-domain "domain name"




# Deploy on glassfish

cd simple-service-webapp
sudo mvn clean package
sudo /opt/glassfish3/bin/asadmin deploy target/simple-service-webapp.war




#install jetty server


cd /tmp
wget http://eclipse.c3sl.ufpr.br/jetty/stable-9/dist/jetty-distribution-9.0.7.v20131107.tar.gz

tar -xvf jetty-distribution-9.0.7.v20131107.tar.gz



	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
	 
	    <modelVersion>4.0.0</modelVersion>
	 
	    <groupId>com.jr2</groupId>
	    <artifactId>simple-service-webapp</artifactId>
	    <packaging>war</packaging>
	    <version>1.0-SNAPSHOT</version>
	    <name>simple-service-webapp</name>
	 
	    <build>
	        <finalName>simple-service-webapp</finalName>
	        <plugins>
	            <plugin>
	                <groupId>org.apache.maven.plugins</groupId>
	                <artifactId>maven-compiler-plugin</artifactId>
	                <version>3.0</version>
	                <inherited>true</inherited>
	                <configuration>
	                    <source>1.7</source>
	                    <target>1.7</target>
	                </configuration>
	            </plugin>
	        </plugins>
	    </build>
	 
	    <dependencyManagement>
	        <dependencies>
	            <dependency>
	                <groupId>org.glassfish.jersey</groupId>
	                <artifactId>jersey-bom</artifactId>
	                <version>2.4.1</version>
	                <type>pom</type>
	                <scope>import</scope>
	            </dependency>
	        </dependencies>
	    </dependencyManagement>
	 
	    <dependencies>
	        <dependency>
	            <groupId>org.glassfish.jersey.containers</groupId>
	            <artifactId>jersey-container-servlet-core</artifactId>
	            <!-- use the following artifactId if you don't need servlet 2.x compatibility -->
	            <!-- artifactId>jersey-container-servlet</artifactId -->
	        </dependency>
	        <!-- uncomment this to get JSON support
	        <dependency>
	            <groupId>org.glassfish.jersey.media</groupId>
	            <artifactId>jersey-media-moxy</artifactId>
	        </dependency>
	        -->
	    </dependencies>
	    <properties>
	        <jersey.version>2.4.1</jersey.version>
	        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	    </properties>
	</project>