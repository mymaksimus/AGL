package de.skysoldier.abstractgl2.mklmbversion.lib.render;

import java.util.ArrayDeque;
import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLBaseUniforms;

public class AGLViewPart implements Comparable<AGLViewPart> {
	
	private AGLView parentView;
	private ArrayList<AGLRenderObject> renderObjects;
	private AGLShaderProgram program;
	private int order;
	private ArrayDeque<Matrix4f> matrixStack;
	
	public AGLViewPart(AGLShaderProgram program, int order){
		this.renderObjects = new ArrayList<>();
		this.program = program;
		this.order = order;
		matrixStack = new ArrayDeque<>();
		matrixStack.push(new Matrix4f());
	}
	
	public AGLViewPart(AGLShaderProgram shaderProgram){
		this(shaderProgram, 0);
	}
	
	protected void setParentView(AGLView parentView){
		this.parentView = parentView;
	}
	
	protected AGLView getParentView(){
		return parentView;
	}
	
	protected void render(){
		program.bind();
		ArrayList<AGLRenderObject> toRemove = new ArrayList<>();
		for(AGLRenderObject r : renderObjects){
			if(r.isRootObject()){
				r.updateModel(matrixStack);
			}
			r.render();
			if(r.isRemoveRequested()){
				toRemove.add(r);
			}
		}
		renderObjects.removeAll(toRemove);
	}
	
	public AGLShaderProgram getShaderProgram(){
		return program;
	}
	
	public void addRenderObject(AGLRenderObject renderObject){
		renderObject.setModelUniformBinding(AGLBaseUniforms.createBaseModelUniform(program).createBinding());
		renderObjects.add(renderObject);
	}
	
	public void addRenderObjects(AGLRenderObject... renderObjects){
		for(AGLRenderObject object : renderObjects){
			addRenderObject(object);
		}
	}
	
	public AGLAsset createAsset(AGLMesh data){
		return new AGLAsset(data, program.getAttributes());
	}
	
	public int getOrder(){
		return order;
	}
	
	public void removeAllRenderObjects(){
		renderObjects.clear();
	}

	public int compareTo(AGLViewPart part){
		if(order > part.getOrder()) return 1;
		return -1;
	}
}