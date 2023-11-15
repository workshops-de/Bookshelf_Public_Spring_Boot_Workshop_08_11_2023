package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI api(BookshelfProperties properties) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(properties.getName())
                                .version(properties.getVersion())
                                .description("%s. It has a capacity of %d, books".formatted(properties.getDescription(), properties.getCapacity()))
                                .license(new License()
                                        .name(properties.getLicense().getName())
                                        .url(properties.getLicense().getUrl().toString())
                                )
                );
    }
}
