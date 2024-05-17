# 빌드 스테이지
FROM arm64v8/gradle:8.5-jdk21 AS build

# 소스코드를 복사할 작업 디렉토리를 생성
WORKDIR /app

# 라이브러리 설치에 필요한 파일만 복사
COPY build.gradle settings.gradle ./

RUN gradle dependencies --no-daemon

# 호스트 머신의 소스코드를 작업 디렉토리로 복사
COPY . /app

# Gradle 빌드를 실행하여 jar 파일 생성
RUN gradle clean build --no-daemon

# 실행 스테이지
FROM openjdk:21-jdk

# 애플리케이션을 실행할 작업 디렉토리를 생성
WORKDIR /app

# 빌드 이미지에서 생성된 jar 파일을 런타임 이미지로 복사
COPY --from=build /app/build/libs/movit-0.0.1-SNAPSHOT.jar /app/movit.jar

# 컨테이너에서 실행되는 애플리케이션을 원격으로 디버깅하기 위해 다음과 같이 작성
EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["java"]
CMD ["-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "movit.jar"]