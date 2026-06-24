@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot"
set "NETBEANS_HOME=C:\Program Files\NetBeans 8.2"
set "BUILD_PROPS=%TEMP%\chromispos-netbeans-build.properties"

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo Java 17 was not found at "%JAVA_HOME%".
    exit /b 1
)

if not exist "%NETBEANS_HOME%\extide\ant\bin\ant.bat" (
    echo NetBeans Ant was not found at "%NETBEANS_HOME%\extide\ant\bin\ant.bat".
    exit /b 1
)

> "%BUILD_PROPS%" echo libs.CopyLibs.classpath=%NETBEANS_HOME:\=/%/java/ant/extra/org-netbeans-modules-java-j2seproject-copylibstask.jar

set "PATH=%JAVA_HOME%\bin;%NETBEANS_HOME%\extide\ant\bin;%PATH%"
call "%NETBEANS_HOME%\extide\ant\bin\ant.bat" "-Duser.properties.file=%BUILD_PROPS%" clean jar
