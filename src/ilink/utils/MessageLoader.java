package ilink.utils;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;


public class MessageLoader {

    public static ResourceBundleMessageSource messageSource;

    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        MessageLoader.messageSource = messageSource;
    }

    public static synchronized String getMessage(String errorKey, Object[] params){
           return  messageSource.getMessage(errorKey, params,Locale.getDefault());
    }

}
