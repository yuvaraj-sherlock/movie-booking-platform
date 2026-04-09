# ─── STAGE 1: BUILD ─────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /workspace/app

COPY pom.xml .
COPY ../pom.xml ../pom.xml

RUN mvn dependency:go-offline -B --no-transfer-progress 2>/dev/null || true

COPY src ./src

RUN mvn package -DskipTests -B --no-transfer-progress

RUN java -Djarmode=layertools -jar target/*.jar extract

# ─── STAGE 2: RUNTIME ───────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine AS runtime

RUN addgroup -S springapp && adduser -S springapp -G springapp

WORKDIR /app

COPY --from=builder --chown=springapp:springapp /workspace/app/dependencies/ ./
COPY --from=builder --chown=springapp:springapp /workspace/app/spring-boot-loader/ ./
COPY --from=builder --chown=springapp:springapp /workspace/app/snapshot-dependencies/ ./
COPY --from=builder --chown=springapp:springapp /workspace/app/application/ ./


USER springapp

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
