FROM openjdk:17
ADD target/dhlpostcode-0.0.1-SNAPSHOT.jar dhlpostcode.jar
ADD filtered_ukpostcodes.csv filtered_ukpostcodes.csv
ENTRYPOINT [ "java", "-jar","dhlpostcode.jar" ]