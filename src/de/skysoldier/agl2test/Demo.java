package de.skysoldier.agl2test;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLApplication;
import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLComplexRenderObject;
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

public class Demo extends AGLApplication {
	
	private AGLDisplay display;
	private ArrayList<Barrel> barrels;
	private AGLAsset barrelCapAsset, barrelWallAsset;
 	private AGLView demoView;
	
	public Demo(){
		barrels = new ArrayList<>();
		build();
	}
	
	private void build(){
		this.display = new AGLDisplay(AGLDisplayCap.NONE);
		AGLResource shaderResource = new AGLResource("demo.shader");
		AGLResource stripMeshDataResource = new AGLResource("demo_strip.vbo");
		AGLResource textureResource1 = new AGLResource("box.png");
		AGLResource textureResource2 = new AGLResource("box2.png");
		AGLResource fanMeshDataResource = new AGLResource("demo_fan.vbo");
		AGLTexture demoTexture1 = new AGLTexture(textureResource1);
		AGLTexture demoTexture2 = new AGLTexture(textureResource2);
		AGLGlslAttribute attributes[] = new AGLGlslAttribute[]{
			AGLGlslAttribute.createAttributeVec3("vertexIn"),
			AGLGlslAttribute.createAttributeVec2("texCoords")
		};
		AGLShaderProgram demoShaderProgram = new AGLShaderProgram(shaderResource, "###", attributes);
		AGLCamera camera = new AGLCamera(new AGLProjection.PerspectiveProjection(60f, 0.1f, 100f));
		this.demoView = new AGLView(camera);
		AGLViewPart demoViewPart = new AGLViewPart(demoShaderProgram, 0);
		demoView.addViewPart(demoViewPart);
		
		AGLMesh stripMeshData = new AGLMesh(stripMeshDataResource, AGLDrawMode.TRIANGLE_STRIP);
		AGLMesh fanMeshData = new AGLMesh(fanMeshDataResource, AGLDrawMode.TRIANGLE_FAN);
		AGLMesh capMesh = new AGLMesh(fanMeshData, attributes);
		AGLMesh wallMesh = new AGLMesh(stripMeshData, attributes);
		this.barrelWallAsset = new AGLAsset(wallMesh, demoTexture2);
		this.barrelCapAsset = new AGLAsset(capMesh, demoTexture1);
		
		barrels.add(new Barrel(0, 0, 0, demoViewPart));

		Mouse.setGrabbed(true);
		AGLRenderController.bindViews(demoView);
		AGLRenderController.init(false, true);
		runGameLoop(10);
	}
	
	public void run(){
		demoView.getCamera().update();
		for(Barrel b : barrels){
			b.update();
		}
	}
	
	public boolean isRunning(){
		return !display.isCloseRequested();
	}
	
	class BarrelCap extends AGLRenderObject {
	
		public BarrelCap(){
			super(barrelCapAsset);
		}
	}
	
	class BarrelWall extends AGLRenderObject {
		
		public BarrelWall(){
			super(barrelWallAsset);
		}
	}
	
	class Barrel extends AGLComplexRenderObject {
		
		private BarrelCap cap, cap2;
		
		public Barrel(float x, float y, float z, AGLViewPart part){
			this.cap = new BarrelCap();
			cap.moveY(2f, 1f);
			this.cap2 = new BarrelCap();
			cap2.moveY(-2f, 1f);
			BarrelWall wall = new BarrelWall();
			addParts(cap, cap2, wall);
			part.addRenderObjects(cap, cap2, wall);
		}
		
		public void update(){
			cap.moveY(-2f, 1f);
			cap2.moveY(2f, 1f);
			rotateX((float) Math.sin(AGLRenderController.getTicksInSeconds()) * 2, AGLRenderController.getDeltaS());
			rotateZ((float) -Math.cos(AGLRenderController.getTicksInSeconds()), AGLRenderController.getDeltaS());
			cap.moveY(2f, 1f);
			cap2.moveY(-2f, 1f);
		}
	}
	
	public static void main(String[] args) {
		new Demo();
	}
}
