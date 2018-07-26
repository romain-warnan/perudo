package fr.plaisance.perudo.rest;

import fr.plaisance.perudo.domaine.Game;
import fr.plaisance.perudo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlayerConverter implements Converter<String, Game> {

    @Autowired
    private GameService gameService;

    @Override
    public Game convert(String playerId) {
        return gameService.getById(Long.valueOf(playerId));
    }
}
