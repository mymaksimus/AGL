package de.skysoldier.abstractgl2.mklmbversion.lib.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static ArrayList<String> exract(String s, String regex, int offset){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		ArrayList<String> matches = new ArrayList<>();
		int counter = 0;
		while(matcher.find()){
			if(++counter > offset) matches.add(matcher.group());
		}
		return matches;
	}
}
