package de.skysoldier.abstractgl2.mklmbversion.lib.render.camera;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;

public class AGLCameraClipSpace {
	
	private float top, right, bottom, left, znear, zfar, fov;
	
	public AGLCameraClipSpace(){
		this(1, 1 * AGLDisplay.getCurrentDisplay().getAspect(), -1, -1 * AGLDisplay.getCurrentDisplay().getAspect(), 0.1f, 1000.0f, 0);
	}
	
	public AGLCameraClipSpace(float znear, float zfar, float fov){
		this(0, 0, 0, 0, znear, zfar, fov);
	}
	
	public AGLCameraClipSpace(float top){
		this(top, 0.1f, 10, 0);
	}
	
	public AGLCameraClipSpace(float top, float znear, float zfar, float fov){
		this(top, top * AGLDisplay.getCurrentDisplay().getAspect(), -top, -top * AGLDisplay.getCurrentDisplay().getAspect(), znear, zfar, fov);
	}
	
	public AGLCameraClipSpace(float top, float right, float bottom, float left, float znear, float zfar, float fov){
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
		this.znear = znear;
		this.zfar = zfar;
		this.fov = fov;
	}

	public float getTop(){
		return top;
	}

	public float getRight(){
		return right;
	}

	public float getBottom(){
		return bottom;
	}

	public float getLeft(){
		return left;
	}

	public float getzNear(){
		return znear;
	}

	public float getzFar(){
		return zfar;
	}
	
	public float getFov(){
		return fov;
	}
}
