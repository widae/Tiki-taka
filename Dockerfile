FROM maven:3.5-jdk-8-alpine as build 
COPY ./ /tiki-taka
WORKDIR /tiki-taka
RUN mvn package

FROM tomcat:8.0.20-jre8
COPY --from=build /tiki-taka/target/tiki-taka.war /usr/local/tomcat/webapps/  
