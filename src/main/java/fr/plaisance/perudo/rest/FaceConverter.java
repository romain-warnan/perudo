package fr.plaisance.perudo.rest;

import fr.plaisance.perudo.domaine.Face;
import fr.plaisance.perudo.domaine.Game;
import fr.plaisance.perudo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FaceConverter implements Converter<Integer, Face> {

    @Override
    public Face convert(Integer integer) {
        return Face.of(integer);
    }
}
