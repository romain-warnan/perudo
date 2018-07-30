package fr.plaisance.perudo;

import fr.plaisance.perudo.rest.converter.FaceConverter;
import fr.plaisance.perudo.rest.converter.GameConverter;
import fr.plaisance.perudo.rest.converter.PlayerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private GameConverter gameConverter;

    @Autowired
    private PlayerConverter playerConverter;

    @Autowired
    private FaceConverter faceConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(gameConverter);
        registry.addConverter(playerConverter);
        registry.addConverter(faceConverter);
    }
}
