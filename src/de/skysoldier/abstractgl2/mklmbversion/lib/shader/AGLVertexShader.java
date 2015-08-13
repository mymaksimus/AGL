package de.skysoldier.abstractgl2.mklmbversion.lib.shader;

import org.lwjgl.opengl.GL20;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLShaderType;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;

public class AGLVertexShader extends AGLShader {

	public AGLVertexShader(AGLResource resource){
		this(resource.toStringResource());
	}
	
	public AGLVertexShader(String content){
		super(content);
	}
	
	protected int getGLType(){
		return GL20.GL_VERTEX_SHADER;
	}
	
	protected AGLShaderType getType(){
		return AGLShaderType.VERTEX;
	}
}