FROM dtr.it.vt.edu/rquintin/cs4604-proj-java:latest


ADD code/java/auto /
COPY sql/install.sql /code/src/main/resources/schema.sql
COPY sql/load.sql /code/src/main/resources/data.sql

WORKDIR /code
CMD ["./mvnw", "spring-boot:run"]
