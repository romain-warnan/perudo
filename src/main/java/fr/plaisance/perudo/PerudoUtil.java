package fr.plaisance.perudo;

import java.util.ResourceBundle;


public abstract class PerudoUtil {

    public static final Integer MAX_PLAYER = Integer.valueOf(PerudoUtil.config("max.player"));
    public static final Integer MAX_DICE = Integer.valueOf(PerudoUtil.config("max.player"));

	private PerudoUtil(){
		// Static class
	}
	
	public static int random(int min, int max){
		return (int)(min + Math.random() * max);
	}
	
	public static String message(String key){
		ResourceBundle bundle = ResourceBundle.getBundle("param.message");
		return bundle.getString(key);
	}
	
	public static String config(String key){
		ResourceBundle bundle = ResourceBundle.getBundle("param.config");
		return bundle.getString(key);
	}
}
