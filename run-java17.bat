@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot"
set "APP_JAR=%~dp0dist\ChromisPOS.jar"

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo Java 17 was not found at "%JAVA_HOME%".
    exit /b 1
)

if not exist "%APP_JAR%" (
    echo "%APP_JAR%" was not found. Run build-java17.bat first.
    exit /b 1
)

cd /d "%~dp0dist"
"%JAVA_HOME%\bin\java.exe" -jar "%APP_JAR%"
