package de.skysoldier.abstractgl2.mklmbversion.lib.render;

public class AGLAxisAlignedBoundingBox {
	
	private float width, height, depth;
	
	public AGLAxisAlignedBoundingBox(float width, float height, float depth){
		this.width = width;
		this.height = height;
		this.depth = depth;
	} 
	
	public AGLAxisAlignedBoundingBox(float width, float height){
		this.width = width;
		this.height = height;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public float getDepth(){
		return depth;
	}
	
	public String toString(){
		return "AGLAABBBoundingBox[width: " + width + ", height: " + height + ", depth:" + depth + "]";
	}
}
