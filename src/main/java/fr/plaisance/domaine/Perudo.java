package fr.plaisance.domaine;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class Perudo {

	private static Perudo instance = null;
	
	private List<Game> games;
	
	private Perudo() {
		super();
	}
	
	public static Perudo getInstance(){
		if(instance == null){
			instance = new Perudo();
		}
		return instance;
	}

	public List<Game> getGames() {
		return games;
	}
	
	public void addGame(Game game){
		if(CollectionUtils.isEmpty(games)){
			games = new ArrayList<Game>();
		}
		games.add(game);
	}
}
