package fr.plaisance;

import fr.plaisance.service.DeclarationService;
import fr.plaisance.service.DeclarationServiceSimple;
import fr.plaisance.service.GameService;
import fr.plaisance.service.GameServiceSimple;
import fr.plaisance.service.PlayerService;
import fr.plaisance.service.PlayerServiceSimple;
import fr.plaisance.xml.PerudoXml;
import fr.plaisance.xml.PerudoXmlSimple;

public class PerudoService {

	public static final Integer MAX_PLAYER = Integer.valueOf(PerudoUtil.config("max.player"));
	public static final Integer MAX_DICE = Integer.valueOf(PerudoUtil.config("max.player"));
	//public static final Random ID_GENERATOR = new Random(System.currentTimeMillis());
	
	public static GameService gameService(){
		return GameServiceSimple.getInstance();
	}
	
	public static PlayerService playerService(){
		return PlayerServiceSimple.getInstance();
	}
	
	public static DeclarationService declarationService(){
		return DeclarationServiceSimple.getInstance();
	}
	
	public static PerudoXml xmlService(){
		return PerudoXmlSimple.getInstance();
	}
}
