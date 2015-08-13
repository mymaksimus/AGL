package de.skysoldier.abstractgl2.mklmbversion.lib.render.geom;

import org.lwjgl.util.vector.Vector3f;

public interface AGLRotateable {
	
	public void rotateToX(float radians);
	public void rotateToY(float radians);
	public void rotateToZ(float radians);
	public void rotateX(float radians);
	public void rotateY(float radians);
	public void rotateZ(float radians);
	public Vector3f getRotation();
}
