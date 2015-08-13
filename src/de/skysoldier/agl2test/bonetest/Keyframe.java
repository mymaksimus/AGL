package de.skysoldier.agl2test.bonetest;

public class Keyframe {
	
	private float time;
	private float value;
	
	public Keyframe(float time, float value){
		this.time = time;
		this.value = value;
	}

	public float getTime(){
		return time;
	}

	public float getValue(){
		return value;
	}
}