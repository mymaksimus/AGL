package de.skysoldier.abstractgl2.mklmbversion.lib.render.geom;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLAxis;

public class AGLBasicMovableMat4Object implements AGLMovable, AGLScaleable, AGLRotateable {

	private Matrix4f model;
	private Vector3f scale;
	private Vector3f translation;
	private Vector3f rotation;
	
	public AGLBasicMovableMat4Object(){
		this.model = new Matrix4f();
		scale = new Vector3f();
		translation = new Vector3f();
		rotation = new Vector3f();
	}
	
	public void scale(Vector3f scale){
		this.scale = scale;
		model.scale(scale);
	}
	
	public void scale(float x, float y, float z){
		scale(new Vector3f(x, y, z));
	}
	
	public void scaleX(float x){
		scale(x, scale.y, scale.z);
	}
	
	public void scaleY(float y){
		scale(scale.x, y, scale.z);
	}
	
	public void scaleZ(float z){
		scale(scale.x, scale.y, z);
	}
	
	public void translateTo(Vector3f position){
		translate(Vector3f.sub(position, translation, new Vector3f()));
	}

	public void translateTo(float x, float y, float z){
		translateTo(new Vector3f(x, y, z));
	}
	
	public void translateToX(float x){
		translateX(x - translation.x);
	}

	public void translateToY(float y){
		translateY(y - translation.y);
	}

	public void translateToZ(float z){
		translateZ(z - translation.z);
	}
	
	public void translate(Vector3f step){
		model.translate(step);
		translation.x = model.m30;
		translation.y = model.m31;
		translation.z = model.m32;
	}
	
	public void translate(float translationX, float translationY, float translationZ){
		translate(new Vector3f(translationX, translationY, translationZ));
	}

	public void translateX(float translationX){
		translate(new Vector3f(translationX, 0, 0));
	}

	public void translateY(float translationY){
		translate(new Vector3f(0, translationY, 0));
	}

	public void translateZ(float translationZ){
		translate(new Vector3f(0, 0, translationZ));
	}
	
	public void rotateToX(float radians){
		rotateX(radians - rotation.getX());
	}
	
	public void rotateToY(float radians){
		rotateY(radians - rotation.getY());
	}
	
	public void rotateToZ(float radians){
		rotateZ(radians - rotation.getZ());
	}
	
	public void rotateX(float radians){
		model.rotate(radians, AGLAxis.X.getVector());
		rotation.setX(rotation.getX() + radians);
	}
	
	public void rotateY(float radians){
		model.rotate(radians, AGLAxis.Y.getVector());
		rotation.setY(rotation.getY() + radians);
	}

	public void rotateZ(float radians){
		model.rotate(radians, AGLAxis.Z.getVector());
		rotation.setZ(rotation.getZ() + radians);
	}
	
	public Vector3f getScale(){
		return scale;
	}
	
	public Vector3f getPosition(){
		return translation;
	}
	
	public Vector3f getRotation(){
		return rotation;
	}
	
	public Matrix4f getModel(){
		return model;
	}
}