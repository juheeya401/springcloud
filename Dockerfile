FROM openjdk:11.0.15-jdk
VOLUME /tmp
# 시스템 내부에 있는 apiEncryptionKey.jks 파일을 컨테이너 root 디렉터리에 'apiEncryptionKey.jks' 명칭으로 복사
COPY apiEncryptionKey.jks /apiEncryptionKey.jks
COPY build/libs/config-service-1.0.jar ConfigServer.jar
ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]