FROM gradle:8.2.1-jdk17 AS build
LABEL authors="alefuentes"
COPY --chown=gradle:gradle . .
WORKDIR .
RUN gradle build -x test --no-daemon
EXPOSE 8081
CMD ["gradle", "bootRun"]