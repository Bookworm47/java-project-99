package hexlet.code.app.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
/**
 * Configuration class for Jackson settings.
 */
@Configuration
public class JacksonConfig {

    /**
     * Provides a Jackson2ObjectMapperBuilder bean.
     * <p>
     * This method can be overridden to customize the Jackson ObjectMapper builder.
     *
     * @return a Jackson2ObjectMapperBuilder instance
     */
    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .modulesToInstall(new JsonNullableModule());
        return builder;
    }

    /**
     * Provides an ObjectMapper bean.
     * <p>
     * This method can be overridden to customize the Jackson ObjectMapper.
     *
     * @param objectMapperBuilder the Jackson2ObjectMapperBuilder instance
     * @return an ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder objectMapperBuilder) {
        return objectMapperBuilder.build();
    }

}
