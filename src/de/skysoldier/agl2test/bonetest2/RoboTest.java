package de.skysoldier.agl2test.bonetest2;

import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGL3dApplication;
import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLAxis;
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
import de.skysoldier.abstractgl2.mklmbversion.lib.render.texture.AGLTexture;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;


public class RoboTest extends AGL3dApplication {
	
	private AGLRenderObject roboTorso, roboHead, roboArmLeft, roboArmRight, roboLegLeft, roboLegRight;
	private Vector3f eye, at, up;
	
	public RoboTest(){
		AGLGlslAttribute attributes[] = new AGLGlslAttribute[]{
			AGLGlslAttribute.createAttributeVec3("vertexIn"),
		};
		AGLGlslAttribute texturedAttributes[] = new AGLGlslAttribute[]{
				attributes[0],
				AGLGlslAttribute.createAttributeVec2("texCoords")
		};
		
		AGLShaderProgram robotProgram = new AGLShaderProgram(getVShader(), getFShader(), attributes);
		AGLView world = new AGLView(getCamera());
		AGLViewPart roboPart = new AGLViewPart(robotProgram);
		buildRobot(roboPart, attributes);
		world.addViewPart(roboPart);
		
		AGLShaderProgram groundProgram = new AGLShaderProgram(getTexturedVShader(), getTexturedFShader(), texturedAttributes);
		AGLViewPart groundPart = new AGLViewPart(groundProgram);
		buildGround(groundPart, texturedAttributes);
		world.addViewPart(groundPart);
		
		eye = new Vector3f(10, 10, -10);
		at = new Vector3f(0, 1.3f, 0);
		up = new Vector3f(0, 1, 0);
		getCamera().lookAt(eye, at, up);
		
		AGLRenderController.bindViews(world);
		AGLRenderController.init(true, true);
		runGameLoop(5);
	}
	
	private void buildGround(AGLViewPart groundPart, AGLGlslAttribute... attributes){
		AGLAsset groundAsset = new AGLAsset(new AGLMesh(new AGLMesh(new float[]{
				-3.0f, 0.0f, 0.0f,	0, 0, 		
			     3.0f, 0.0f, 0.0f,  1, 0,
				-3.0f, 0.0f,100.0f,  0, 20,
				 3.0f, 0.0f,100.0f,  1, 20
		}, AGLDrawMode.TRIANGLE_STRIP), attributes), new AGLTexture(new AGLResource("box2.png")));
		AGLRenderObject ground = new AGLRenderObject(groundAsset);
		ground.getModel().translate(new Vector3f(0, -(2.5f + 2.7f + 0.1f), 0));
		groundPart.addRenderObjects(ground);
	}
	
	private void buildRobot(AGLViewPart roboPart, AGLGlslAttribute... attributes){
		roboTorso = new AGLRenderObject(createLinedCubeAsset(2.0f, 4.0f, 1.4f, attributes));
		roboHead = new AGLRenderObject(createLinedCubeAsset(1.2f, 1.0f, 0.7f, attributes));
		roboArmLeft = new AGLRenderObject(createLinedCubeAsset(0.7f, 2.4f, 0.7f, ObjectOrigin.NORTH, attributes));
		roboArmRight = new AGLRenderObject(createLinedCubeAsset(0.7f, 2.4f, 0.7f, ObjectOrigin.NORTH, attributes));
		roboLegLeft = new AGLRenderObject(createLinedCubeAsset(0.9f, 2.7f, 0.7f, ObjectOrigin.NORTH, attributes));
		roboLegRight = new AGLRenderObject(createLinedCubeAsset(0.9f, 2.7f, 0.7f, ObjectOrigin.NORTH, attributes));
		
		roboHead.getModel().translate(new Vector3f(0, 2.7f, 0));
		roboArmLeft.getModel().translate(new Vector3f(-1.7f, 1.4f, 0));
		roboArmRight.getModel().translate(new Vector3f(1.7f, 1.4f, 0));
		roboLegLeft.getModel().translate(new Vector3f(-0.5f, -2.5f, 0));
		roboLegRight.getModel().translate(new Vector3f(0.5f, -2.5f, 0));
		roboArmLeft.getModel().rotate((float) Math.toRadians(15), AGLAxis.X.getVector());
		roboArmRight.getModel().rotate((float) Math.toRadians(-15), AGLAxis.X.getVector());
		roboLegLeft.getModel().rotate((float) Math.toRadians(-15), AGLAxis.X.getVector());
		roboLegRight.getModel().rotate((float) Math.toRadians(15), AGLAxis.X.getVector());
		
		roboTorso.addSubRenderObject(roboHead);
		roboTorso.addSubRenderObject(roboArmLeft);
		roboTorso.addSubRenderObject(roboArmRight);
		roboTorso.addSubRenderObject(roboLegLeft);
		roboTorso.addSubRenderObject(roboLegRight);
		
		roboPart.addRenderObjects(roboTorso);
		roboPart.addRenderObjects(roboHead);
		roboPart.addRenderObjects(roboArmLeft);
		roboPart.addRenderObjects(roboArmRight);
		roboPart.addRenderObjects(roboLegLeft);
		roboPart.addRenderObjects(roboLegRight);		
	}
	
