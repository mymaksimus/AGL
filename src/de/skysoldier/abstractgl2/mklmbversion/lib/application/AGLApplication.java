package de.skysoldier.abstractgl2.mklmbversion.lib.application;

import org.lwjgl.opengl.Display;

import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderController;

public abstract class AGLApplication implements AGLGameLoop {
	
	private AGLDisplay display;
	
	public AGLApplication(){
		display = buildDisplay();
	}
	
	public void runGameLoop(int fpsDispPerSecond){
		float counter = 0;
		while (isRunning()){
			AGLRenderController.updateTime();
			run();
			counter += AGLRenderController.getDeltaS();
			if(fpsDispPerSecond > 0 && counter >= 1f / (float) fpsDispPerSecond){
				Display.setTitle("fps: " + AGLRenderController.getFps());
				counter = 0;
			}
			AGLRenderController.render();
		}
	}
	
	public AGLDisplay getDisplay(){
		return display;
	}
	
	public abstract void build();
	public abstract void run();	
	public abstract boolean isRunning();
	public abstract AGLDisplay buildDisplay();
}