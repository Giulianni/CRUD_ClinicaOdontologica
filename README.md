# CRUD Clinica Odontologica - API REST 

Sistema de administración de pacientes y odontólogos para una clínica. 

### Requerimientos 
1) Java 11
2) Maven

### Instalación 
- [Instalacion Java 11](https://www.oracle.com/java/technologies/downloads/#java11) 
- [Instalacion Maven](https://maven.apache.org/download.cgi).

## Instrucciones 
1) Clonar el proyecto 
```
git clone https://github.com/Giulianni/CRUD_ClinicaOdontologica.git
```

2) Estructura del proyecto 
```
CRUD_ClinicaOdontologica
│   pom.xml 
└───src
```
3)Ejecutar en la terminal: 
```
cd CRUD_ClinicaOdontologica
mvn clean package
```
5) Ejecutando los comandos abajo, tu proyecto deberia correr en el puerto 8080: 
```
cd target
java -jar clinica-odontologica.jar
```
6) Para cambiar el puerto configurado, debes acceder al archivo 'application.properties'
