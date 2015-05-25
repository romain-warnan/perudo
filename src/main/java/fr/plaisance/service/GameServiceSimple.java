package fr.plaisance.service;

import static fr.plaisance.PerudoService.playerService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

import fr.plaisance.PerudoService;
import fr.plaisance.domaine.Face;
import fr.plaisance.domaine.Game;
import fr.plaisance.domaine.Perudo;
import fr.plaisance.domaine.PerudoAction;
import fr.plaisance.domaine.Player;
import fr.plaisance.exception.ExpiredIdentifierException;
import fr.plaisance.exception.GameAlreadyStartedException;
import fr.plaisance.exception.NotEnoughPlayersException;

public class GameServiceSimple implements GameService{
	
	private static GameService instance = null;
	private Random random;
	
	private GameServiceSimple(){
		// Private constructor
		random = new Random(System.currentTimeMillis());
	}
	
	public static GameService getInstance(){
		if(instance == null){
			instance = new GameServiceSimple();
		}
		return instance;
	}
	
	@Override
	public Game createGame() {
		Long gameId = this.nextGameId();
		Game game = new Game(gameId);
		game.setPalifico(false);
		Perudo.getInstance().addGame(game);
		return game;
	}

	@Override
	public Boolean canStart(Game game) {
		if(CollectionUtils.isEmpty(game.getPlayers())){
			return false;
		}
		else{
			return game.getPlayers().size() > 1;
		}
	}

	@Override
	public Boolean isStarted(Game game) {
		if(this.canStart(game) == false){
			return false;
		}
		else{
			for (Player player : game.getPlayers()) {
				if(player.isActive()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Boolean isOver(Game game) {
		if(this.canStart(game) == false){
			return false;
		}
		else{
			int totalPlayers = game.getPlayers().size();
			int lostPlayers = 0;
			for (Player player : game.getPlayers()) {
				if(playerService().hasLost(player)){
					lostPlayers ++;
				}
			}
			return lostPlayers + 1 >= totalPlayers;
		}
	}
	
	@Override
	public void rollDice(Game game) {
		if(this.canStart(game)){
			for (Player player : game.getPlayers()) {
				playerService().rollDice(player);
			}	
		}
	}
	
	private Long nextGameId(){
		Long id = null;
		while(id == null || this.alreadyExists(id)){
			id = Math.abs(random.nextLong());
		}
		return id;
	}

	private boolean alreadyExists(Long id){
		if(CollectionUtils.isEmpty(Perudo.getInstance().getGames())){
			return false;
		}
		else{
			for (Game game : Perudo.getInstance().getGames()) {
				if(game.getGameId().equals(id)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Player activePlayer(Game game) {
		for (Player player : game.getPlayers()) {
			if(player.isActive()){
				return player;
			}
		}
		return null;
	}
	
	@Override
	public Player previousPlayer(Game game) {
		Player active = this.activePlayer(game);
		if(active != null){
			List<Player> players = game.getPlayers();
			int index = players.indexOf(active);
			if(index > 0){
				for(int n = index - 1 ; n >= 0 ; n --){
					if(playerService().hasLost(players.get(n)) == false){
						return players.get(n);
					}
				}
			}
			if(index < players.size() - 1){
				for(int n = players.size() - 1 ; n > index ; n --){
					if(playerService().hasLost(players.get(n)) == false){
						return players.get(n);
					}
				}
			}

		}
		return null;
	}

	@Override
	public Player nextPlayer(Game game) {
		Player active = this.activePlayer(game);
		if(active != null){
			List<Player> players = game.getPlayers();
			int index = players.indexOf(active);
			if(index < players.size() - 1){
				for(int n = index + 1 ; n < players.size() ; n ++){
					if(playerService().hasLost(players.get(n)) == false){
						return players.get(n);
					}
				}
			}
			if(index > 0){
				for(int n = 0 ; n < index ; n ++){
					if(playerService().hasLost(players.get(n)) == false){
						return players.get(n);
					}
				}
			}

		}
		return null;
	}

	@Override
	public Integer countFaces(Game game, Face value) {
		int total = 0;
		for (Player player : game.getPlayers()) {
			if(playerService().hasLost(player) == false){
				for (Face face : player.getFaces()) {
					if(game.isPalifico()){
						total = (face == value ? total + 1 : total);
					}
					else{
						total = (face == value || face == Face.PACO ? total + 1 : total);
					}
				}
			}
		}
		return total;
	}

	@Override
	public void clearDeclarations(Game game) {
		for (Player player : game.getPlayers()) {
			player.setDeclaration(null);
		}
	}
	
	@Override
	public void clearActions(Game game) {
		for (Player player : game.getPlayers()) {
			player.setAction(null);
		}
	}

	@Override
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

	@Override
	public Game lastGame() {
		if(CollectionUtils.isNotEmpty(Perudo.getInstance().getGames())){
			for (Game game : Perudo.getInstance().getGames()) {
				if(isStarted(game) == false && game.getPlayers().size() < PerudoService.MAX_PLAYER){
					return game;
				}
			}
		}
		return createGame();
	}

	@Override
	public Game getById(Long gameId) throws ExpiredIdentifierException {
		if(CollectionUtils.isEmpty(Perudo.getInstance().getGames())){
			throw new ExpiredIdentifierException();
		}
		for(Game game : Perudo.getInstance().getGames()){
			if(game.getGameId().equals(gameId)){
				return game;
			}
		}
		throw new ExpiredIdentifierException();
	}

	@Override
	public Player winner(Game game) {
		if(isOver(game)){
			for (Player player : game.getPlayers()) {
				if(playerService().hasLost(player) == false){
					return player;
				}
			}
		}
		return null;
	}

	@Override
	public PerudoAction lastAction(Game game) {
		for (Player player : game.getPlayers()) {
			if(player.getAction() != null){
				return player.getAction();
			}
		}
		return null;
	}

	@Override
	public boolean isPaused(Game game) {
		PerudoAction lastAction = this.lastAction(game);
		return lastAction == PerudoAction.CALZA || lastAction == PerudoAction.DUDO;
	}
}
