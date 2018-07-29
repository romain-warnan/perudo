package fr.plaisance.perudo.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.plaisance.perudo.PerudoUtil;
import fr.plaisance.perudo.domaine.*;
import fr.plaisance.perudo.exception.ExpiredIdentifierException;
import fr.plaisance.perudo.exception.GameAlreadyStartedException;
import fr.plaisance.perudo.exception.NotEnoughPlayersException;

import java.util.*;

@Service
public class GameService {
	
    private static final Random random = new Random(System.currentTimeMillis());

    @Autowired
    private PlayerService playerService;

    @Autowired
    private Perudo perudo;
	
	public Game createGame() {
		UUID gameId = UUID.randomUUID();
		Game game = new Game(gameId);
		game.setPalifico(false);
		perudo.addGame(game);
		return game;
	}

	public Boolean canStart(Game game) {
		if(CollectionUtils.isEmpty(game.getPlayers())){
			return false;
		}
		else{
			return game.getPlayers().size() > 1;
		}
	}

	public Boolean isStarted(Game game) {
		if(!this.canStart(game)){
			return false;
		}
        return game.getPlayers()
            .stream()
            .anyMatch(Player::isActive);
	}

	public Boolean isOver(Game game) {
		if(!this.canStart(game)){
			return false;
		}
        int totalPlayers = game.getPlayers().size();
        int lostPlayers = (int) game.getPlayers()
            .stream()
            .filter(playerService::hasLost)
            .count();
        return lostPlayers  > totalPlayers;
	}
	
	public void rollDice(Game game) {
		if(this.canStart(game)){
            game.getPlayers().forEach(playerService::rollDice);
		}
	}

	public Player activePlayer(Game game) {
		return game.getPlayers()
            .stream()
            .filter(Player::isActive)
            .findFirst()
            .orElse(null);
	}
	
	public Player previousPlayer(Game game) {
		Player active = this.activePlayer(game);
		if(active != null){
			List<Player> players = game.getPlayers();
			int index = players.indexOf(active);
			if(index > 0){
				for (int n = index - 1 ; n >= 0 ; n --){
					if(!playerService.hasLost(players.get(n))){
						return players.get(n);
					}
				}
			}
			if(index < players.size() - 1){
				for (int n = players.size() - 1 ; n > index ; n --){
					if(!playerService.hasLost(players.get(n))){
						return players.get(n);
					}
				}
			}

		}
		return null;
	}

	public Player nextPlayer(Game game) {
		Player active = this.activePlayer(game);
		if(active != null){
			List<Player> players = game.getPlayers();
			int index = players.indexOf(active);
			if(index < players.size() - 1){
				for(int n = index + 1 ; n < players.size() ; n ++){
					if(!playerService.hasLost(players.get(n))){
						return players.get(n);
					}
				}
			}
			if(index > 0){
				for(int n = 0 ; n < index ; n ++){
					if(!playerService.hasLost(players.get(n))){
						return players.get(n);
					}
				}
			}

		}
		return null;
	}

	public Integer countFaces(Game game, Face value) {
		return (int) game.getPlayers()
            .stream()
            .filter(player -> !playerService.hasLost(player))
            .flatMap(player -> player.getFaces().stream())
            .filter(face -> game.isPalifico() ? face == value || face == Face.PACO : face == value)
            .count();
	}

	public void clearDeclarations(Game game) {
        game.getPlayers().forEach(player -> player.setDeclaration(null));
	}

	public void clearActions(Game game) {
        game.getPlayers().forEach(player -> player.setAction(null));
	}

	public void startGame(Game game) throws NotEnoughPlayersException, GameAlreadyStartedException {
		if(this.canStart(game)){
			if(this.isStarted(game)){
				throw new GameAlreadyStartedException();
			}
			else{
				Collections.shuffle(game.getPlayers());
				game.getPlayers().get(0).setActive(true);
			}
		}
		else{
			throw new NotEnoughPlayersException();
		}
	}

	public Game lastGame() {
        return perudo.getGames()
            .stream()
            .filter(game -> !isStarted(game) && game.getPlayers().size() < PerudoUtil.MAX_PLAYER)
            .findFirst()
            .orElseGet(this::createGame);
	}

	public Game getById(UUID gameId) throws ExpiredIdentifierException {
		return perudo.getGames().stream()
            .filter(game -> game.getId().equals(gameId))
            .findFirst()
            .orElseThrow(ExpiredIdentifierException::new);
	}

	public Player winner(Game game) {
		if(isOver(game)){
            game.getPlayers()
                .stream()
                .filter(player -> !playerService.hasLost(player))
                .findFirst()
                .orElse(null);
		}
		return null;
	}

	public PerudoAction lastAction(Game game) {
		return game.getPlayers()
            .stream()
            .map(Player::getAction)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
	}

	public boolean isPaused(Game game) {
		PerudoAction lastAction = this.lastAction(game);
		return lastAction == PerudoAction.CALZA || lastAction == PerudoAction.DUDO;
	}
}
