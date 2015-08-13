package de.skysoldier.abstractgl2.mklmbversion.lib.render;

import java.util.ArrayList;
import java.util.Collections;

import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLCamera;

public class AGLView implements Comparable<AGLView> {
	
	private ArrayList<AGLViewPart> parts;
	private AGLCamera camera;
	private int order;
	
	public AGLView(AGLCamera camera, int order){
		this.parts = new ArrayList<>();
		this.camera = camera;
		this.order = order;
	}
	
	public AGLView(AGLCamera camera){
		this(camera, 0);
	}
	
	protected void render(){
		camera.updateData();
		for(AGLViewPart part : parts){
			part.render();
		}
	}
	
	public void addViewPart(AGLViewPart part){
		getCamera().addViewPartBinding(part);
		part.setParentView(this);
		parts.add(part);
		Collections.sort(parts);
	}
	
	public int getOrder(){
		return order;
	}
	
	public AGLCamera getCamera(){
		return camera;
	}

	public int compareTo(AGLView view){
		if(order > view.getOrder()) return 1;
		return -1;
	}
}