package ilink.security;

import org.springframework.security.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.util.Assert;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Collections;
import java.util.TreeMap;

import ilink.utils.MessageLoader;
import ilink.utils.TrackingManager;


public class AmdocsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{
    private UserDetailsService userDetailsService;
    private UserAuthenticator userAuthenticator;
    private TrackingManager trackMngr;
    private static Map cridentialsStorage = null;
    private static Logger log = Logger.getLogger(AmdocsAuthenticationProvider.class);

    public AmdocsAuthenticationProvider(){
        log.debug("#########  AmdocsAuthenticationProvider - default constructor #########");
        if(cridentialsStorage == null){
           cridentialsStorage = Collections.synchronizedMap(new TreeMap<String, String>());
        }
    }
    public AmdocsAuthenticationProvider(UserDetailsService userDetailsService,
                                        UserAuthenticator userAuthenticator){
        log.debug("#########  AmdocsAuthenticationProvider - constructor #########");
        init(userDetailsService, userAuthenticator);
        if(cridentialsStorage == null){
           cridentialsStorage = Collections.synchronizedMap(new TreeMap<String, String>());
        }
    }
    public void init(UserDetailsService userDetailsService ,UserAuthenticator userAuthenticator){
        log.debug("#########  AmdocsAuthenticationProvider - init #########");
        Assert.notNull(userDetailsService, "A userDetailsService must be set");
        Assert.notNull(userAuthenticator, "A userAuthenticator must be set");
        this.userDetailsService  = userDetailsService;
        this.userAuthenticator  = userAuthenticator;
     }

      protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException
      {
           log.debug("#########  AmdocsAuthenticationProvider - retrieveUser #########");
           UserDetails loadedUser;
           try {
                // authenticate user
                userAuthenticator.authenticate(authentication);
                String userName = (String)authentication.getPrincipal();
                String password = (String)authentication.getCredentials();
                addUser(userName,password);
                // populate a authentication object with authorities.
                loadedUser  = userDetailsService.loadUserByUsername(userName);
                trackMngr.logMessage(username, TrackingManager.TransType.PSTWS, "login", TrackingManager.MsgType.TXT,
                                     "User: " + username + " was logged in to ipad application.");
                log.info("#########  User: " + username + " was logged in to ipad application. #########");
        }
          catch (UsernameNotFoundException ex) {
              log.error("##############  User:" + username + " exception ->" + ex.getMessage());
              trackMngr.logMessage(username, TrackingManager.TransType.ERRWS, "login", TrackingManager.MsgType.TXT, ex.getMessage());

              throw new AuthenticationServiceException(ex.getMessage());
          }
           catch (Exception ex) {
              log.error("##############  exception -> " + ex.getMessage());
               trackMngr.logMessage(username, TrackingManager.TransType.ERRWS, "login", TrackingManager.MsgType.TXT, ex.getMessage());

              throw new AuthenticationServiceException(MessageLoader.getMessage("ERROR_AUTHENTICATION_ERROR", null));   
          }
          if (loadedUser == null){
              trackMngr.logMessage( username, TrackingManager.TransType.ERRWS, "login", TrackingManager.MsgType.TXT,
                                    "User: " + username + " was not able to pass authentication." );

              throw new AuthenticationServiceException(MessageLoader.getMessage("ERROR_LOGIN_USER", null));
          }
              return loadedUser;
    }

    public UserDetailsService getUserDetailsService() {
      log.debug("#########  AmdocsAuthenticationProvider - getUserDetailsService() #########");
      return userDetailsService;
    }
    public void setUserDetailsService(UserDetailsService userDetailsService) {
      log.debug("#########  AmdocsAuthenticationProvider - setUserDetailsService() #########");
      this.userDetailsService = userDetailsService;
    }

    public UserAuthenticator getUserAuthenticator() {
      log.debug("#########  AmdocsAuthenticationProvider - getUserAuthenticator() #########");
      return userAuthenticator;
    }
    public void setUserAuthenticator(UserAuthenticator userAuthenticator) {
      log.debug("#########  AmdocsAuthenticationProvider - setUserAuthenticator() #########");
      this.userAuthenticator = userAuthenticator;
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)throws AuthenticationException{
       //no additional passwords checks
       log.debug("#########  AmdocsAuthenticationProvider - additionalAuthenticationChecks() #########");
    }

    public static void addUser(String user, String password){
        cridentialsStorage.put(user, password);
    }
    public static String getPasswordByUserName(String user){
        return (String)cridentialsStorage.get(user);
    }

    public TrackingManager getTrackMngr() {
        return trackMngr;
    }

    public void setTrackMngr(TrackingManager trackMngr) {
        this.trackMngr = trackMngr;
    }
}