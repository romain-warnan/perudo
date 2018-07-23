package fr.plaisance.perudo.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.plaisance.perudo.PerudoUtil;
import fr.plaisance.perudo.domaine.*;
import fr.plaisance.perudo.exception.*;

import java.util.Objects;
import java.util.Random;

@Service
public class PlayerService {

    @Autowired
    private GameService gameService;

    @Autowired
    private DeclarationService declarationService;

	private static final Random random = new Random(System.currentTimeMillis());

	public Player createPlayer(String playerName, Game game) throws TooManyPlayersException {

		if(CollectionUtils.isEmpty(game.getPlayers()) || game.getPlayers().size() < PerudoUtil.MAX_PLAYER){
			Long playerId = this.nextPlayerId();
			Player player = new Player(playerId);
			player.setPlayerName(playerName);
			player.setActive(false);
			player.setAction(null);
			player.setColor(this.nextColor());
			for(int n = 0 ; n < 5 ; n ++){
				player.addFace(Face.PACO);
			}
			game.addPlayer(player);
			return player;
		}
		else{
			throw new TooManyPlayersException();
		}	
	}

	public Boolean hasLost(Player player) {
		return CollectionUtils.isEmpty(player.getFaces());
	}

	public void rollDice(Player player) {
		int nbFaces = player.getFaces().size();
		player.getFaces().clear();
		for(int n = 0 ; n < nbFaces ; n ++){
			int number = PerudoUtil.random(1, 6);
			for (Face face : Face.values()) {
				if(number == face.getValue()){
					player.getFaces().add(face);
				}
			}
		}
	}
	
	public void loseDie(Game game, Player player){
		game.setPalifico(false);
		if(CollectionUtils.isNotEmpty(player.getFaces())){
			if(player.getFaces().size() == 2){
				game.setPalifico(true);
			}
			Face face = player.getFaces().iterator().next();
			player.getFaces().remove(face);	
		}
	}
	
	public void winDie(Game game, Player player){
		game.setPalifico(false);
		if(CollectionUtils.isNotEmpty(player.getFaces()) && player.getFaces().size() < PerudoUtil.MAX_DICE){
			Face face = Face.PACO;
			player.addFace(face);
		}
	}
	
	private Long nextPlayerId(){
		Long id = null;
		while(id == null || this.alreadyExists(id)){
			id = Math.abs(random.nextLong());
		}
		return id;
	}
	
	private PerudoColor nextColor(){
		PerudoColor color = null;
		while(color == null || this.alreadyUsed(color)){
			int n = PerudoUtil.random(1, 6);
			color = PerudoColor.values()[n - 1];
		}
		return color;
	}

	private boolean alreadyExists(Long id){
		if(CollectionUtils.isEmpty(Perudo.getInstance().getGames())){
			return false;
		}
		else{
			for (Game game : Perudo.getInstance().getGames()) {
				if(CollectionUtils.isNotEmpty(game.getPlayers())){
					for (Player player : game.getPlayers()) {
						if(player.getPlayerId().equals(id)){
							return true;
						}
					}	
				}
			}
		}
		return false;
	}
	
	private boolean alreadyUsed(PerudoColor color){
		if(CollectionUtils.isEmpty(Perudo.getInstance().getGames())){
			return false;
		}
		else{
			for (Game game : Perudo.getInstance().getGames()) {
				if(CollectionUtils.isNotEmpty(game.getPlayers())){
					for (Player player : game.getPlayers()) {
						if(player.getColor() == color){
							return true;
						}
					}	
				}
			}
		}
		return false;
	}
	
	public void bet(Player player, Game game, Declaration declaration) throws PerudoException {
		if(gameService.isStarted(game)){
			if(Objects.equals(gameService.activePlayer(game), player)){
				Declaration previousDeclaration = null;
				Player previousPlayer = gameService.previousPlayer(game);
				player.setAction(PerudoAction.BET);
				player.setDeclaration(declaration);
				if(previousPlayer != null){
					previousDeclaration = previousPlayer.getDeclaration();
					previousPlayer.setAction(null);
				}
				
				if(declarationService.isAllowed(declaration, previousDeclaration, game.isPalifico())){
					gameService.nextPlayer(game).setActive(true);
					player.setActive(false);
				}
				else{
					throw new InvalidDeclarationException();
				}
			}
			else{
				throw new WrongPlayerException();
			}
		}
		else{
			throw new GameNotStartedException();
		}
	}

	public void dudo(Player player, Game game) throws PerudoException {
		if(gameService.isStarted(game)){
			if(Objects.equals(gameService.activePlayer(game), player)){
				player.setAction(PerudoAction.DUDO);
				Player previousPlayer = gameService.previousPlayer(game);
				if(previousPlayer == null){
					throw new RookieMistakeException();
				}
				else{
					previousPlayer.setAction(null);
					Declaration declaration = previousPlayer.getDeclaration();
					if(declaration == null){
						throw new RookieMistakeException();
					}
					else{
						Face face = declaration.getFace();
						int total = gameService.countFaces(game, face);
						if(declaration.getNumber() > total){
							previousPlayer.setActive(true);
							player.setActive(false);
							//this.loseDie(game, previousPlayer);
							game.setResult(PerudoResultType.LOSE, previousPlayer);
						}
						else{
							player.setActive(true);
							previousPlayer.setActive(false);
							//this.loseDie(game, player);
							game.setResult(PerudoResultType.LOSE, player);
						}
						gameService.clearDeclarations(game);
					}
				}
			}
			else{
				throw new WrongPlayerException();
			}
		}
		else{
			throw new GameNotStartedException();
		}
	}

	public void calza(Player player, Game game) throws PerudoException {
		if(gameService.isStarted(game)){
			player.setAction(PerudoAction.CALZA);
			Player previousPlayer = gameService.previousPlayer(game);
			if(previousPlayer == null){
				throw new RookieMistakeException();
			}
			else{
				previousPlayer.setAction(null);
				Declaration declaration = previousPlayer.getDeclaration();
				if(declaration == null){
					throw new RookieMistakeException();
				}
				else{
					Face face = declaration.getFace();
					int total = gameService.countFaces(game, face);
					player.setActive(true);
					if(declaration.getNumber() == total){	
						//this.winDie(game, player);
						game.setResult(PerudoResultType.WIN, player);
					}
					else{
						//this.loseDie(game, player);
						game.setResult(PerudoResultType.LOSE, player);
					}
					gameService.clearDeclarations(game);
				}
			}
		}
		else{
			throw new GameNotStartedException();
		}
	}

	public Player getById(Long playerId) throws ExpiredIdentifierException {
		for(Game game : Perudo.getInstance().getGames()){
			for (Player player : game.getPlayers()) {
				if(player.getPlayerId().equals(playerId)){
					return player;
				}
			}
		}
		throw new ExpiredIdentifierException();
	}

	public boolean check(Player player, Game game) throws PerudoException {
		for (Player item : game.getPlayers()) {
			if(item.getPlayerId().equals(player.getPlayerId())){
				return true;
			}
		}
		throw new ExpiredIdentifierException();
	}
}
