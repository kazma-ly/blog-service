FROM openjdk:11

WORKDIR /blog

COPY ./blog-service-4.0.0.jar /blog/blog.jar

EXPOSE 1331

ENTRYPOINT ["java", "-jar", "blog.jar", "--spring.profiles.active=pro"]