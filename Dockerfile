FROM openjdk:11

WORKDIR /blog

COPY ./build/libs/blog-service-release.jar /blog/blog.jar

EXPOSE 1331

ENTRYPOINT ["java", "-jar", "/blog.jar", "--spring.profiles.active=pro"]