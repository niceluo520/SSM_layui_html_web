package com.cbox.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int timeOut) {
        // Cookie[] cookies = request.getCookies();
        // Cookie cookie = null;
        // if (cookies != null) {
        // for (int i = 0; i < cookies.length; ++i) {
        // if (cookies[i].getName().equalsIgnoreCase(cookieKey)) {
        // cookie = cookies[i];
        // break;
        // }
        // }
        // }
        //
        // if (cookie == null) {
        // if (cookieValue != null) {
        // response.addHeader("Set-Cookie", cookieKey + "=" + cookieValue + ";Max-Age=" + timeOut + ";Path=/;HTTPOnly");
        // }
        // } else if (cookieValue != null) {
        // response.addHeader("Set-Cookie", cookieKey + "=" + cookieValue + ";Max-Age=" + timeOut + ";Path=/;HTTPOnly");
        // } else {
        // cookie.setValue(cookieValue);
        // cookie.setPath("/");
        // cookie.setMaxAge(timeOut);
        // response.addCookie(cookie);
        // }

        response.addHeader("Set-Cookie", cookieName + "=" + cookieValue + ";Max-Age=" + timeOut + ";Path=/;HTTPOnly");
    }

    public static String getCookie(HttpServletRequest req, String cookieName) {
        String cookieValue = "";
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; ++i) {
                if (cookieName.equals(cookies[i].getName())) {
                    cookieValue = cookies[i].getValue();
                }
            }
        }

        return cookieValue;
    }
}