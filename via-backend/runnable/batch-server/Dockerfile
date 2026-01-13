FROM eclipse-temurin:21-jre

WORKDIR /app

RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    --no-install-recommends \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    --no-install-recommends \
    && rm -rf /var/lib/apt/lists/* /usr/share/doc /usr/share/man

# spring 유저 홈 디렉토리 생성이 핵심
RUN addgroup --system spring \
    && adduser --system --ingroup spring --home /home/spring spring \
    && mkdir -p /home/spring \
    && chown -R spring:spring /home/spring

# HOME 환경변수 명시
ENV HOME=/home/spring

COPY --chown=spring:spring runnable/batch-server/build/libs/*.jar app.jar

USER spring:spring

EXPOSE 8081

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
