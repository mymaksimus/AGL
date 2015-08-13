package de.skysoldier.abstractgl2.mklmbversion.lib.buffer;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLBufferMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;

public class AGLVbo extends AGLArrayBuffer {
	
	private AGLMesh meshData;
	private int rowSize;
	private int rowCount;
	private int totalSize;
	
	public AGLVbo(AGLMesh data, ArrayList<AGLGlslAttribute> attributes){
		super.bind();
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.getData().length);
		this.meshData = data;
		buffer.put(data.getData());
		buffer.flip();
		totalSize = buffer.capacity();
		loadAttributes(attributes);
		super.data(buffer, AGLBufferMode.DRAW);
		unbind();
	}
	
	public static void unbind(){
		AGLArrayBuffer.unbind();
	}
	
	protected void loadAttributes(ArrayList<AGLGlslAttribute> attributes){
		int bufferSize = 0;
		int rowSize = 0;
		for(AGLGlslAttribute attribute : attributes){
			rowSize += attribute.getSize();
		}
		this.rowSize = rowSize;
		rowCount = totalSize / rowSize;
		rowSize = rowSize * Float.SIZE / 8;
		for(AGLGlslAttribute attribute : attributes){
			GL20.glEnableVertexAttribArray(attribute.getId());
			GL20.glVertexAttribPointer(attribute.getId(), attribute.getSize(), GL11.GL_FLOAT, attribute.isNormalized(), rowSize, bufferSize * Float.SIZE / 8);
			bufferSize += attribute.getSize();
		}
	}
	
	public int getRowSize(){
		return rowSize;
	}
	
	public int getRowCount(){
		return rowCount;
	}
	
	public int getTotalSize(){
		return totalSize;
	}
	
	public AGLMesh getMeshData(){
		return meshData;
	}
}