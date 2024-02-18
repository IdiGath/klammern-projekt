FROM maven:3.9.6-eclipse-temurin-21
# Erstellen von Application-Ordner und das Kopieren der Sourcen
ADD . /klammern-projekt
WORKDIR /klammern-projekt

# Prüfen ob alles in Ordnung ist
RUN echo "Ordner Inhalt:"
RUN ls -a

# Build ausführen
RUN mvn install
RUN echo "jar is fertig"
# Entfernen von Build-Container und reine Ausführung der Jar-Datei
FROM openjdk:21-jdk
LABEL maintainer="Ievgenii Izrailtenko"

# Jar zum Container hinzufügen
COPY --from=0 "/klammern-projekt/backend/target/klammern-projekt.jar" klammern-projekt.jar

# Jar-Artefakt ausführen
CMD ["java","-jar","/klammern-projekt.jar"]