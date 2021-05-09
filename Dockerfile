FROM adoptopenjdk:11-jre-hotspot
ADD target/text-search-1.0.0.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","application.jar"]
