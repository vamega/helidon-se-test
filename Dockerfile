
# 1st stage, build the app
FROM eclipse-temurin:24-jdk AS build

# Install maven
WORKDIR /usr/share
WORKDIR /helidon

# Copy Maven wrapper files
COPY mvnw ./
COPY .mvn ./.mvn

# Create a first layer to cache the "Maven World" in the local repository.
# Incremental docker builds will always resume after that, unless you update
# the pom
COPY pom.xml .
RUN ./mvnw --no-transfer-progress \
    package -Dmaven.test.skip -Declipselink.weave.skip

# Do the Maven build!
# Incremental docker builds will resume here when you change sources
COPY src src
RUN ./mvnw package -DskipTests

RUN echo "done!"

# 2nd stage, build the runtime image
FROM container-registry.oracle.com/java/jdk-no-fee-term:21
WORKDIR /helidon

# Copy the binary built in the 1st stage
COPY --from=build /helidon/target/helidon-se-test.jar ./
COPY --from=build /helidon/target/libs ./libs

CMD ["java", "-jar", "helidon-se-test.jar"]

EXPOSE 8080
