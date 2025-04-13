FROM openjdk:17
ADD target/dhlpostcode-0.0.1-SNAPSHOT.jar dhlpostcode.jar
ADD src/main/java/com/example/dhlpostcode/postcode-outcodes.csv postcode-outcodes.csv
ENTRYPOINT [ "java", "-jar","dhlpostcode.jar" ]