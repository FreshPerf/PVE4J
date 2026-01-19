package fr.freshperf.pve4j;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SecurityConfig Tests")
class SecurityConfigTest {

    @Test
    @DisplayName("Should create secure configuration")
    void shouldCreateSecureConfiguration() {
        SecurityConfig config = SecurityConfig.secure();
        
        assertThat(config).isNotNull();
    }

    @Test
    @DisplayName("Should create insecure configuration")
    void shouldCreateInsecureConfiguration() {
        SecurityConfig config = SecurityConfig.insecure();
        
        assertThat(config).isNotNull();
    }

    @Test
    @DisplayName("Secure and insecure configs should be different instances")
    void secureAndInsecureConfigsShouldBeDifferentInstances() {
        SecurityConfig secure = SecurityConfig.secure();
        SecurityConfig insecure = SecurityConfig.insecure();
        
        assertThat(secure).isNotSameAs(insecure);
    }
}

