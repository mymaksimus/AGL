package de.skysoldier.agl2test.sidescroller;

import org.lwjgl.opengl.GL11;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.texture.AGLTexture;

public enum Texture {
		
	NULL(null),
	GROUND("ground.png"),
	BACKGROUND("background.png"),
	PLAYER("player.png");
	
	private AGLTexture texture;
	
	Texture(String resourceName){
		if(resourceName != null){
			texture = new AGLTexture(new AGLResource("sidescroller/textures/" + resourceName));
			texture.setFilterMode(GL11.GL_LINEAR);
		}
	}
	
	public AGLTexture getTexture(){
		return texture;
	}
}