	private AGLAsset createLinedCubeAsset(float sizex, float sizey, float sizez, AGLGlslAttribute attributes[]){
		return createLinedCubeAsset(sizex, sizey, sizez, ObjectOrigin.CENTER, attributes);
	}
	
	private AGLAsset createLinedCubeAsset(float sizex, float sizey, float sizez, ObjectOrigin origin, AGLGlslAttribute attributes[]){
		float halfSizex = sizex * 0.5f, halfSizey = sizey * 0.5f, halfSizez = sizez * 0.5f;
		float data[] = new float[]{
				-halfSizex,-halfSizey,-halfSizez,	-halfSizex,-halfSizey, halfSizez,
				-halfSizex,-halfSizey, halfSizez,	 halfSizex,-halfSizey, halfSizez,
				 halfSizex,-halfSizey, halfSizez,	 halfSizex,-halfSizey,-halfSizez,
				 halfSizex,-halfSizey,-halfSizez,	-halfSizex,-halfSizey,-halfSizez,
		
				-halfSizex, halfSizey,-halfSizez,	-halfSizex, halfSizey, halfSizez,
				-halfSizex, halfSizey, halfSizez,	 halfSizex, halfSizey, halfSizez,
				 halfSizex, halfSizey, halfSizez,	 halfSizex, halfSizey,-halfSizez,
				 halfSizex, halfSizey,-halfSizez,	-halfSizex, halfSizey,-halfSizez,
				 
				-halfSizex,-halfSizey,-halfSizez, 	-halfSizex, halfSizey,-halfSizez,
				-halfSizex,-halfSizey, halfSizez,   -halfSizex, halfSizey, halfSizez,
				 halfSizex,-halfSizey, halfSizez,    halfSizex, halfSizey, halfSizez,
				 halfSizex,-halfSizey,-halfSizez,    halfSizex, halfSizey,-halfSizez,
		};
		if(origin == ObjectOrigin.NORTH){
			for(int i = 1; i < data.length; i+=3){
				data[i] -= halfSizey;
			}
		}
//		else if(origin == ObjectOrigin.SOUTH){
//			for(int i = 0; i < data.length; i++){
//				data[i] += halfSizey;
//			}
//		}
		AGLMesh roboTorsoData = new AGLMesh(data, AGLDrawMode.LINES);
		AGLMesh mesh = new AGLMesh(roboTorsoData, attributes);
		return new AGLAsset(mesh, null);
	}
	
	enum ObjectOrigin {
		NORTH, SOUTH, CENTER
	}
	
	
	float posz;
	public void run(){
		super.run();
		
		float rotation = AGLRenderController.getDeltaS() * (float) Math.sin(5 * AGLRenderController.getTicksInSeconds()) * 0.3f * 5f;
		roboLegRight.getModel().rotate(-rotation, AGLAxis.X.getVector());
		roboLegLeft.getModel().rotate(rotation, AGLAxis.X.getVector());
		roboArmRight.getModel().rotate(rotation, AGLAxis.X.getVector());
		roboArmLeft.getModel().rotate(-rotation, AGLAxis.X.getVector());
		
		float trans = AGLRenderController.getDeltaS() * 2;
		posz += trans;
		roboTorso.getModel().translate(new Vector3f(0, 0, trans));
	
		eye.x = (float) Math.sin(0.7f * AGLRenderController.getTicksInSeconds()) * 20;
		eye.z = (float) Math.cos(0.7f * AGLRenderController.getTicksInSeconds()) * 40 + at.z;
		at.z = posz;
		getCamera().lookAt(eye, at, up);
	}
	
	public AGL3dCamera buildCamera(){
		return new AGL3dCamera(new AGLProjection.PerspectiveProjection(45, 0.1f, 1000.0f));
	}
	
	public AGLDisplay buildDisplay(){
		return new AGLDisplay(AGLDisplayCap.FULLSCREEN);
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
			"out vec4 outColor;					\n"+
			"void main(){						\n"+
			"outColor = vec4(0, 1.0, 0, 1.0);	\n"+
			"}";
	}
	
	private String getTexturedVShader(){
		return 
			"#version 150												\n"+
			"in vec3 vertexIn;											\n"+
			"in vec2 texCoords;											\n"+
			"uniform mat4 camera;										\n"+
			"uniform mat4 model;										\n"+
			"out vec2 fragTexCoords;									\n"+
			"void main(){												\n"+
			"fragTexCoords = texCoords;									\n"+
			"gl_Position = camera * model * vec4(vertexIn, 1.0);		\n"+
			"}";
	}
	
	private String getTexturedFShader(){
		return
			"#version 150									\n"+
			"in vec2 fragTexCoords;							\n"+
			"uniform sampler2D texture;						\n"+
			"out vec4 outColor;								\n"+
			"void main(){									\n"+
			"vec4 tcolor = texture2D(texture, fragTexCoords);	\n"+
			"float value = (tcolor.r + tcolor.g + tcolor.b) / 3;\n"+
			"outColor = vec4(value, value, value, 1);\n"+
			"}";
	}
	
	public static void main(String[] args){
		new RoboTest();
	}
}
