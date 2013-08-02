package fr.plaisance.service;

import fr.plaisance.domaine.PerudoAction;
import fr.plaisance.domaine.Face;
import fr.plaisance.domaine.Game;
import fr.plaisance.domaine.Player;
import fr.plaisance.exception.ExpiredIdentifierException;
import fr.plaisance.exception.GameAlreadyStartedException;
import fr.plaisance.exception.NotEnoughPlayersException;

public interface GameService {
	
	Game lastGame();
	Game createGame();
	Game getById(Long gameId) throws ExpiredIdentifierException;
	void startGame(Game game) throws NotEnoughPlayersException, GameAlreadyStartedException;
	Boolean canStart(Game game);
	Boolean isStarted(Game game);
	Boolean isOver(Game game);
	void rollDice(Game game);
	Player previousPlayer(Game game);
	Player nextPlayer(Game game);
	Player activePlayer(Game game);
	Player winner(Game game);
	Integer countFaces(Game game, Face face);
	void clearDeclarations(Game game);
	void clearActions(Game game);
	PerudoAction lastAction(Game game);
	boolean isPaused(Game game);
}
