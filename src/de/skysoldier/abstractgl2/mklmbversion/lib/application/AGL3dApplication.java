package de.skysoldier.abstractgl2.mklmbversion.lib.application;

import org.lwjgl.input.Mouse;

import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGL3dCamera;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLCamera;


public abstract class AGL3dApplication extends AGLApplication {
	
	private AGLCamera camera;
	
	public AGL3dApplication(){
		this.camera = buildCamera();
	}
	
	public void build(){
		Mouse.setGrabbed(true);
	}
	
	public void run(){
		camera.update();
	}
	
	public boolean isRunning(){
		return !getDisplay().isCloseRequested();
	}
	
	public AGL3dCamera getCamera(){
		return (AGL3dCamera) camera;
	}
	
	public abstract AGL3dCamera buildCamera();
}
