FROM gradle:8.3.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/arafin/filemanagement
WORKDIR /home/arafin/filemanagement
# RUN gradle build --no-daemon
WORKDIR /home/arafin/filemanagement/build/libs
ENTRYPOINT ["java", "-jar", "file-app-v1.jar"]