set akkahome=%AKKA_HOME%
set AKKA_HOME=
set SCRIPT_DIR=%~dp0
java -Xmx512M -jar "%SCRIPT_DIR%misc\sbt-launch-0.7.4.jar" %*
set AKKA_HOME=%akka_home%
