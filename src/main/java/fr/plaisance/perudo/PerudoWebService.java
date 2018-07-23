package fr.plaisance.perudo;

import fr.plaisance.domaine.*;
import fr.plaisance.exception.PerudoException;
import fr.plaisance.perudo.domaine.Game;
import fr.plaisance.perudo.domaine.Player;
import fr.plaisance.perudo.exception.PerudoException;
import fr.plaisance.perudo.service.GameService;
import fr.plaisance.perudo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static fr.plaisance.PerudoService.*;

@RestController("/game")
public class PerudoWebService {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

	@GetMapping(value = "/perudo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Document perudo(@RequestParam("game") Long gameId, @RequestParam("player") Long playerId)  {
		// System.out.println("perudo : game = " + gameId + ", player = " + playerId);
		Document document = null;
		try {
			Game game = gameService.getById(gameId);
			Player player = playerService.getById(playerId);
			playerService.check(player, game);
			if(gameService.isPaused(game)){
				document = xmlService().buildFullDocument(game, player);
			}
			else{
				document = xmlService().buildPlayerDocument(game, player);
			}
		}
		catch (PerudoException e) {
			document = xmlService().buildErrorDocument(e);
		}
		return document;
	}
	
	@GET
	@Path("/player")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlayer(@QueryParam("name") String playerName) {
		System.out.print("addPlayer : name = " + playerName);
		Game game = gameService.lastGame();
		String response = null;
		try {
			Player player = playerService.createPlayer(playerName, game);
			game.addMessage(PerudoMessageType.INFO, "Le joueur " + playerName + " a été ajouté à la partie.");
			response =  SUCCESS + ":" + player.getPlayerId() + ":" + game.getGameId();	
		}
		catch (PerudoException e) {
			game.addMessage(PerudoMessageType.ERROR, e.getMessage());
			response = ERROR + ":" + e.getMessage();
		}
		System.out.println(" -> " + response);
		return response;
	}
	
	@GET
	@Path("/roll")
	@Produces(MediaType.TEXT_PLAIN)
	public String rollDice(@QueryParam("player") Long playerId, @QueryParam("game") Long gameId) {
		System.out.print("rollDice : player = " + playerId);
		String response = null;
		try {
			Player player = playerService.getById(playerId);
			Game game = gameService.getById(gameId);
			PerudoResult result = game.getResult();
			switch (result.getType()) {
				case WIN:
					playerService.winDie(game, result.getPlayer());
					game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a gagné un dé.");
					break;
				case LOSE:
					playerService.loseDie(game, result.getPlayer());
					game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a perdu un dé.");
					break;
			}
			gameService.rollDice(game);
			gameService.clearActions(game);
			response =  SUCCESS + ":" + player.getPlayerId() + ":" + game.getGameId();	
		}
		catch (PerudoException e) {
			response = ERROR + ":" + e.getMessage();
		}
		System.out.println(" -> " + response);
		return response;
	}
	
	@GET
	@Path("/start")
	@Produces(MediaType.TEXT_PLAIN)
	public String startGame(@QueryParam("game") Long gameId) {
		System.out.print("startGame : game = " + gameId);
		String response = null;
		try {
			Game game = gameService.getById(gameId);
			gameService.startGame(game);
			gameService.rollDice(game);
			game.addMessage(PerudoMessageType.INFO, "La partie a débutée.");
			response = SUCCESS + ":" + gameId;
		}
		catch (PerudoException e) {
			response = ERROR + ":" + e.getMessage();
		}
		System.out.println(" -> " + response);
		return response;
	}
	
	@GET
	@Path("/end")
	@Produces(MediaType.TEXT_PLAIN)
	public String endGame(@QueryParam("game") Long gameId) {
		System.out.print("endGame : game = " + gameId);
		String response = null;
		try {
			Game game = gameService.getById(gameId);
			Player winner = gameService.winner(game);
			game.addMessage(PerudoMessageType.INFO, "La partie est terminée.");
			int dices = winner.getFaces().size();
			String playerName = winner.getPlayerName();
			if(dices == PerudoService.MAX_DICE){
				game.addMessage(PerudoMessageType.INFO, playerName + " a gagné avec tous ses dés ! Good game Sir.");
			}
			else if(dices == 1){
				game.addMessage(PerudoMessageType.INFO, playerName + " a gagné avec un dé. Borderline\u2026");
			}
			else{
				game.addMessage(PerudoMessageType.INFO, playerName + " a gagné avec " + dices + " dés !");
			}
			
			response = SUCCESS + ":" + playerName;
		}
		catch (PerudoException e) {
			response = ERROR + ":" + e.getMessage();
		}
		System.out.println(" -> " + response);
		return response;
	}
	
	@GET
	@Path("/bet")
	@Produces(MediaType.TEXT_PLAIN)
	public String bet(
		@QueryParam("game") Long gameId,
		@QueryParam("player") Long playerId,
		@QueryParam("number") Integer number,
		@QueryParam("face") Integer face
	) {
		System.out.print("bet : game = " + gameId + ", player = " + playerId + ", number = " + number + ", face = " + face);
		String response = null;
		try {
			Game game = gameService.getById(gameId);
			Player player = playerService.getById(playerId);
			Declaration declaration = declarationService().createDeclaration(face, number);
			playerService.bet(player, game, declaration);
			game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a enchéri : " + number + "x" + face + ".");
			response = SUCCESS + ":bet:" + player.getPlayerName() + ":" + number + ":" + face;
		}
		catch (PerudoException e) {
			response = ERROR + ":" + e.getMessage();
		}
		System.out.println(" -> " + response);
		return response;
	}
	
	@GET
	@Path("/dudo")
	@Produces(MediaType.TEXT_PLAIN)
	public String dudo(
		@QueryParam("game") Long gameId,
		@QueryParam("player") Long playerId
	) {
		System.out.print("dudo : game = " + gameId + ", player = " + playerId);
		String response = null;
		try {
			Game game = gameService.getById(gameId);
			Player player = playerService.getById(playerId);
			playerService.dudo(player, game);
			if(gameService.isOver(game)){
				game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a gagné la partie.");
				response = SUCCESS + ":over:" + gameService.winner(game).getPlayerName();
			}
			else{
				game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a dit Dudo.");
				response = SUCCESS + ":dudo:" + gameService.activePlayer(game).getPlayerName();
			}
		}
		catch (PerudoException e) {
			response = ERROR + ":" + e.getMessage();
		}
		System.out.println(" -> " + response);
		return response;
	}
	
	@GET
	@Path("/calza")
	@Produces(MediaType.TEXT_PLAIN)
	public String calza(
		@QueryParam("game") Long gameId,
		@QueryParam("player") Long playerId
	) {
		System.out.print("calza : game = " + gameId + ", player = " + playerId);
		String response = null;
		try {
			Game game = gameService.getById(gameId);
			Player player = playerService.getById(playerId);
			playerService.calza(player, game);
			if(gameService.isOver(game)){
				game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a gagné la partie.");
				response = SUCCESS + ":over:" + gameService.winner(game).getPlayerName();
			}
			else{
				game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a dit Calza.");
				response = SUCCESS + ":calza:" + player.getPlayerName();
			}
		}
		catch (PerudoException e) {
			response = ERROR + ":" + e.getMessage();
		}
		System.out.println(" -> " + response);
		return response;
	}
}
