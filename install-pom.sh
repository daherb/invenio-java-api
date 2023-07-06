mvn clean compile package install
mvn install:install-file -Dfile=target/invenio-java-api-0.0.1-SNAPSHOT.jar -DgroupId=de.ids-mannheim.lza -DartifactId=invenio-java-api -Dversion=1.0 -Dpackaging=jar  -DgeneratePom=true
