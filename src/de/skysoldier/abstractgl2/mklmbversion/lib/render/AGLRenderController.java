package de.skysoldier.abstractgl2.mklmbversion.lib.render;

import java.util.ArrayList;
import java.util.Collections;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGL;

public final class AGLRenderController {
	
	private static ArrayList<AGLView> activeViews = new ArrayList<>();
	private static long lastFrame;
	private static long thisFrame;
	private static float delta;
	private static float deltams;
	private static float deltas;
	private static float fps;
	private static float ticksInSeconds;
	
	private AGLRenderController(){
		
	}
	
	public static void init(boolean alphaBlending, boolean depthTest){
		if(alphaBlending){
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		if(depthTest){
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDepthFunc(GL11.GL_LESS);
		}
        lastFrame = AGL.getTime();
	}
	
	public static void render(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);
		for(AGLView view : activeViews){
			view.render();
		}
		Display.update();
	}
	
	public static void updateTime(){
		thisFrame = AGL.getTime();
		delta = thisFrame - lastFrame;
		lastFrame = thisFrame;
		deltams = (float) (delta / 1e6);
		deltas = (float) (delta / 1e9);
		fps = (float) 1e9 / delta;
		ticksInSeconds += deltas;
	}
	
	public static ArrayList<AGLView> getActiveViews(){
		return activeViews;
	}
	
	public static float getFps(){
		return fps;
	}
	
	public static float getDeltaNs(){
		return delta;
	}
	
	public static float getDeltaMs(){
		return deltams;
	}
	
	public static float getDeltaS(){
		return deltas;
	}
	
	public static float getTicksInSeconds(){
		return ticksInSeconds;
	}
	
	public static void bindViews(AGLView... views){
		for(AGLView view : views){
			activeViews.add(view); 
		}
		Collections.sort(activeViews);
	}
	
	public static void unbindViews(AGLView... views){
		for(AGLView view : views){
			activeViews.remove(view);
		}
	}
	
	public static void unbindViews(){
		activeViews.clear();
	}
}