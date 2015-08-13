package de.skysoldier.abstractgl2.mklmbversion.lib.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGL;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLGlslVariableType;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;
import de.skysoldier.abstractgl2.mklmbversion.lib.utils.StringUtils;

public class AGLShaderProgram {
	
	private int id;
	private static int active;
	private ArrayList<AGLGlslAttribute> attributes;
	
	public AGLShaderProgram(AGLShader... shader){
		id = GL20.glCreateProgram();
		attributes = new ArrayList<>();
		for(AGLShader s : shader){
			s.create();
			s.bind(id);
			if(s instanceof AGLVertexShader) parseGlslAttributes(s);
		}
		for(AGLGlslAttribute a : attributes){
			GL20.glBindAttribLocation(id, a.getId(), a.getName());
			System.out.println(a);
		}
		GL20.glLinkProgram(id);
        System.out.println("[AGLShaderProgram : Constructor Info] Link Errors: " + GL20.glGetProgramInfoLog(id, GL20.glGetProgrami(id, GL20.GL_INFO_LOG_LENGTH)));
	}
	
	private void parseGlslAttributes(AGLShader shader){
		BufferedReader reader = new BufferedReader(new StringReader(shader.getContent()));
		String line;
		try{
			while((line = reader.readLine()) != null){
				if(line.startsWith("in")){
					ArrayList<String> parts = StringUtils.exract(line, "\\w+", 1);
					attributes.add(new AGLGlslAttribute(parts.get(1), AGLGlslVariableType.valueOf(parts.get(0).toUpperCase()).getSize(), false, AGL.genId()));
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<AGLGlslAttribute> getAttributes(){
		return attributes;
	}
	
	public int getId(){
		return id;
	}
	
	public void bind(){
		if(active != id){
			AGLShaderProgram.active = id;
			GL20.glUseProgram(active);
		}
	}

	protected static int getActive(){
		return active;
	}
	
	protected static void unbind(){
		active = 0;
		GL20.glUseProgram(0);
	}
}