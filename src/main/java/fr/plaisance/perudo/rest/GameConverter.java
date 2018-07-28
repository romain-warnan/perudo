package fr.plaisance.perudo.rest;

import fr.plaisance.perudo.domaine.Game;
import fr.plaisance.perudo.exception.ExpiredIdentifierException;
import fr.plaisance.perudo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameConverter implements Converter<String, Game> {

    @Autowired
    private GameService gameService;

    @Override
    public Game convert(String gameId) {
        return gameService.getById(UUID.fromString(gameId));
    }
}
