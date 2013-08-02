package fr.plaisance;

import static fr.plaisance.PerudoService.declarationService;
import static fr.plaisance.PerudoService.gameService;
import static fr.plaisance.PerudoService.playerService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.Collections;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import fr.plaisance.domaine.Declaration;
import fr.plaisance.domaine.Face;
import fr.plaisance.domaine.Game;
import fr.plaisance.domaine.Perudo;
import fr.plaisance.domaine.Player;
import fr.plaisance.exception.InvalidDeclarationException;
import fr.plaisance.exception.PerudoException;
import fr.plaisance.exception.TooManyPlayersException;
import fr.plaisance.exception.WrongPlayerException;
import fr.plaisance.xml.PerudoXmlSimple;

public class PerudoTest {
	
	@Before
	public void setUp(){
		if(CollectionUtils.isNotEmpty(Perudo.getInstance().getGames())){
			Perudo.getInstance().getGames().clear();
		}
	}
	
	@Test
	public void testPerudo() throws PerudoException {
		
		Game game1 = gameService().createGame();
		assertEquals(1, Perudo.getInstance().getGames().size());
		assertEquals(Long.valueOf(1L), game1.getGameId());
		
		playerService().createPlayer("Player 1", game1);
		playerService().createPlayer("Player 2", game1);
		playerService().createPlayer("Player 3", game1);
		playerService().createPlayer("Player 4", game1);
		playerService().createPlayer("Player 5", game1);
		Player player1 = playerService().createPlayer("Player 6", game1);
		assertEquals(Long.valueOf(6L), player1.getPlayerId());
		assertEquals(5, player1.getFaces().size());
		
		try{
			playerService().createPlayer("Player 7", game1);
			fail();
		}
		catch(TooManyPlayersException e){
			
		}
		
		Game game2 = gameService().createGame();
		assertEquals(2, Perudo.getInstance().getGames().size());
		assertEquals(Long.valueOf(2L), game2.getGameId());
		
		Player player2 = playerService().createPlayer("Player 7", game2);
		assertEquals(Long.valueOf(7L), player2.getPlayerId());
		assertFalse(gameService().canStart(game2));
		
		playerService().createPlayer("Player 8", game2);
		assertTrue(gameService().canStart(game2));
		assertFalse(gameService().isStarted(game2));
		
		gameService().rollDice(game2);
		game2.getPlayers().get(0).setActive(true);
		assertTrue(gameService().isStarted(game2));
		
		Player first = gameService().activePlayer(game2);
		Player next = game2.getPlayers().get(1);
		
		Declaration declaration = declarationService().createDeclaration(Face.TWO, 1);	
		playerService().bet(first, game2, declaration);
		
		assertFalse(first.isActive());
		assertTrue(next.isActive());
		
		try{
			playerService().bet(first, game2, declaration);
			fail();
		}
		catch(WrongPlayerException e){
			
		}
		
		try{
			playerService().bet(next, game2, declaration);
			fail();
		}
		catch(InvalidDeclarationException e){
			
		}
		
		gameService().rollDice(game2);
		playerService().bet(next, game2, declarationService().createDeclaration(Face.TWO, 11));
		assertTrue(first.isActive());
		assertFalse(next.isActive());
		
		playerService().dudo(first, game2);
		assertFalse(first.isActive());
		assertTrue(next.isActive());
		assertEquals(5, first.getFaces().size());
		assertEquals(4, next.getFaces().size());
		
		first.getFaces().clear();
		Collections.addAll(first.getFaces(), Face.FIVE, Face.FIVE);
		next.getFaces().clear();
		next.setDeclaration(null);
		Collections.addAll(next.getFaces(), Face.FIVE, Face.PACO, Face.THREE);
		first.setActive(true);
		playerService().bet(first, game2, declarationService().createDeclaration(Face.FIVE, 4));
		playerService().calza(next, game2);
		assertFalse(first.isActive());
		assertTrue(next.isActive());
		assertEquals(2, first.getFaces().size());
		assertEquals(4, next.getFaces().size());
		
		gameService().clearDeclarations(game2);
		first.getFaces().clear();
		Collections.addAll(first.getFaces(), Face.FIVE, Face.FIVE);
		next.getFaces().clear();
		Collections.addAll(next.getFaces(), Face.FIVE, Face.PACO, Face.THREE);
		assertFalse(game2.isPalifico());
		
		first.setActive(true);
		playerService().bet(first, game2, declarationService().createDeclaration(Face.THREE, 4));
		playerService().dudo(next, game2);
		assertTrue(first.isActive());
		assertFalse(next.isActive());
		assertEquals(1, first.getFaces().size());
		assertEquals(3, next.getFaces().size());
		assertTrue(game2.isPalifico());
		
		gameService().clearDeclarations(game2);
		first.getFaces().clear();
		Collections.addAll(first.getFaces(), Face.FIVE);
		next.getFaces().clear();
		Collections.addAll(next.getFaces(), Face.FIVE, Face.PACO, Face.THREE);
		playerService().bet(first, game2, declarationService().createDeclaration(Face.FIVE, 3));
		playerService().dudo(next, game2);
		assertTrue(playerService().hasLost(first));
		assertTrue(gameService().isOver(game2));
	}
	
	@Test
	public void testXML() throws Exception {
		Game game = gameService().createGame();
		playerService().createPlayer("Player 7", game);
		
		playerService().createPlayer("Player 8", game);
		assertTrue(gameService().canStart(game));
		assertFalse(gameService().isStarted(game));
		
		gameService().startGame(game);
		gameService().rollDice(game);
		assertTrue(gameService().isStarted(game));
		
		PerudoXmlSimple.getInstance().buildFullDocument(game, game.getPlayers().get(0));
	}
	
	@Test
	public void testPath() throws Exception{
		URL url = this.getClass().getClassLoader().getResource("xml/perudo.xsd");
		File file = new File(url.getFile());
		System.out.println(file);
		
		System.out.println(PerudoUtil.message("exception.rookie.mistake"));
	}
}
