package fr.plaisance.perudo.domaine;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Perudo {

	private List<Game> games = new ArrayList<>();

	public List<Game> getGames() {
		return games;
	}
	
	public void addGame(Game game){
		games.add(game);
	}
}
