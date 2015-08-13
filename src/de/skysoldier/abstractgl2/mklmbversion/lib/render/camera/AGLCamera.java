package de.skysoldier.abstractgl2.mklmbversion.lib.render.camera;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGL;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLViewPart;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.geom.AGLBasicMovableMat4Object;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLBaseUniforms;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniform;

public class AGLCamera extends AGLBasicMovableMat4Object {

	private Matrix4f finalProjectionMatrix;
	private FloatBuffer cameraMatrixBuffer, projectionMatrixBuffer;
	private ArrayList<AGLUniform> connectedUniforms;
	private AGLProjection projection;
	
	public AGLCamera(AGLProjection projection){
		this.cameraMatrixBuffer = BufferUtils.createFloatBuffer(16);
		this.projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
		this.connectedUniforms = new ArrayList<>();
		this.projection = projection;
		fillBuffers();
	}
	
	public void addViewPartBinding(AGLViewPart part){
		connectedUniforms.add(AGLBaseUniforms.createBaseCameraUniform(part.getShaderProgram()));
	}
	
	public void updateData(){
		fillBuffers();
		finalProjectionMatrix = Matrix4f.mul(projection.getMatrix(), getModel(), new Matrix4f());
//		getModel().setIdentity();
		for(AGLUniform uniform : connectedUniforms){
			uniform.putData(true, AGL.getMatrixData(finalProjectionMatrix));
		}
	}
	
	public void update(){
		
	}
    
    private void fillBuffers(){
    	getModel().store(cameraMatrixBuffer);
		projection.getMatrix().store(projectionMatrixBuffer);
		cameraMatrixBuffer.rewind();
		projectionMatrixBuffer.rewind();
	}
    
	public FloatBuffer getCameraMatrixBuffer(){
		cameraMatrixBuffer.rewind();
		return cameraMatrixBuffer;
	}
	
	public FloatBuffer getProjectionMatrixBuffer(){
		projectionMatrixBuffer.rewind();
		return projectionMatrixBuffer;
	}
	
	public AGLProjection getProjection(){
		return projection;
	}
	
	public Matrix4f getProjectionMatrix(){
		return projection.getMatrix();
	}
	
	public Matrix4f getCameraMatrix(){
		return getModel();
	}
	
	public Matrix4f getFinalProjectionMatrix(){
		return finalProjectionMatrix;
	}
	
	public float getTop(){
		return getProjection().getCameraClipSpace().getTop();
	}
	
	public float getRight(){
		return getProjection().getCameraClipSpace().getRight();
	}

	public float getBottom(){
		return getProjection().getCameraClipSpace().getBottom();
	}
	
	public float getLeft(){
		return getProjection().getCameraClipSpace().getLeft();
	}
}