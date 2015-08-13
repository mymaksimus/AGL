package de.skysoldier.agl2test.bonetest2;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGL3dApplication;
import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderController;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderObject;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLView;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLViewPart;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGL3dCamera;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLProjection;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniform;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniformBinding;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniformType;

public class HandTest extends AGL3dApplication {
	
	private Vector3f eye, at, up;
	private AGLUniform colorUniform;
	
	public HandTest(){
		AGLGlslAttribute attributes[] = new AGLGlslAttribute[]{
			AGLGlslAttribute.createAttributeVec3("vertexIn"),
		};
		
		AGLShaderProgram handProgram = new AGLShaderProgram(getVShader(), getFShader(), attributes);
		colorUniform = new AGLUniform(AGLUniformType.VEC4, handProgram, "color");

		AGLView world = new AGLView(getCamera());
		AGLViewPart handPart = new AGLViewPart(handProgram);
		buildAxis(handPart, attributes);
		buildJoint(6f, handPart, attributes);
		world.addViewPart(handPart);
		
		eye = new Vector3f(0, 1, 5);
		at = new Vector3f(0, 0, 0);
		up = new Vector3f(0, 1, 0);
		getCamera().lookAt(eye, at, up);
		
		AGLRenderController.bindViews(world);
		AGLRenderController.init(true, true);
		runGameLoop(5);
	}
	
	public void buildJoint(float radius, AGLViewPart part, AGLGlslAttribute attributes[]){
		int numPointsPerStrip = 30, numStrips = 10;
		float data[] = new float[numPointsPerStrip * 3 * numStrips];
		for(int i = 0; i < numStrips; i++){
			float anglePhiDegrees = i * (360.0f / numStrips);//i * (360.0f / (numStrips - 1)); 
			float anglePhiRadians = (float) Math.toRadians(anglePhiDegrees);
			for(int j = i * numPointsPerStrip * 3; j < (i * numPointsPerStrip * 3) + numPointsPerStrip * 3; j += 3){
				float angleThetaDegrees = ((j - (i * numPointsPerStrip * 3)) / 3) * (360.0f / (numPointsPerStrip - 1));
				float angleThetaRadians = (float) Math.toRadians(angleThetaDegrees + 90);
				float cosPhi = (float) Math.cos(anglePhiRadians);
				float sinPhi = (float) Math.sin(anglePhiRadians);
				float cosTheta = (float) Math.cos(angleThetaRadians);
				float sinTheta = (float) Math.sin(angleThetaRadians);
				float x = cosTheta * cosPhi;
				float y = sinTheta;
				float z = cosTheta * sinPhi;
				data[j + 0] = x * radius; 
				data[j + 1] = y * radius;  
				data[j + 2] = z * radius; 
			}
		}
		AGLAsset asset = new AGLAsset(new AGLMesh(new AGLMesh(data, AGLDrawMode.LINE_STRIP), attributes), null);
		AGLRenderObject sphere = new AGLRenderObject(asset);
		sphere.scaleTo(0.2f, 0.2f, 0.2f);
		AGLUniformBinding binding = new AGLUniformBinding(colorUniform);
		binding.setData(0.7f, 0.0f, 0.5f, 1.0f);
		sphere.addUniformBinding(binding);
		part.addRenderObjects(sphere);
	}
	
	private void buildAxis(AGLViewPart part, AGLGlslAttribute attributes[]){
		AGLAsset axis = new AGLAsset(new AGLMesh(new AGLMesh(new float[]{
				0, 0, 0, 2, 0, 0,
				0, 0, 0, 0, 2, 0,
				0, 0, 0, 0, 0, 2
		}, AGLDrawMode.LINES), attributes), null);
		AGLRenderObject axisObject = new AGLRenderObject(axis);
		AGLUniformBinding binding = new AGLUniformBinding(colorUniform);
		binding.setData(1.0f, 1.0f, 1.0f, 1.0f);
		axisObject.addUniformBinding(binding);
		part.addRenderObjects(axisObject);
	}
	
	float eyeAngle = 0;
	public void run(){
		super.run();
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			eyeAngle += AGLRenderController.getDeltaS();
			eye.x = (float) Math.sin(eyeAngle) * 5;
			eye.z = (float) Math.cos(eyeAngle) * 5;
			getCamera().lookAt(eye, at, up);
		}
	}
	
	public AGL3dCamera buildCamera(){
		return new AGL3dCamera(new AGLProjection.PerspectiveProjection(45f, 0.1f, 1000.0f));
	}
	
	public AGLDisplay buildDisplay(){
		return new AGLDisplay(AGLDisplayCap.NONE);
	}
	
	private String getVShader(){
		return 
			"#version 150												\n"+
			"in vec3 vertexIn;											\n"+
			"uniform mat4 camera;										\n"+
			"uniform mat4 model;										\n"+
			"void main(){												\n"+
			"gl_Position = camera * model * vec4(vertexIn, 1.0);		\n"+
			"}";
	}
	
	private String getFShader(){
		return
			"#version 150						\n"+
			"uniform vec4 color;				\n"+
			"out vec4 outColor;					\n"+
			"void main(){						\n"+
			"outColor = color;					\n"+
			"}";
	}
	
	public static void main(String[] args){
		new HandTest();
	}
}
