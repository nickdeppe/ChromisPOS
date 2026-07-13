@echo off
setlocal EnableExtensions

set "NETBEANS_HOME=C:\Program Files\NetBeans 8.2"
set "BUILD_PROPS=%TEMP%\chromispos-netbeans-build.properties"
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

if not exist "%JAVA_HOME%\bin\javac.exe" (
    echo A Java 21 JDK was not found at "%JAVA_HOME%".
    exit /b 1
)

"%JAVA_HOME%\bin\javac.exe" --release 21 -version >nul 2>&1
if errorlevel 1 (
    echo The JDK at "%JAVA_HOME%" cannot compile for Java 21.
    echo Install a Java 21 JDK, or set JAVA21_HOME to the Java 21 install folder.
    exit /b 1
)

if not exist "%NETBEANS_HOME%\extide\ant\bin\ant.bat" (
    echo NetBeans Ant was not found at "%NETBEANS_HOME%\extide\ant\bin\ant.bat".
    exit /b 1
)

> "%BUILD_PROPS%" echo libs.CopyLibs.classpath=%NETBEANS_HOME:\=/%/java/ant/extra/org-netbeans-modules-java-j2seproject-copylibstask.jar

set "PATH=%JAVA_HOME%\bin;%NETBEANS_HOME%\extide\ant\bin;%PATH%"
call "%NETBEANS_HOME%\extide\ant\bin\ant.bat" "-Duser.properties.file=%BUILD_PROPS%" clean jar
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
