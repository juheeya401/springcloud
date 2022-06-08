FROM openjdk:11.0.15-jdk
VOLUME /tmp
COPY build/libs/discoveryservice-1.0.jar DiscoveryService.jar
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]