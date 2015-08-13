package de.skysoldier.abstractgl2.mklmbversion.lib.render.geom;

import org.lwjgl.util.vector.Vector3f;

public interface AGLMovable {

	public void translateTo(Vector3f position);
	public void translateTo(float x, float y, float z);
	public void translateToX(float x);
	public void translateToY(float y);
	public void translateToZ(float z);
	public void translate(Vector3f translation);
	public void translate(float translationX, float translationY, float translationZ);
	public void translateX(float translationX);
	public void translateY(float translationY);
	public void translateZ(float translationZ);
	public Vector3f getPosition();
}