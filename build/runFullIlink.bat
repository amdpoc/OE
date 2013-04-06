REM ===============================================================
REM Author:
REM Date:    
REM Description: Set all environment variable for the ilink project
REM Common parameters: 
REM  Supported Parameters:  build, deploy, clean, deploywar, install, reload, start, stop, list	
REM ==================================================================
call runIlink clean
call runIlink deploy
call runIlink reload
