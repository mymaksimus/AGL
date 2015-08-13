package de.skysoldier.abstractgl2.mklmbversion.lib.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.skysoldier.abstractgl2.mklmbversion.lib.buffer.AGLVao;
import de.skysoldier.abstractgl2.mklmbversion.lib.buffer.AGLVbo;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;

public class AGLAsset {

	private AGLMesh mesh;
	private AGLVao vao;
	private AGLVbo vbo;
	
	public AGLAsset(AGLMesh mesh, ArrayList<AGLGlslAttribute> attributes){
		this.mesh = mesh;
		vao = new AGLVao();
		vao.bind();
		this.vbo = new AGLVbo(mesh, attributes);
		AGLVao.unbind();
	}
	
	protected void render(){
		vao.bind();
		GL11.glDrawArrays(mesh.getDrawMode().getRawMode(), 0, vbo.getRowCount());
	}
}
