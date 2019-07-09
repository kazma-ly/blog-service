FROM openjdk:11

CMD mkdir /var/blog

COPY ./blog-service-4.0.0.jar /var/blog/blog.jar

EXPOSE 1331

ENTRYPOINT java -jar /var/blog/blog.jar --spring.profiles.active=pro