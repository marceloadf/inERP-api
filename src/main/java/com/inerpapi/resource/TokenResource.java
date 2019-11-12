package com.inerpapi.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inerpapi.config.property.InerpApiProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokenResource{

    @Autowired
    private InerpApiProperty inerpApiProperty;

    @DeleteMapping("/revoke")
    public void revoke(HttpServletRequest req, HttpServletResponse res){
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(inerpApiProperty.getSeguranca().isEnableHttps());
        cookie.setPath("/oauth/token");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        res.setStatus(HttpStatus.NO_CONTENT.value());
    }
}