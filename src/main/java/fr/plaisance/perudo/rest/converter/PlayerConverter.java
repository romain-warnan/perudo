package fr.plaisance.perudo.rest.converter;

import fr.plaisance.perudo.domaine.Game;
import fr.plaisance.perudo.domaine.Player;
import fr.plaisance.perudo.service.GameService;
import fr.plaisance.perudo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlayerConverter implements Converter<String, Player> {

    @Autowired
    private PlayerService playerService;

    @Override
    public Player convert(String playerId) {
        return playerService.getById(UUID.fromString(playerId));
    }
}
