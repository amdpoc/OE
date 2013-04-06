package ilink.utils;

import org.apache.log4j.Logger;

import java.util.*;

public class iLinkPropertiesLoader {

    private static Logger log = Logger.getLogger(iLinkPropertiesLoader.class);
    private static Properties applicationProperties;

    public iLinkPropertiesLoader(){}

    public void setApplicationProperties( Properties applicationProperties)
    {
          iLinkPropertiesLoader.applicationProperties = applicationProperties;
    }
    public  Properties getApplicationProperties()
    {
         return applicationProperties;
    }
    public  static String  getApplicationProperty(String key)
    {
           return  applicationProperties.getProperty(key);
    }
    public  static List getListApplicationProperty(String key)
    {
        String tokenizer = applicationProperties.getProperty("stringTokenizer");
        StringTokenizer valuesList = new StringTokenizer(applicationProperties.getProperty(key), tokenizer);
        Vector propList = new Vector();
        while(valuesList.hasMoreTokens())
        {
           propList.add(valuesList.nextToken());
        }
        return propList;
    }


} //END OF CLASS DEFINITION

