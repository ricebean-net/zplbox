# build webapp
FROM node:18 AS webapp-builder

ENV TZ=CET

ARG VERSION=0.0.0

COPY ["src/main/webapp", "/home/node/app"]
WORKDIR /home/node/app

RUN yarn install
RUN yarn version --new-version ${VERSION}
RUN yarn test --watchAll=false --passWithNoTests
RUN yarn build


# compile and test app
FROM eclipse-temurin:21-alpine AS java-build

ENV LANGUAGE=en_US
ENV LANG=en_US.utf-8
ENV LC_ALL=en_US.UTF-8
ENV TZ=CET

COPY ["src", "/work/app/src"]
COPY ["gradle", "/work/app/gradle"]
COPY ["gradlew", "build.gradle", "settings.gradle", "/work/app/"]

COPY --from=webapp-builder ["/home/node/app/build", "/work/app/src/main/resources/static"]



WORKDIR /work/app

ARG VERSION=0.0.0

RUN ./gradlew -i build -PprojectVersion=${VERSION} --no-daemon

RUN rm /work/app/build/libs/*-plain.jar \
    && cp /work/app/build/libs/*.jar /work/app/build/libs/zplbox.jar


# build JRE
FROM java-build AS jre-build

RUN $JAVA_HOME/bin/jlink \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /work/jre

         
# create final image
FROM alpine:3

ARG VERSION
ARG COMMIT_SHA

ENV VERSION=${VERSION}
ENV COMMIT_SHA=${COMMIT_SHA}

ENV LANGUAGE=en_US
ENV LANG=en_US.utf-8
ENV LC_ALL=en_US.UTF-8
ENV TZ=CET

RUN apk add chromium

ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"
ENV JAVA_TOOL_OPTIONS="-XX:-TieredCompilation"

COPY --from=jre-build --chown=meixxi:meixxi ["/work/jre", "$JAVA_HOME"]
COPY --from=java-build --chown=meixxi:meixxi ["/work/app/build/libs/zplbox.jar", "/app/zplbox.jar"]

ENTRYPOINT [ "java", "-jar", "/app/zplbox.jar"]

EXPOSE 8080
