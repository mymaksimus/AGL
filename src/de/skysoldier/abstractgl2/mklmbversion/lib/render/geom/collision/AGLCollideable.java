package de.skysoldier.abstractgl2.mklmbversion.lib.render.geom.collision;

import org.lwjgl.util.vector.Vector3f;

public interface AGLCollideable {
	AGLAabb getAabb();
	boolean basicCollision(AGLCollideable collideable);
	boolean collision(AGLCollideable collideable);
	boolean contains(Vector3f point);
}