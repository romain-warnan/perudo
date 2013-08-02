package fr.plaisance.xml;

import static fr.plaisance.PerudoService.gameService;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import fr.plaisance.domaine.Face;
import fr.plaisance.domaine.Game;
import fr.plaisance.domaine.PerudoMessage;
import fr.plaisance.domaine.Player;

public class PerudoXmlSimple implements PerudoXml{

	private static PerudoXml instance = null;
	
	private PerudoXmlSimple(){
		// Singleton
	}
	
	public static PerudoXml getInstance(){
		if(instance == null){
			instance = new PerudoXmlSimple();
		}
		return instance;
	}
	
	@Override
	public Document buildFullDocument(Game game, Player player) throws SAXException, ParserConfigurationException, IOException {
		return buildDocument(game, player, true);
	}
	
	@Override
	public Document buildPlayerDocument(Game game, Player player) throws SAXException, ParserConfigurationException, IOException {
		return buildDocument(game, player, false);
	}
	
	private Document buildDocument(Game game, Player current, Boolean full) throws SAXException, ParserConfigurationException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();		
		
		Element gameElement = document.createElement("game");
		
		gameElement.setAttribute("palifico", (game.isPalifico() ? "true" : "false"));
		gameElement.setAttribute("ready", (gameService().canStart(game) ? "true" : "false"));
		gameElement.setAttribute("started", (gameService().isStarted(game) ? "true" : "false"));
		gameElement.setAttribute("over", (gameService().isOver(game) ? "true" : "false"));
		gameElement.setAttribute("paused", (gameService().isPaused(game)  ? "true" : "false"));
		document.appendChild(gameElement);
		
		Element gameIdElement = document.createElement("gameId");
		gameElement.appendChild(gameIdElement);
		Text gameIdText = document.createTextNode(game.getGameId().toString());
		gameIdElement.appendChild(gameIdText);
		
		Element messagesElement = document.createElement("messages");
		gameElement.appendChild(messagesElement);
		
		for (PerudoMessage message : game.getMessages()) {
			
			Element messageElement = document.createElement("message");
			Text messageText = document.createTextNode(message.getMessage());
			messageElement.appendChild(messageText);
			messageElement.setAttribute("type", message.getType());
			messagesElement.appendChild(messageElement);
		}
		
		Element playersElement = document.createElement("players");
		gameElement.appendChild(playersElement);
		
		for (Player player : game.getPlayers()) {
			Element playerElement = document.createElement("player");
			playerElement.setAttribute("active", (player.isActive() ? "true" : "false"));
			playerElement.setAttribute("color", player.getColor().getColorId());
			if(player.getAction() != null){
				playerElement.setAttribute("action", player.getAction().action());
			}
			playersElement.appendChild(playerElement);
			
			Element playerIdElement = document.createElement("playerId");
			playerElement.appendChild(playerIdElement);
			
			if(current.getPlayerId() == player.getPlayerId()){
				Text playerIdText = document.createTextNode(player.getPlayerId().toString());
				playerIdElement.appendChild(playerIdText);
			}
			
			Element playerNameElement = document.createElement("playerName");
			playerElement.appendChild(playerNameElement);
			Text playerNameText = document.createTextNode(player.getPlayerName());
			playerNameElement.appendChild(playerNameText);
			
			Element playerRankElement = document.createElement("playerRank");
			playerElement.appendChild(playerRankElement);
			Text playerRankText = document.createTextNode("" + game.getPlayers().indexOf(player));
			playerRankElement.appendChild(playerRankText);
			
			if(player.getDeclaration() != null){
				Element declarationElement = document.createElement("declaration");
				playerElement.appendChild(declarationElement);
				
				Element numberElement = document.createElement("number");
				declarationElement.appendChild(numberElement);
				Text numberText = document.createTextNode(player.getDeclaration().getNumber().toString());
				numberElement.appendChild(numberText);
				
				Element valueElement = document.createElement("value");
				declarationElement.appendChild(valueElement);
				Text valueText = document.createTextNode(player.getDeclaration().getFace().getValue().toString());
				valueElement.appendChild(valueText);
			}
			
			if(full || current.getPlayerId() == player.getPlayerId()){
				Element facesElement = document.createElement("faces");
				playerElement.appendChild(facesElement);
				
				for (Face face : player.getFaces()) {
					Element faceElement = document.createElement("face");
					facesElement.appendChild(faceElement);
					Text faceText = document.createTextNode(face.getValue().toString());
					faceElement.appendChild(faceText);
				}
			}
		}
		return document;
	}
	
	@Override
	public Document buildErrorDocument(Exception e) throws ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		
		Element errorElement = document.createElement("error");
		document.appendChild(errorElement);
		Text errorText = document.createTextNode(e.getMessage());
		errorElement.appendChild(errorText);
		
		return document;
	}
	
	protected boolean isValid(Document document) throws IOException, SAXException {

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		URL url = this.getClass().getClassLoader().getResource("xml/perudo.xsd");
		File file = new File(url.getFile());
		Schema schema = schemaFactory.newSchema(file);
		DOMSource source = new DOMSource(document);
		Validator validator = schema.newValidator();
		try{
			validator.validate(source);
		}
		catch(SAXException e){
			System.out.println(e.getLocalizedMessage());
			return false;
		}
		return true;
	}
}
