package de.skysoldier.agl2test;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLApplication;
import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderController;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderObject;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLView;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLViewPart;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLCamera;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLProjection;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.texture.AGLTexture;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;

public class Demo2D extends AGLApplication {
	
	private AGLDisplay display;
	
	public Demo2D(){
		build();
	}
	
	private void build(){
		this.display = new AGLDisplay(AGLDisplayCap.NONE);
		AGLResource shaderResource = new AGLResource("demo2D.shader");
		AGLResource textureResource = new AGLResource("box2.png");
		AGLTexture demoTexture = new AGLTexture(textureResource);
		AGLGlslAttribute attributes[] = new AGLGlslAttribute[]{
			AGLGlslAttribute.createAttributeVec2("vertexIn"),
			AGLGlslAttribute.createAttributeVec2("texCoords")
		};
		AGLShaderProgram demoShaderProgram = new AGLShaderProgram(shaderResource, "###", attributes);
		AGLCamera camera = new AGLCamera(new AGLProjection.OrthogonalProjection(1.0f));
		AGLView demoView = new AGLView(camera);
		AGLViewPart demoViewPart = new AGLViewPart(demoShaderProgram, 0);
		demoView.addViewPart(demoViewPart);
		
		AGLAsset demoAsset = new AGLAsset(new AGLMesh(new AGLMesh(new float[]{
				0, 0,  0, 1, 
				1, 0,  1, 1,
				0, 1,  0, 0,
		}, AGLDrawMode.TRIANGLES), attributes), demoTexture);
		AGLRenderObject renderObject = new AGLRenderObject(demoAsset);
		demoViewPart.addRenderObjects(renderObject);
		
		AGLRenderController.bindViews(demoView);
		AGLRenderController.init(false, false);
		runGameLoop(10);
	}
	
	public void run(){
	
	}
	
	public boolean isRunning(){
		return !display.isCloseRequested();
	}
	
	public static void main(String[] args) {
		new Demo2D();
	}
}
