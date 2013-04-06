package ilink.security;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;


public interface UserAuthenticator {
       boolean authenticate(Authentication authentication) throws AuthenticationException;

}
