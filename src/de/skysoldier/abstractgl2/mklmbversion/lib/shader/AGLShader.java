package de.skysoldier.abstractgl2.mklmbversion.lib.shader;

import org.lwjgl.opengl.GL20;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLShaderType;

public abstract class AGLShader {
	
	private int id;
	private String content;
	protected AGLShaderType type;
	
	public AGLShader(String content){
		this.content = content;
	}
	
	protected void create(){
		id = GL20.glCreateShader(getGLType());
		GL20.glShaderSource(id, content);
		GL20.glCompileShader(id);
	}
	
	protected void bind(int programId){
		GL20.glAttachShader(programId, id);
	}
	
	protected void unbind(int programId){
		GL20.glDetachShader(programId, id);
	}
	
	protected String getContent(){
		return content;
	}
	
	public int getId(){
		return id;
	}
	
	protected abstract int getGLType();
	protected abstract AGLShaderType getType();
}