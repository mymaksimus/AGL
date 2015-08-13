package de.skysoldier.abstractgl2.mklmbversion.lib.shader;

import org.lwjgl.opengl.GL20;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLShaderType;

public class AGLFragmentShader extends AGLShader {

	public AGLFragmentShader(AGLResource resource){
		this(resource.toStringResource());
	}
	
	public AGLFragmentShader(String content){
		super(content);
	}

	protected int getGLType() {
		return GL20.GL_FRAGMENT_SHADER;
	}
	
	protected AGLShaderType getType(){
		return AGLShaderType.FRAGMENT;
	}
}
