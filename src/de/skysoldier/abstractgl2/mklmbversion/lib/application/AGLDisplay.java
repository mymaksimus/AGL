package de.skysoldier.abstractgl2.mklmbversion.lib.application;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLException;

public class AGLDisplay {
	
	private static AGLDisplay currentDisplay;
	private DisplayMode mode;
	private IntBuffer viewport;
	
	public AGLDisplay(int width, int height){
		create(width, height);
	}
	
	public AGLDisplay(AGLDisplayCap cap){
		DisplayMode mode = null;
		if(cap == AGLDisplayCap.FULLSCREEN){
			try{
				Display.setFullscreen(true);
			}
			catch(LWJGLException e){
				e.printStackTrace();
			}
			mode = Display.getDesktopDisplayMode();
			create(mode, true);
		}
		else if(cap == AGLDisplayCap.NONE){
			create(800, 600);
		}
	}
	
	public void create(int width, int height){
		create(new DisplayMode(width, height), false);
	}
	
	public static AGLDisplay getCurrentDisplay(){
		return currentDisplay;
	}
	
	private void create(DisplayMode mode, boolean fullscreen){
		try{
			Display.setDisplayMode(mode);
			Display.setFullscreen(fullscreen);
			Display.create();
			this.mode = mode;
			viewport = BufferUtils.createIntBuffer(4);
			viewport.put(0);
			viewport.put(0);
			viewport.put(getWidth());
			viewport.put(getHeight());
		}
		catch(Exception e){
			throw new AGLException("An Error occured creating display.");
		}
		currentDisplay = this;
	}
	
	public boolean isCloseRequested(){
		return Display.isCloseRequested();
	}
	
	public int getWidth(){
		return mode.getWidth();
	}
	
	public int getHeight(){
		return mode.getHeight();
	}
	
	public float getAspect(){
		return (float) getWidth() / (float) getHeight();
	}
}
