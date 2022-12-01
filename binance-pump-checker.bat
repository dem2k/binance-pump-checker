@echo off
set classpath=%~dp0config;%~dp0target\classes
for %%i in ("%~dp0target\dependency\*.jar") do call :addcp %%i
java dem2k.BinancePumpChecker %*
goto ende
:addcp
set classpath=%1;%classpath%
:ende
