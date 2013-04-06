package ilink.security;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.AuthenticationServiceException;
import org.apache.log4j.Logger;
import ilink.utils.iLinkUtils;
import java.util.Vector;
import java.util.List;


public class iBatisAmdocsUserDetailsService extends SqlMapClientDaoSupport implements UserDetailsService {
    
 private static Logger log = Logger.getLogger(iBatisAmdocsUserDetailsService.class);

 public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
 {
    UserDetails user;
    AmdocsEmployee employee = (AmdocsEmployee)getSqlMapClientTemplate().queryForObject( "getAmdocsEmployee", userName );
    if(employee == null){
       throw new UsernameNotFoundException("User is not authorized");
    }
    //initActivityCode(employee);
    GrantedAuthority[] authArr = initGrantedAuthorities(employee);
    getManagerEmailByRepId(employee);
    String password = AmdocsAuthenticationProvider.getPasswordByUserName(userName);
    if(userName == null || password == null)
        throw new UsernameNotFoundException("Please login!");
    user = new AmdocsUserDetails(userName, password, true, true, true, true, authArr, employee);

    return user;
 }
 private void getManagerEmailByRepId(AmdocsEmployee employee)
 {
    String managerEmail = (String) getSqlMapClientTemplate().queryForObject("getManagerEmailByRepId", employee.getEmployeeNumber());
     if(managerEmail == null)
     {
        managerEmail = "";
     }
     employee.setManagerEmail(managerEmail);
 }
 private void initActivityCode(AmdocsEmployee employee)
 {
     List<String> employeeActivityCode =
         (List<String>) getSqlMapClientTemplate().queryForList("getAmdocsEmployeeActivityCode", employee.getEmployeeNumber());
     if(employeeActivityCode.size() > 0)
     {
         employee.setActivityCode(iLinkUtils.converListToString(employeeActivityCode, iLinkUtils.STR_DELIMETER));
     }
 }
 private GrantedAuthority[] initGrantedAuthorities(AmdocsEmployee employee)
 {
     String employeeId = employee.getEmployeeNumber();
     List<String> employeeSecProfile =
             (List<String>) getSqlMapClientTemplate().queryForList("getAmdocsEmployeeSecProfile", employeeId);
     List authorities = new Vector();
     GrantedAuthority[] toReturn = {new GrantedAuthorityImpl("demo")};

     if(employeeSecProfile.size() > 0)
     {
          employee.setSecurityProfile(iLinkUtils.converListToString(employeeSecProfile, iLinkUtils.STR_DELIMETER));
          if(iLinkUtils.checkSecurityProfilesList(employeeSecProfile))
          {
               GrantedAuthority newAuthority = new GrantedAuthorityImpl("ROLE_USER");
               authorities.add(newAuthority);
          }
         employee.setReadOnly(iLinkUtils.checkReadOnlySecurityProfilesList(employeeSecProfile));
     }
     if(authorities.isEmpty()){
        throw new UsernameNotFoundException("User is not authorized");
     }
     return (GrantedAuthority[]) authorities.toArray(toReturn);

 }
}
