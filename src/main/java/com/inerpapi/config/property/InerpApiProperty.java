package com.inerpapi.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("inerp")
public class InerpApiProperty{

    private final Seguranca seguranca = new Seguranca();
    private String originAllowed = "http://localhost:9000";

    public Seguranca getSeguranca() {
        return seguranca;
    }

    public static class Seguranca{

        private boolean enableHttps;

        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }

    public String getOriginAllowed() {
        return originAllowed;
    }

    public void setOriginAllowed(String originAllowed) {
        this.originAllowed = originAllowed;
    }
}