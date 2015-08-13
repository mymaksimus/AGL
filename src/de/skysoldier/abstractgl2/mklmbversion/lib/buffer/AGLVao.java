package de.skysoldier.abstractgl2.mklmbversion.lib.buffer;

import org.lwjgl.opengl.GL30;

public class AGLVao {
	
	private int id;
	private static int current;
	
	public AGLVao(){
		id = GL30.glGenVertexArrays();
	}
	
	public void bind(){
		if(AGLVao.current != id){
			GL30.glBindVertexArray(id);
			AGLVao.current = id;
		}
	}
	
	public static void unbind(){
		AGLVao.current = 0;
		GL30.glBindVertexArray(0);
	}
}