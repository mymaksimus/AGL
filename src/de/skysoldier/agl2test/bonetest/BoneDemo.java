package de.skysoldier.agl2test.bonetest;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLApplication;
import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderController;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLView;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLCamera;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLProjection;

public class BoneDemo extends AGLApplication {
	
	private AGLDisplay display;
	
	private BoneTest test;
	
	public BoneDemo(){
		build();
	}
	
	private void build(){
		this.display = new AGLDisplay(AGLDisplayCap.NONE);
		AGLCamera camera = new AGLCamera(new AGLProjection.OrthogonalProjection(1.0f));
		final AGLView boneTestView = new AGLView(camera);
		this.test = new BoneTest(boneTestView, display);
		
		AGLRenderController.bindViews(boneTestView);
		AGLRenderController.init(false, false);
		
		runGameLoop(10);
	}
	
	float pos;
	float rot;
	public void run(){
		test.update();
	}
	
	public boolean isRunning(){
		return !display.isCloseRequested();
	}
	
	public static void main(String[] args) {
		new BoneDemo();
	}
}
