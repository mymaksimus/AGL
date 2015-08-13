package de.skysoldier.abstractgl2.mklmbversion.lib.buffer;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL15;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLBufferMode;

public abstract class AGLArrayBuffer {
	
	private int id;
	private static int current;

	public AGLArrayBuffer(){
		id = GL15.glGenBuffers();
	}
	
	protected void data(FloatBuffer buffer, AGLBufferMode bufferMode){
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, bufferMode.getRawMode());
	}
	
	public void bind(){
		if(AGLArrayBuffer.current != id){
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
			AGLArrayBuffer.current = id;
		}
	}
	
	public static void unbind(){
		AGLArrayBuffer.current = 0;
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
}