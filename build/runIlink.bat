REM ===============================================================
REM Author:
REM Date:    
REM Description: Set all environment variable for the ilink project
REM Common parameters: 
REM  Supported Parameters:  build, deploy, clean, deploywar, install, reload, start, stop, list	
REM ==================================================================
SETLOCAL
call setIlinkEnv.cmd
%ANT_HOME%\bin\ant -buildfile buildIlink.xml %1

