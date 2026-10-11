 FROM tomcat
 USER root
 COPY pocApiMedHead-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/
 CMD java -jar /usr/local/tomcat/webapps/pocApiMedHead-0.0.1-SNAPSHOT.jar