echo off
set JAVA_MODULE_PATH=".\javafx-sdk-23.0.1\lib"
set JAVA_OPTS=--module-path %JAVA_MODULE_PATH% --add-modules javafx.controls,javafx.graphics,javafx.fxml,javafx.base
java %JAVA_OPTS% -jar UTBM_Dice.jar