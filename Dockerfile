FROM amazoncorretto:17

WORKDIR /app

COPY target/employee-system-backend-0.0.1-SNAPSHOT.jar /app/employee-system-backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "employee-system-backend.jar"]