# main module
ARG module_app=module-app

# Build Stage for Spring boot application image
FROM maven:3-jdk-8 as build

WORKDIR /app

# main
COPY pom.xml .

# module-app
ARG module_app
RUN mkdir -p ${module_app}
COPY ["${module_app}/src", "${module_app}/src"]
COPY ["${module_app}/pom.xml","${module_app}/."]

# module-common
ARG module_common=module-common
RUN mkdir -p ${module_common}
COPY ["${module_common}/src", "${module_common}/src"]
COPY ["${module_common}/pom.xml", "${module_common}/."]

# module-liquec
ARG module_liquec=module-liquec
RUN mkdir -p ${module_liquec}
COPY ["${module_liquec}/src", "${module_liquec}/src"]
COPY ["${module_liquec}/pom.xml", "${module_liquec}/."]

# module-project
ARG module_project=module-project
RUN mkdir -p ${module_project}
COPY ["${module_project}/src", "${module_project}/src"]
COPY ["${module_project}/pom.xml", "${module_project}/."]

# module-security
ARG module_security=module-security
RUN mkdir -p ${module_security}
COPY ["${module_security}/src", "${module_security}/src"]
COPY ["${module_security}/pom.xml", "${module_security}/."]

# module-statistics
ARG module_statistics=module-statistics
RUN mkdir -p ${module_statistics}
COPY ["${module_statistics}/src", "${module_statistics}/src"]
COPY ["${module_statistics}/pom.xml", "${module_statistics}/."]

# module-ui
ARG module_ui=module-ui
RUN mkdir -p ${module_ui}
COPY ["${module_ui}/public", "${module_ui}/public"]
COPY ["${module_ui}/src", "${module_ui}/src"]
COPY ["${module_ui}/pom.xml", "${module_ui}/pom.xml"]
COPY ["${module_ui}/*.js", "${module_ui}/*.json", "${module_ui}/"]

RUN mvn package -DskipTests
RUN mkdir -p ${module_app}/target/dependency && (cd ${module_app}/target/dependency; jar -xf ../*.jar)

# Production Stage for Spring boot application image
FROM openjdk:8-jre-alpine as production
ARG module_app
ARG dependency=/app/${module_app}/target/dependency

# Copy the dependency application file from build stage artifact
COPY --from=build ${dependency}/BOOT-INF/lib /app/lib
COPY --from=build ${dependency}/META-INF /app/META-INF
COPY --from=build ${dependency}/BOOT-INF/classes /app

# Run the Spring boot application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "edu.uoc.geopocket.GeoPocket"]