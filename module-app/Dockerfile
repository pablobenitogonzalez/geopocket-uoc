# Build Stage for Spring boot application image
FROM openjdk:8-jdk-alpine as build

WORKDIR /app

COPY target/*.jar .

RUN mkdir -p dependency && (cd dependency; jar -xf ../*.jar)

# Production Stage for Spring boot application image
FROM openjdk:8-jre-alpine as production
ARG dependency=/app/dependency

# Copy the dependency application file from build stage artifact
COPY --from=build ${dependency}/BOOT-INF/lib /app/lib
COPY --from=build ${dependency}/META-INF /app/META-INF
COPY --from=build ${dependency}/BOOT-INF/classes /app

# Run the Spring boot application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "edu.uoc.geopocket.GeoPocket"]