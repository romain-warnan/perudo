package fr.plaisance.perudo.rest;

import fr.plaisance.perudo.PerudoUtil;
import fr.plaisance.perudo.domaine.*;
import fr.plaisance.perudo.exception.PerudoException;
import fr.plaisance.perudo.service.DeclarationService;
import fr.plaisance.perudo.service.GameService;
import fr.plaisance.perudo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PerudoController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private DeclarationService declarationService;

	@GetMapping(value = "/perudo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Game perudo(@RequestParam("game") Game game, @RequestParam("player") Player player)  {
        playerService.check(player, game);
        if(!gameService.isPaused(game)){
            // Retirer les dés des autres
        }
        return game;
	}

    @GetMapping(value = "/player", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Game addPlayer(@RequestParam("name") String playerName) {
		Game game = gameService.lastGame();
        Player player = playerService.createPlayer(playerName, game);
        game.addMessage(PerudoMessageType.INFO, "Le joueur " + playerName + " a été ajouté à la partie.");
        return game;
	}

    @GetMapping(value = "/roll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Game rollDice(@RequestParam("game") Game game, @RequestParam("player") Player player) {
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
        return game;
	}

    @GetMapping(value = "/start", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Game startGame(@RequestParam("game") Game game) {
        gameService.startGame(game);
        gameService.rollDice(game);
        game.addMessage(PerudoMessageType.INFO, "La partie a débutée.");
        return game;
    }

    @GetMapping(value = "/end", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Game endGame(@RequestParam("game") Game game) {
		String response = null;
        Player winner = gameService.winner(game);
        game.addMessage(PerudoMessageType.INFO, "La partie est terminée.");
        int dices = winner.getFaces().size();
        String playerName = winner.getPlayerName();
        if(dices == PerudoUtil.MAX_DICE){
            game.addMessage(PerudoMessageType.INFO, playerName + " a gagné avec tous ses dés ! Good game Sir.");
        }
        else if(dices == 1){
            game.addMessage(PerudoMessageType.INFO, playerName + " a gagné avec un dé. Borderline\u2026");
        }
        else{
            game.addMessage(PerudoMessageType.INFO, playerName + " a gagné avec " + dices + " dés !");
        }
		return game;
	}

    @GetMapping(value = "/bet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Game bet(@RequestParam("game") Game game, @RequestParam("player") Player player, @RequestParam("number") Integer number, @RequestParam("face") Face face) {
        Declaration declaration = declarationService.createDeclaration(face, number);
        playerService.bet(player, game, declaration);
        game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a enchéri : " + number + "x" + face + ".");
		return game;
	}

    @GetMapping(value = "/dudo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Game dudo(@RequestParam("game") Game game, @RequestParam("player") Player player) {
        playerService.dudo(player, game);
        if(gameService.isOver(game)){
            game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a gagné la partie.");
        }
        else{
            game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a dit Dudo.");
        }
        return game;
	}

    @GetMapping(value = "/calza", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Game calza(@RequestParam("game") Game game, @RequestParam("player") Player player) {
        playerService.calza(player, game);
        if(gameService.isOver(game)){
            game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a gagné la partie.");
        }
        else{
            game.addMessage(PerudoMessageType.INFO, player.getPlayerName() + " a dit Calza.");
        }
        return game;
	}
}
