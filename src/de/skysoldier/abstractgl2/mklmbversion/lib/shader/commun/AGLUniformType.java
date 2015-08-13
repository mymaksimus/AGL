package de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL20;


public enum AGLUniformType {
	
	MATRIX4(new AGLUniformImplementation(){
		public int getSize(){
			return 16;
		}

		public void updateData(AGLUniform uniform){
			GL20.glUniformMatrix4(uniform.getId(), false, uniform.getDataBuffer());
		}
	}),
	VEC4(new AGLUniformImplementation(){
		public int getSize(){
			return 4;
		}

		public void updateData(AGLUniform uniform){
			FloatBuffer dataBuffer = uniform.getDataBuffer();
			GL20.glUniform4f(uniform.getId(), dataBuffer.get(0), dataBuffer.get(1), dataBuffer.get(2), dataBuffer.get(3));
		}
	}),
	VEC3(new AGLUniformImplementation(){
		public int getSize(){
			return 3;
		}

		public void updateData(AGLUniform uniform){
			FloatBuffer dataBuffer = uniform.getDataBuffer();
			GL20.glUniform3f(uniform.getId(), dataBuffer.get(0), dataBuffer.get(1), dataBuffer.get(2));
		}
	}),
	VEC2(new AGLUniformImplementation(){
		public int getSize(){
			return 2;
		}

		public void updateData(AGLUniform uniform){
			FloatBuffer dataBuffer = uniform.getDataBuffer();
			GL20.glUniform2f(uniform.getId(), dataBuffer.get(0), dataBuffer.get(1));
		}
	}),
	FLOAT(new AGLUniformImplementation(){
		public int getSize(){
			return 1;
		}

		public void updateData(AGLUniform uniform){
			FloatBuffer dataBuffer = uniform.getDataBuffer();
			GL20.glUniform1f(uniform.getId(), dataBuffer.get(0));
		}
	}),
	INTEGER(new AGLUniformImplementation(){
		public int getSize(){
			return 1;
		}

		public void updateData(AGLUniform uniform){
			FloatBuffer dataBuffer = uniform.getDataBuffer();
			GL20.glUniform1i(uniform.getId(), (int) dataBuffer.get(0));
		}
	});
	
	private AGLUniformImplementation implementation;
	
	AGLUniformType(AGLUniformImplementation implementation){
		this.implementation = implementation;
	}
	
	public static AGLUniformType getType(String s){
		return s.equals("sampler2D") ? INTEGER : valueOf(s);
	}
	
	public AGLUniformImplementation getImplementation(){
		return implementation;
	}
}
