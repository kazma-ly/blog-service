FROM openjdk:11

COPY ./blog-service-release.jar blog.jar

EXPOSE 1331

ENTRYPOINT ["java", "-jar", "blog.jar", "--spring.profiles.active=pro"]