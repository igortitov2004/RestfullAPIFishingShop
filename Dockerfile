FROM openjdk:20
ADD target/fishing-shop.jar fishing-shop.jar
ENTRYPOINT ["java","-jar","fishing-shop.jar"]