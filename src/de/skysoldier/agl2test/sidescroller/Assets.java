package de.skysoldier.agl2test.sidescroller;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;
import de.skysoldier.abstractgl2.mklmbversion.lib.utils.AGLModelLoader;

public enum Assets {
	
	GROUND(new AGLMesh(new float[]{
			-20, 0, 0,	  0, 1, 0, 	 0, 0,
			-20, 0,-11,   0, 1, 0,	 0, 2,
			 20, 0, 0,	  0, 1, 0,	 2, 0,
			 20, 0,-11f,   0, 1, 0,   2, 2
	}, AGLDrawMode.TRIANGLE_STRIP)),
	BACKGROUND(new AGLMesh(new float[]{
			-20,  0, -10,	0, 0, 1,	0, 1,
			-20, 10, -10,	0, 0, 1,	0, 0,
			 20,  0, -10,	0, 0, 1,	2, 1,
			 20, 10, -10, 	0, 0, 1,	2, 0
	}, AGLDrawMode.TRIANGLE_STRIP)),
	PLAYER(new AGLMesh(
			AGLModelLoader.loadObj(new AGLResource("sidescroller/models/player.obj"), true),
			AGLDrawMode.TRIANGLES)),
	TEST_PLAYER(new AGLMesh(new float[]{
			-0.1f,-0.1f,-0.1f,	-0.1f,-0.1f, 0.1f,
			-0.1f,-0.1f, 0.1f, 	 0.1f,-0.1f, 0.1f,
			 0.1f,-0.1f, 0.1f, 	 0.1f,-0.1f,-0.1f,
			 0.1f,-0.1f,-0.1f, 	-0.1f,-0.1f,-0.1f,
			-0.1f, 0.1f,-0.1f, 	-0.1f, 0.1f, 0.1f,
			-0.1f, 0.1f, 0.1f, 	 0.1f, 0.1f, 0.1f,
			 0.1f, 0.1f, 0.1f, 	 0.1f, 0.1f,-0.1f,
			 0.1f, 0.1f,-0.1f, 	-0.1f, 0.1f,-0.1f,
			-0.1f,-0.1f,-0.1f, 	-0.1f, 0.1f,-0.1f,
			-0.1f,-0.1f, 0.1f, 	-0.1f, 0.1f, 0.1f,
			 0.1f,-0.1f, 0.1f, 	 0.1f, 0.1f, 0.1f,
			 0.1f,-0.1f,-0.1f, 	 0.1f, 0.1f,-0.1f,
	}, AGLDrawMode.LINES));
	
	private AGLMesh data;
	private AGLAsset asset;
	
	Assets(AGLMesh data){
		this.data = data;
	}
	
	public void create(AGLGlslAttribute attributes[]){
		create(attributes, Texture.NULL);
	}
	
	public void create(AGLGlslAttribute attributes[], Texture texture){
		asset = new AGLAsset(new AGLMesh(data, attributes), texture.getTexture());
	}
	
	public AGLMesh getData(){
		return data;
	}
	
	public AGLAsset getAsset(){
		return asset;
	}
}
