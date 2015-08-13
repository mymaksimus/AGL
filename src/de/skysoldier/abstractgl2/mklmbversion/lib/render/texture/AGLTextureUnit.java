package de.skysoldier.abstractgl2.mklmbversion.lib.render.texture;

import org.lwjgl.opengl.GL13;

public class AGLTextureUnit {
	
	private static int units[] = new int[]{
		GL13.GL_TEXTURE0,
		GL13.GL_TEXTURE1,
		GL13.GL_TEXTURE2,
		GL13.GL_TEXTURE3,
		GL13.GL_TEXTURE4,
		GL13.GL_TEXTURE5,
		GL13.GL_TEXTURE6,
		GL13.GL_TEXTURE7,
		GL13.GL_TEXTURE8,
		GL13.GL_TEXTURE9,
		GL13.GL_TEXTURE10,
		GL13.GL_TEXTURE11,
		GL13.GL_TEXTURE12,
		GL13.GL_TEXTURE13,
		GL13.GL_TEXTURE14,
		GL13.GL_TEXTURE15,
		GL13.GL_TEXTURE16,
		GL13.GL_TEXTURE17,
		GL13.GL_TEXTURE18,
		GL13.GL_TEXTURE19,
		GL13.GL_TEXTURE20,
		GL13.GL_TEXTURE21,
		GL13.GL_TEXTURE22,
		GL13.GL_TEXTURE23,
		GL13.GL_TEXTURE24,
		GL13.GL_TEXTURE25,
		GL13.GL_TEXTURE26,
		GL13.GL_TEXTURE27,
		GL13.GL_TEXTURE28,
		GL13.GL_TEXTURE29,
		GL13.GL_TEXTURE30,
		GL13.GL_TEXTURE31
	};
	
	private int unit;
	
	protected AGLTextureUnit(int unit){
		this.unit = units[unit];
	}
	
	protected void bind(){
		GL13.glActiveTexture(unit);
	}
}
