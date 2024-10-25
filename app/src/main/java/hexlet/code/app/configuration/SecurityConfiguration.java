package hexlet.code.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class for security settings.
 */

@Configuration
public  class SecurityConfiguration {

    /**
     * Provides a PasswordEncoder bean.
     * <p>
     * This method can be overridden to provide a custom PasswordEncoder.
     *
     * @return a PasswordEncoder instance
     */

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a WebSecurityCustomizer bean.
     * <p>
     * This method can be overridden to customize web security settings.
     *
     * @return a WebSecurityCustomizer instance
     */

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/**"));
    }
}
