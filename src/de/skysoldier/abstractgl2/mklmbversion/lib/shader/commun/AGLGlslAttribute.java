package de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGL;

public class AGLGlslAttribute {
	
	private String name;
	private int size;
	private boolean normalized;
	private int id;
	
	public AGLGlslAttribute(String name, int size, boolean normalized, int id){
		this.name = name;
		this.size = size;
		this.normalized = normalized;
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getSize(){
		return size;
	}
	
	public boolean isNormalized(){
		return normalized;
	}
	
	public int getId(){
		return id;
	}
	
	public static AGLGlslAttribute createAttributeVec3(String name){
		return new AGLGlslAttribute(name, 3, false, AGL.genId());
	}

	public static AGLGlslAttribute createAttributeVec2(String name){
		return new AGLGlslAttribute(name, 2, false, AGL.genId());
	}
	
	public static AGLGlslAttribute createAttributeFloat(String name){
		return new AGLGlslAttribute(name, 1, false, AGL.genId());
	}
	
	public String toString(){
		return "[GLslAttrib] {name: " + name + ", size: " + size + ", id: " + id + "}"; 
	}
}