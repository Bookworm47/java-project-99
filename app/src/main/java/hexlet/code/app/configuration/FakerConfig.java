package hexlet.code.app.configuration;


import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Faker settings.
 */
@Configuration
public class FakerConfig {

    /**
     * Provides a Faker bean.
     * <p>
     * This method can be overridden to provide a custom Faker instance.
     *
     * @return a Faker instance
     */
    @Bean
    public Faker faker() {
        return new Faker();
    }
}
