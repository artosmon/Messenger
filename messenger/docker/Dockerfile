FROM openjdk:8-jdk-alpine
LABEL org.opencontainers.image.authors="BolshakovMA"

RUN apk update \
    && export TZ='Europe/Moscow' \
    && echo 'Europe/Moscow' > /etc/timezone \
    && rm -rf /var/cache/

RUN apk --update add nodejs npm
RUN npm i -g redoc-cli

COPY *.jar /app.jar

CMD ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${@}"]