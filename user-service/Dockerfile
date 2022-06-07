FROM openjdk:11.0.15-jre-buster
VOLUME /tmp
# 주의. 이 Dockerfile 이 있는 위치를 기준으로 작성
COPY build/libs/user-service-1.0.jar UserService.jar
ENTRYPOINT ["java","-jar","UserService.jar"]