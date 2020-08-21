# SatLoca - Micronaut server that streams a JSON feed of a satellite's changing position

Given a satellite's [NORAD catalog number](https://celestrak.com/satcat/search.php), tracks the satellite position
across the sky (latitude, longitude and altitude).

Created as an exercise in using [Micronaut](https://micronaut.io), [RxJava](https://github.com/ReactiveX/RxJava)
and [GraalVM](https://www.graalvm.org/).

This project uses: [predict4java](https://github.com/davidmoten/predict4java) to do the calculations.
This library can also calculate the satellite's position relative to a point on the ground
(direction, range, rate of range change, if above the horizon, etc.) and when it will next pass overhead.
Implementing these are left as an exercise for the reader.

## Setup and Build

### Java 11

You will need to have the Java 11 JDK (or higher) installed.  Check by typing the following into the command line:

    $ java -version
    openjdk version "11.0.4" 2019-07-16
    OpenJDK Runtime Environment 18.9 (build 11.0.4+11)
    OpenJDK 64-Bit Server VM 18.9 (build 11.0.4+11, mixed mode)
    
    $ javac -version
    javac 11.0.4

If these are not found or show a version lower than 11 then download and install a Java JDK from [Oracle](https://jdk.java.net/).

Once installed, the directory containing java should have been added to your PATH environment variable,
so that the above `java` and `javac` commands will work from the command line. 

### Building and starting the server from the command line

    cd path/to/this/dir
    ./gradlew build           (gradlew - if using Windows command prompt)
    ./gradlew run             (Use "./gradlew --stop" in another window to stop)
    
Browse to <http://localhost:8080/swagger-ui/> to see the API details.

Note: That the Swagger-UI waits until the server closes the connection before displaying any results.
The "track" service keeps the connection open to send updates.  Either:

* Use the "max" parameter with a low number so that the response will be displayed after this many seconds.
* Test these services using CURL with the "--no-buffer" option (output displayed by CURL once a second when received).

For example:

    curl -si --no-buffer -X GET "http://localhost:8080/track/25544" -H  "accept: application/x-json-stream"

### Running in an IDE

#### Lombok

This project uses Lombok to insert getters, setters and other boiler plate code.  As a consequence the raw code will
show compilation errors in your IDE.  To avoid this use the Lombok plugin. Go to <https://projectlombok.org/> and
select the installation instructions for your IDE from the "Install" menu.

#### IntelliJ

From the IntelliJ welcome window: select "Open or Import", navigate to this folder and click "OK".

IntelliJ will recognise that this is a Gradle project and configure everything appropriately from the files it finds.
However, it can take 2 or more attempts to start the application.

#### Eclipse

To import into Eclipse use the command:

    ./gradlew eclipse

Then import into Eclipse using : File -> Import -> Gradle -> Existing Gradle Project.

##### Debugging

Micronaut does compile time dependency injection using annotation processors - currently Eclipse
Version: 2020-06 (4.16.0) seems to mix up the classpath for the generated classes.  Starting the server directly via
the Application class, the server starts but all requests return a 404 status.

To workaround this, start the application via Gradle, passing a JvmDebugPort system property.

    ./gradlew -DJvmDebugPort=8000 run --continuous

Then attach the Eclipse debugger to port 8000.

### Running using GraalVM

To use Oracle GraalVM native image runtime (assuming you have docker installed, a bash command line and CURL):

* Build the application with `./gradlew assemble`.
* Build a docker image with `./docker-build.sh`.  This takes some time (about 30 minutes on my laptop).
* Run the docker image with `docker run -p 8080:8080 --rm --name satloca satloca`.
* Test service with `curl -si --no-buffer -X GET "http://localhost:8080/track/25544" -H  "accept: application/x-json-stream"`. Control-C to stop.
* Stop docker container with `docker kill satloca`.

The Swagger-UI does not currently work.  This probably requires some unknown classes to be annotated for introspection.
