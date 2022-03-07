package fpt.com.fresher.recruitmentmanager.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SessionUtils {

    public static void putValue(HttpServletRequest request, String key, Object value, int expired) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(expired);
        session.setAttribute(key, value);
    }

    public static void putValue(HttpServletRequest request, String key, Iterable<Object> value, int expired) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(expired);
        session.setAttribute(key, value);
    }

    public static Object getValue(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    public static void removeValue(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

}
