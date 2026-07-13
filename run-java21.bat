@echo off
setlocal EnableExtensions

set "APP_JAR=%~dp0dist\ChromisPOS.jar"
set "JAVA_HOME="

call :find_java21

if not defined JAVA_HOME (
    echo Java 21 was not found.
    echo Install a Java 21 JDK, or set JAVA21_HOME to the Java 21 install folder.
    exit /b 1
)

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo Java 21 was not found at "%JAVA_HOME%".
    exit /b 1
)

if not exist "%APP_JAR%" (
    echo "%APP_JAR%" was not found. Run build-java21.bat first.
    exit /b 1
)

cd /d "%~dp0dist"
"%JAVA_HOME%\bin\java.exe" -jar "%APP_JAR%"
exit /b %ERRORLEVEL%

:find_java21
if defined JAVA21_HOME (
    if exist "%JAVA21_HOME%\bin\java.exe" (
        set "JAVA_HOME=%JAVA21_HOME%"
        exit /b 0
    )
)

for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk-21*") do (
    if exist "%%~fD\bin\java.exe" (
        set "JAVA_HOME=%%~fD"
        exit /b 0
    )
)

for /d %%D in ("C:\Program Files\Java\jdk-21*") do (
    if exist "%%~fD\bin\java.exe" (
        set "JAVA_HOME=%%~fD"
        exit /b 0
    )
)
exit /b 0
