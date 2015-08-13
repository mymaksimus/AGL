package de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;

public class AGLUniform {
	
	private int id, size;
	private FloatBuffer dataBuffer;
	private AGLShaderProgram program;
	private AGLUniformImplementation implementation;
	
	public AGLUniform(AGLUniformType type, AGLShaderProgram program, String name){
		this.implementation = type.getImplementation(); 
		size = implementation.getSize();
		dataBuffer = BufferUtils.createFloatBuffer(size);
		this.program = program;
		program.bind();
		id = GL20.glGetUniformLocation(program.getId(), name);
	}
	
	protected void putData(float data[]){
		dataBuffer.put(data);
		dataBuffer.flip();
	}
	
	public void putData(boolean update, float... data){
		putData(data);
		if(update){
			requestUpdate();
		}
	}
	
	public AGLUniformBinding createBinding(){
		return new AGLUniformBinding(this);
	}
	
	protected void requestUpdate(){
		program.bind();
		implementation.updateData(this);
	}
	
	protected FloatBuffer getDataBuffer(){
		return dataBuffer;
	}
	
	protected int getSize(){
		return implementation.getSize();
	}
	
	protected int getId(){
		return id;
	}
}