package de.skysoldier.agl2test.lightingtest;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGL;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniform;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniformType;

public class Light {
	
	private Vector3f color, ambient;
	private Vector4f position;
	private float attenuation;
	
	private AGLUniform positionUniform, colorUniform, ambientUniform, attenuationUniform;
	
	public Light(AGLShaderProgram program, int index){
		this(new Vector4f(0, 0, 0, 1), new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), 0, program, index);
	}
	
	public Light(Vector4f position, Vector3f color, Vector3f ambient, float attenuation, AGLShaderProgram program, int index){
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
		positionUniform = new AGLUniform(AGLUniformType.VEC4, program, "lights[" + index + "].position");
		colorUniform = new AGLUniform(AGLUniformType.VEC3, program, "lights[" + index + "].color");
		ambientUniform = new AGLUniform(AGLUniformType.VEC3, program, "lights[" + index + "].ambient");
		attenuationUniform = new AGLUniform(AGLUniformType.FLOAT, program, "lights[" + index + "].attenuation");
	}
	
	public void setPosition(Vector4f position){
		this.position = position;
	}
	
	public void setPosition(Vector3f position){
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
	}
	
	public void setColor(Vector3f color){
		this.color = color;
	}
	
	public void setAmbient(Vector3f ambient){
		this.ambient = ambient;
	}
	
	public void setAttenuation(float attenuation){
		this.attenuation = attenuation;
	}
	
	public void updatePosition(){
		positionUniform.putData(true, AGL.getVectorData(position));
	}
	
	public void updateColor(){
		colorUniform.putData(true, AGL.getVectorData(color));
	}
	
	public void updateAmbient(){
		ambientUniform.putData(true, AGL.getVectorData(ambient));
	}
	
	public void updateAttenuation(){
		attenuationUniform.putData(true, attenuation);
	}
	
	public void updateAll(){
		updatePosition();
		updateColor();
		updateAmbient();
		updateAttenuation();
	}
}
