package fr.plaisance;

import java.util.ResourceBundle;


public abstract class PerudoUtil {

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
