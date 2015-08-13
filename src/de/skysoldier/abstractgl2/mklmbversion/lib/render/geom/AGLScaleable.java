package de.skysoldier.abstractgl2.mklmbversion.lib.render.geom;

import org.lwjgl.util.vector.Vector3f;

public interface AGLScaleable {
	
	public void scale(Vector3f scale);
	public void scale(float x, float y, float z); 
	public void scaleX(float x);
	public void scaleY(float y);
	public void scaleZ(float z);
	public Vector3f getScale();
}
