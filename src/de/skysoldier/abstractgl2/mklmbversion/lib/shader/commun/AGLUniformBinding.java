package de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLException;

public class AGLUniformBinding {
	
	private AGLUniform uniform;
	private float[] data;
	
	protected AGLUniformBinding(AGLUniform uniform){
		this.uniform = uniform;
	}
	
	public void requestUpdate(){
		uniform.putData(data);
		uniform.requestUpdate();
	}
	
	public AGLUniform getUniform(){
		return uniform;
	}
	
	public void putData(float... data){
		if(data.length > uniform.getSize()) throw new AGLException("data length > uniform size"); 
		else this.data = data;
	}
}