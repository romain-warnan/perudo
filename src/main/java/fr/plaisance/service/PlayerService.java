package fr.plaisance.service;

import fr.plaisance.domaine.Declaration;
import fr.plaisance.domaine.Game;
import fr.plaisance.domaine.Player;
import fr.plaisance.exception.ExpiredIdentifierException;
import fr.plaisance.exception.PerudoException;
import fr.plaisance.exception.TooManyPlayersException;

public interface PlayerService {

	Player createPlayer(String playerName, Game game) throws TooManyPlayersException;
	Player getById(Long playerId) throws ExpiredIdentifierException;
	Boolean hasLost(Player player);
	void rollDice(Player player);
	void winDie(Game game, Player player);
	void loseDie(Game game, Player player);
	void bet(Player player, Game game, Declaration declaration) throws PerudoException;
	void dudo(Player player, Game game) throws PerudoException;
	void calza(Player player, Game game) throws PerudoException;
	boolean check(Player player, Game game) throws PerudoException;
}
