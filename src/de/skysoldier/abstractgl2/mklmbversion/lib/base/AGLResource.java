package de.skysoldier.abstractgl2.mklmbversion.lib.base;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AGLResource {
	
	private InputStream inputStream;
	
	public AGLResource(String name){
		inputStream = getClass().getClassLoader().getResourceAsStream(name);
	}
	
	public AGLResource(File file){
		try{
			inputStream = new FileInputStream(file);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public InputStream getInputStream(){
		return inputStream;
	}
	
	public BufferedImage toImageResource(){
		try{
			return ImageIO.read(inputStream);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	static interface LineFeedback {
		void onLineRead(String line);
	}
	
	public String toTextResource(LineFeedback feedback){
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String content = "";
		String line = "";
		try{
			while ((line = reader.readLine()) != null){
				if(feedback != null) feedback.onLineRead(line);
				content += line + "\n";
			}
			return content;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<String> toLineListResource(){
		final ArrayList<String> lines = new ArrayList<>();
		toTextResource(new LineFeedback(){
			public void onLineRead(String line){
				lines.add(line);
			}
		});
		return lines;
	}
	
	public String toStringResource(){
		return toTextResource(null);
	}
}