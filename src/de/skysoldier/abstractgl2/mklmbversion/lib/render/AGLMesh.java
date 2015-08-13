package de.skysoldier.abstractgl2.mklmbversion.lib.render;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;

public class AGLMesh {
	
	private float data[];
	private AGLDrawMode drawMode;
	
	public AGLMesh(AGLResource resource, AGLDrawMode drawMode){
		String raw[] = resource.toStringResource().replaceAll("[\\s\\t\\n\\r]", "").split(",");
		float data[] = new float[raw.length];
		for(int i = 0; i < raw.length; i++){
			data[i] = Float.parseFloat(raw[i]);
		}
		init(data, drawMode);
	}
	
	public AGLMesh(float data[], AGLDrawMode drawMode){
		init(data, drawMode);
	}
	
	private void init(float data[], AGLDrawMode drawMode){
		this.data = data;
		this.drawMode = drawMode;
	}
	
	public float[] getData(){
		return data;
	}
	
	public AGLDrawMode getDrawMode(){
		return drawMode;
	}
}
