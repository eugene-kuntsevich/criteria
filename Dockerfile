FROM java:8
EXPOSE 8080
ADD out/artifacts/criteria_api_jar/criteria-api.jar criteria-api.jar
ENTRYPOINT ["java","-jar","criteria-api.jar"]