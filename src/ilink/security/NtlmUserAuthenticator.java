package ilink.security;

import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.util.Assert;
import org.apache.log4j.Logger;
import java.util.Properties;
import java.util.Enumeration;
import java.net.UnknownHostException;
import jcifs.Config;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbSession;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;
import ilink.utils.MessageLoader;


public class NtlmUserAuthenticator implements UserAuthenticator
{
    private static Logger log = Logger.getLogger(NtlmUserAuthenticator.class);
    private boolean authenticatorFlag;
    
    public NtlmUserAuthenticator()
    {
        log.debug("####### NtlmUserAuthenticator - constructor##########");
    }
    public void setAuthenticatorFlag(boolean authenticatorFlag)
    {
       this.authenticatorFlag = authenticatorFlag;
    }
    public boolean getAuthenticatorFlag()
     {
       return this.authenticatorFlag;
     }
    public void setJcifsProperties(Properties props)
    {
        log.debug("####### NtlmUserAuthenticator - setJcifsProperties##########");
        Assert.notNull(props, "A JcifsProperties must be set");

        for(Enumeration e=props.keys(); e.hasMoreElements();) {
            String name = (String) e.nextElement();
            if(name.startsWith("jcifs.")) {
                Config.setProperty(name, props.getProperty(name));
            }
        }
         // Default to 5 minutes
        Config.setProperty("jcifs.smb.client.soTimeout", "300000");
        // Default to 20 minutes
        Config.setProperty("jcifs.netbios.cachePolicy", "1200");
    }
    public boolean authenticate(Authentication authentication) throws AuthenticationException
    {
        log.debug("####### NtlmUserAuthenticator - authenticate()##########");
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                            "NtlmUserAuthenticator: Only UsernamePasswordAuthenticationToken is supported");

        boolean status = false;

        if(authenticatorFlag)
        {
        try {
             String hostName = Config.getProperty("jcifs.http.domainController");
             log.debug("######### Attempting to contact server: " + hostName);

             UniAddress domainController = UniAddress.getByName(hostName, true);
             log.debug("######### Got domainController: " + domainController.toString());
             String domain = Config.getProperty("jcifs.smb.client.domain");
             NtlmPasswordAuthentication credentials =
                       new NtlmPasswordAuthentication(domain, authentication.getName(),
                                                      (String)authentication.getCredentials());

             log.debug("######### Attempting log into domain: " + domain + " as " + authentication.getName());
             SmbSession.logon(domainController, credentials);
             status = true;
             log.debug("######### " + credentials + " successfully authenticated against " + domainController);

           } catch (UnknownHostException ex) {
                   log.error("NtlmUserAuthenticator: UnknownHostException exception:" + ex.getMessage());
                   throw new AuthenticationServiceException(MessageLoader.getMessage("ERROR_AUTHENTICATION_ERROR", null));

               } catch (SmbAuthException ex) {
                   log.error("NtlmUserAuthenticator: BadCredentialsException exception:" + ex.getMessage());
                   throw new BadCredentialsException(ex.getMessage());

               } catch (SmbException ex) {
                   log.error("NtlmUserAuthenticator: AuthenticationServiceException exception:" + ex.getMessage());
                   throw new AuthenticationServiceException(MessageLoader.getMessage("ERROR_AUTHENTICATION_ERROR", null));
               }
        }else
        {
            log.debug("NtlmUserAuthenticator: Authentication skipped due to authenticatorFlag = " + authenticatorFlag);
            status = true;
        }
        return  status;
    }
}
