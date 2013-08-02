package fr.plaisance.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import fr.plaisance.domaine.Game;
import fr.plaisance.domaine.Player;

public interface PerudoXml {

	Document buildFullDocument(Game game, Player player) throws SAXException, ParserConfigurationException, IOException;
	Document buildPlayerDocument(Game game, Player player) throws SAXException, ParserConfigurationException, IOException;
	Document buildErrorDocument(Exception e) throws ParserConfigurationException;
}
