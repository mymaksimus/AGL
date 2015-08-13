package de.skysoldier.abstractgl2.mklmbversion.lib.render.geom.collision;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class AGLAabb {

	private Vector3f minCorner;
	private Vector3f maxCorner;
	private Vector3f center;
	
	public AGLAabb(){
		this(new Vector3f(), new Vector3f());
	}
	
	public AGLAabb(Vector2f minCorner, Vector2f maxCorner){
		this(new Vector3f(minCorner.x, minCorner.y, 0), new Vector3f(maxCorner.x, maxCorner.y, 0));
	}
	
	public AGLAabb(Vector3f minCorner, Vector3f maxCorner){
		this.minCorner = minCorner;
		this.maxCorner = maxCorner;
		calcCenter();
	}
	
	public void calcCenter(){
		center = new Vector3f(
			minCorner.x + 0.5f * (maxCorner.x - minCorner.x), 
			minCorner.y + 0.5f * (maxCorner.y - minCorner.y), 
			minCorner.z + 0.5f * (maxCorner.z - minCorner.z));
	}
	
	public Vector3f getMinCorner(){
		return minCorner;
	}
	
	public Vector3f getMaxCorner(){
		return maxCorner;
	}
	
	public Vector3f getCenter(){
		return center;
	}
	
	public String toString(){
		return "[AABB: min(" + minCorner.x + "|" + minCorner.y + "), ctr(" + center.x + "|" + center.y + "), max(" + maxCorner.x + "|" + maxCorner.y + ")]";
	}
}