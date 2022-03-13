package fpt.com.fresher.recruitmentmanager.object.oauth2.user;

import fpt.com.fresher.recruitmentmanager.object.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String oauth2ClientName, Map<String, Object> attributes) {

        if(oauth2ClientName.equalsIgnoreCase("GOOGLE")) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + oauth2ClientName + " is not supported yet.");
        }
    }
}
