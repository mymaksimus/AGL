	package de.skysoldier.abstractgl2.mklmbversion.lib.render.camera;

import org.lwjgl.util.vector.Vector3f;


public class AGL3dCamera extends AGLCamera {

	private float pitch, yaw;
	
	public AGL3dCamera(AGLProjection projection){
		super(projection);
	}
	
	public void update(){
		super.update();
//		final float step = 20;
//        float keySpeed = step;
//        final float mouseSpeed = 0.00001f * step;
//        final float rotVmax = 1;
//        final float rotVMin = -1;
//        
//		yaw += Mouse.getDX() * mouseSpeed;
//		pitch -= Mouse.getDY() * mouseSpeed;
//		if(pitch > rotVmax) pitch = rotVmax;
//        if(pitch < rotVMin) pitch = rotVMin;
//        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
//        	x -= (float) Math.sin(yaw) * keySpeed * AGLRenderController.getDeltaS();
//            z += (float) Math.cos(yaw) * keySpeed * AGLRenderController.getDeltaS();
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
//            x += (float) Math.sin(yaw) * keySpeed * AGLRenderController.getDeltaS();
//            z -= (float) Math.cos(yaw) * keySpeed * AGLRenderController.getDeltaS();
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
//            x += (float) Math.cos(yaw) * keySpeed * AGLRenderController.getDeltaS();
//            z += (float) Math.sin(yaw) * keySpeed * AGLRenderController.getDeltaS();
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
//            x -= (float) Math.cos(yaw) * keySpeed * AGLRenderController.getDeltaS();
//            z -= (float) Math.sin(yaw) * keySpeed * AGLRenderController.getDeltaS();
//        }
//        float rotX = (float) (Math.cos(yaw) * pitch);
//        float rotZ = (float) (Math.sin(yaw) * pitch);
//        final double sinX = Math.sin(rotX);
//        final double sinY = Math.sin(yaw);
//        final double sinZ = Math.sin(rotZ);
//        final double cosX = Math.cos(rotX);
//        final double cosY = Math.cos(yaw);
//        final double cosZ = Math.cos(rotZ);
//        getModel().m00 = (float) (cosY * cosZ + sinY * sinX * sinZ);
//        getModel().m01 = (float) (cosX * sinZ);
//        getModel().m02 = (float) (-sinY * cosZ + cosY * sinX * sinZ);
//        getModel().m10 = (float) (-cosY * sinZ + sinY * sinX * cosZ);
//        getModel().m11 = (float) (cosX * cosZ);
//        getModel().m12 = (float) (sinY * sinZ + cosY * sinX * cosZ);
//        getModel().m20 = (float) (sinY * cosX);
//        getModel().m21 = (float) (-sinX);
//        getModel().m22 = (float) (cosY * cosX);
//        getModel().m30 = getModel().m00 * x + getModel().m10 * y + getModel().m20 * z;
//        getModel().m31 = getModel().m01 * x + getModel().m11 * y + getModel().m21 * z;
//        getModel().m32 = getModel().m02 * x + getModel().m12 * y + getModel().m22 * z;
//        getModel().m33 = 1;
		
//		//lookat test
//		Vector3f eye = new Vector3f(1, 1, 1);
//		Vector3f at = new Vector3f(0, 0, 0);
//		Vector3f up = new Vector3f(0, 1, 0);
//		Vector3f zaxis = (Vector3f) Vector3f.sub(at, eye, new Vector3f()).normalise();
//		Vector3f xaxis = (Vector3f) Vector3f.cross(up, zaxis, new Vector3f()).normalise();
//		Vector3f yaxis = Vector3f.cross(zaxis, xaxis, new Vector3f());
//		
//		getModel().setIdentity();
//		getModel().m00 = xaxis.x;
//		getModel().m10 = yaxis.x;
//		getModel().m20 = zaxis.x;
//		getModel().m01 = xaxis.y;
//		getModel().m11 = yaxis.y;
//		getModel().m21 = zaxis.y;
//		getModel().m02 = xaxis.z;
//		getModel().m12 = yaxis.z;
//		getModel().m22 = zaxis.z;
//		getModel().m03 = -Vector3f.dot(xaxis, eye);
//		getModel().m13 = -Vector3f.dot(yaxis, eye);
//		getModel().m23 = -Vector3f.dot(zaxis, eye);
//		getModel().m33 = 1;
	
		//lookat test 2
	}
	
	public void lookAt(Vector3f eye, Vector3f at, Vector3f up){
		Vector3f forward = new Vector3f();
        Vector3f side = new Vector3f();
        Vector3f.sub(at, eye, forward);
        forward.normalise();
        Vector3f.cross(forward, up, side);
        side.normalise();
        Vector3f upCopy = new Vector3f(up);
        Vector3f.cross(side, forward, upCopy);
        getModel().setIdentity();
        getModel().m00 = side.x;
        getModel().m01 = side.y;
        getModel().m02 = side.z;
        getModel().m10 = upCopy.x;
        getModel().m11 = upCopy.y;
        getModel().m12 = upCopy.z;
        getModel().m20 = -forward.x;
        getModel().m21 = -forward.y;
        getModel().m22 = -forward.z;
        getModel().transpose();
        
        getModel().translate(new Vector3f(-eye.x, -eye.y, -eye.z));
//        Matrix4f translation = new Matrix4f();
//        translation.translate((Vector3f) eye.negate());
//        eye.negate();
//        Matrix4f.mul(getModel(), translation, getModel());
	}
	
	public float getPitch(){
		return pitch;
	}
	
	public float getYaw(){
		return yaw;
	}
}
