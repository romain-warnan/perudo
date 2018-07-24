package fr.plaisance.perudo;

import fr.plaisance.perudo.rest.GameConverter;
import fr.plaisance.perudo.rest.PlayerConverter;
import fr.plaisance.perudo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private GameConverter gameConverter;

    @Autowired
    private PlayerConverter playerConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(gameConverter);
        registry.addConverter(playerConverter);
    }
}
