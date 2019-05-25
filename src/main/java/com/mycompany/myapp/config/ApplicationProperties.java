package com.mycompany.myapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Snorlax.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final Admin admin = new Admin();

    public ApplicationProperties() {}

    public Admin getAdmin() {
        return admin;
    }

    public static class Admin {
        private String mail = "";

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }
    }
}
