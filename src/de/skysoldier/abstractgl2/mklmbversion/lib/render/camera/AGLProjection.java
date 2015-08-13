package de.skysoldier.abstractgl2.mklmbversion.lib.render.camera;

import org.lwjgl.util.vector.Matrix4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLProjectionType;

public abstract class AGLProjection {
	
	private AGLCameraClipSpace clipSpace;
	private AGLProjectionType type;
	
	public AGLProjection(AGLCameraClipSpace clipSpace, AGLProjectionType type){
		this.clipSpace = clipSpace;
		this.type = type;
	}
	
	public AGLProjectionType getType(){
		return type;
	}
	
	public AGLCameraClipSpace getCameraClipSpace(){
		return clipSpace;
	}
	
	protected abstract Matrix4f getMatrix();
	
	public static class PerspectiveProjection extends AGLProjection {
		
		public PerspectiveProjection(float fov, float znear, float zfar){
			super(new AGLCameraClipSpace(znear, zfar, fov), AGLProjectionType.PERSPECTIVE);
		}
		
		public Matrix4f getMatrix(){
			Matrix4f m = new Matrix4f();
			final double f = (1.0 / Math.tan(Math.toRadians(super.clipSpace.getFov() / 2.0)));
            m.m00 = (float) (f / AGLDisplay.getCurrentDisplay().getAspect());
            m.m11 = (float) f;
            m.m22 = (super.clipSpace.getzFar() + super.clipSpace.getzNear()) / (super.clipSpace.getzNear() - super.clipSpace.getzFar());
            m.m23 = -1;
            m.m32 = (2 * super.clipSpace.getzFar() + super.clipSpace.getzNear()) / (super.clipSpace.getzNear() - super.clipSpace.getzFar());
            m.m33 = 0;
			return m;
		}
	}
	
	public static class OrthogonalProjection extends AGLProjection {
		
		public OrthogonalProjection(float top){
			super(new AGLCameraClipSpace(top), AGLProjectionType.ORTHOGONAL);
		}
		
		protected Matrix4f getMatrix(){
			Matrix4f m = new Matrix4f();
			m.m00 = 2 / (super.clipSpace.getRight() - super.clipSpace.getLeft());
	        m.m03 = -(super.clipSpace.getRight() + super.clipSpace.getLeft()) / (super.clipSpace.getRight() - super.clipSpace.getLeft());
	        m.m11 = 2 / (super.clipSpace.getTop() - super.clipSpace.getBottom());  
	        m.m13 = -(super.clipSpace.getTop() + super.clipSpace.getBottom()) / (super.clipSpace.getTop() - super.clipSpace.getBottom());
	        m.m22 = -2 / (super.clipSpace.getzFar() - super.clipSpace.getzNear());
	        return m;
		}
	}
}
