# Easyfood

## Repo del backend para la aplicación Easyfood de la materia TIP para la Universidad Nacional de Quilmes.

### Para mas detalle ingresar al repositorio de [documentación](https://github.com/Grupo5-TIP/documentacion)


[![Build Status Master](https://travis-ci.com/Grupo5-TIP/backend.svg?branch=dev)](https://travis-ci.com/github/PabloGMarrero/desapp-unq-grupo-F-012020)


## Instalación

### Pre-requisitos

Es necesario tener instalado jdk8 en adelante, configurado el JAVA_PATH. También deberá instalar Maven y configurar también sus variables de entornos.
También se deberá tener instalado MySQL.

### Pasos
Se deberá pasar como param el SQL_USER y SQL_PASSWORD al comando mvn de la siguiente manera

mvn spring-boot:run -DPROJECT_SQL_USER={user} -DPROJECT_SQL_PASSWORD={password}
