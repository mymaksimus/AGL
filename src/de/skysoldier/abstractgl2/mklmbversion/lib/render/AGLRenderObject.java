package de.skysoldier.abstractgl2.mklmbversion.lib.render;

import java.util.ArrayDeque;
import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGL;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.geom.AGLBasicMovableMat4Object;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.texture.AGLTexture;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniformBinding;

public class AGLRenderObject extends AGLBasicMovableMat4Object {
	
	private AGLAsset asset;
	private ArrayList<AGLUniformBinding> bindings;
	private AGLUniformBinding modelUniformBinding;
	private float creationTime;
	private boolean isRemoveRequested;
	private AGLRenderObject parent;
	private ArrayList<AGLRenderObject> subRenderObjects;
	private Matrix4f uploadModel;
	private AGLTexture texture;
	
	public AGLRenderObject(AGLAsset asset){
		this.asset = asset;
		this.bindings = new ArrayList<>();
		creationTime = AGL.getTime();
		subRenderObjects = new ArrayList<>();
		uploadModel = new Matrix4f();
	}
	
	public void addUniformBinding(AGLUniformBinding uniform){
		bindings.add(uniform);
	}
	
	protected void render(){
		update();
		updateUniformBindings();
		if(texture != null) texture.bind();
		asset.render();
	}
	
	public void setTexture(AGLTexture texture){
		this.texture = texture;
	}
	
	public void update(){
	
	}
	
	public void addSubRenderObject(AGLRenderObject object){
		subRenderObjects.add(object);
		object.setParent(this);
	}
	
	public void setParent(AGLRenderObject parent){
		this.parent = parent;
	}
	
	protected void updateModel(ArrayDeque<Matrix4f> matrixStack){
		Matrix4f preModel = matrixStack.peek();
		Matrix4f.mul(preModel, getModel(), uploadModel);
		matrixStack.push(uploadModel);
		for(AGLRenderObject object : subRenderObjects){
			object.updateModel(matrixStack);
		}
		matrixStack.pop();
	}
	
	protected void updateUniformBindings(){
		modelUniformBinding.putData(AGL.getMatrixData(uploadModel));
		for(AGLUniformBinding binding : bindings){
 			binding.requestUpdate();
		}
	}
	
	public AGLAsset getAsset(){
		return asset;
	}
	
	public void setRemoveRequested(boolean isRemoveRequested){
		this.isRemoveRequested = isRemoveRequested;
	}
	
	protected void setModelUniformBinding(AGLUniformBinding binding){
		this.modelUniformBinding = binding;
		addUniformBinding(modelUniformBinding);
	}
	
	public Matrix4f getUploadModel(){
		return uploadModel;
	}
	
	public boolean isRemoveRequested(){
		return isRemoveRequested;
	}
	
	public boolean isRootObject(){
		return parent == null;
	}
	
	public float getCreationTime(){
		return creationTime;
	}
}