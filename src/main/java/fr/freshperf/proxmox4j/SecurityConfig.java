package fr.freshperf.proxmox4j;

/**
 * Configuration for SSL/TLS security settings when connecting to Proxmox API.
 * Use the builder pattern to customize security verification.
 */
public class SecurityConfig {
    
    private final boolean verifySslCertificate;
    private final boolean verifyHostname;
    
    private SecurityConfig(Builder builder) {
        this.verifySslCertificate = builder.verifySslCertificate;
        this.verifyHostname = builder.verifyHostname;
    }
    
    public boolean shouldVerifySslCertificate() {
        return verifySslCertificate;
    }
    
    public boolean shouldVerifyHostname() {
        return verifyHostname;
    }
    
    /**
     * Creates a builder for SecurityConfig with all security checks enabled by default.
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Creates a SecurityConfig with all security checks enabled (secure/production mode).
     */
    public static SecurityConfig secure() {
        return builder().build();
    }
    
    /**
     * Creates a SecurityConfig with all security checks disabled (insecure/development mode).
     * WARNING: Only use this in development/testing environments!
     */
    public static SecurityConfig insecure() {
        return builder()
                .disableSslVerification()
                .disableHostnameVerification()
                .build();
    }
    
    public static class Builder {
        private boolean verifySslCertificate = true;
        private boolean verifyHostname = true;
        
        private Builder() {
        }
        
        /**
         * Disables SSL certificate verification.
         * WARNING: This makes the connection vulnerable to man-in-the-middle attacks!
         */
        public Builder disableSslVerification() {
            this.verifySslCertificate = false;
            return this;
        }
        
        /**
         * Disables hostname verification.
         * WARNING: This allows connections to servers with mismatched hostnames!
         */
        public Builder disableHostnameVerification() {
            this.verifyHostname = false;
            return this;
        }
        
        /**
         * Enables SSL certificate verification (default).
         */
        public Builder enableSslVerification() {
            this.verifySslCertificate = true;
            return this;
        }
        
        /**
         * Enables hostname verification (default).
         */
        public Builder enableHostnameVerification() {
            this.verifyHostname = true;
            return this;
        }
        
        /**
         * Disables all security checks (equivalent to insecure()).
         * WARNING: Only use this if you know what you are doing!
         */
        public Builder disableAll() {
            this.verifySslCertificate = false;
            this.verifyHostname = false;
            return this;
        }
        
        public SecurityConfig build() {
            return new SecurityConfig(this);
        }
    }
}

