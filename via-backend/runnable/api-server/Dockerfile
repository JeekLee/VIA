FROM eclipse-temurin:21-jre

WORKDIR /app

RUN addgroup --system spring \
    && adduser --system --ingroup spring --home /home/spring spring \
    && mkdir -p /home/spring \
    && chown -R spring:spring /home/spring

ENV HOME=/home/spring

COPY --chown=spring:spring runnable/api-server/build/libs/*.jar app.jar

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
