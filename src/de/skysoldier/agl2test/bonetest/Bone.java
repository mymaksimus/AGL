package de.skysoldier.agl2test.bonetest;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderObject;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;

public class Bone extends AGLRenderObject {

	private float length;
	private Vector3f jointShift;
	private ArrayList<Bone> children;
	private BoneAngleAnimation animation;
	private boolean debug;
	
	public Bone(float x, float y, float length, AGLGlslAttribute attributes[]){
		super(new AGLAsset(new AGLMesh(new AGLMesh(new float[]{
				0, 0, 0, 1, 0,
				0, -length, 1, 0, 0
		}, AGLDrawMode.LINES), attributes), null));
//		scaleTo(1, length, 1);
		translateToGlobal(x, y, 0);
		this.length = length;
		this.jointShift = new Vector3f(0, 0, 0);
		children = new ArrayList<>();
	}
	
	public void setDebug(boolean debug){
		this.debug = debug;
	}
	
	public boolean debug(){
		return debug;
	}
	
	public void setJointShift(Vector3f joint){
		this.jointShift = joint;
	}
	
	public Vector3f getJointShift(){
		return jointShift;
	}
	
	public void setAnimation(BoneAngleAnimation animation, float startTime){
		this.animation = animation;
		animation.setup(this, startTime);
	}
	
	public BoneAngleAnimation getAnimation(){
		return animation;
	}
	
	public void addChildren(Bone b){
		children.add(b);
	}
	
	public ArrayList<Bone> getChildren(){
		return children;
	}
	
	public float getLength(){
		return length;
	}
}
