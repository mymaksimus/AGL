package de.skysoldier.agl2test.lightingtest;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGL3dApplication;
import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderController;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderObject;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLView;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLViewPart;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGL3dCamera;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLProjection;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.texture.AGLTexture;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLFragmentShader;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLVertexShader;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniform;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniformType;
import de.skysoldier.abstractgl2.mklmbversion.lib.utils.AGLModelLoader;

public class Test extends AGL3dApplication {
	
	private Light light1;
	
	public Test(){
		String vShaderContent = 
				"#version 150\n"+
				"in vec3 vertex;\n"+
				"in vec3 normal;\n"+
				"in vec2 vtUnused;\n"+
				"uniform mat4 camera;\n"+
				"uniform mat4 model;\n"+
				"out vec3 fVertex;\n"+
				"out vec3 fNormal;\n"+
				"out vec2 fVt;\n"+
				"vec3 getNormal();\n"+
				"void main(){\n"+
				"fVertex = vertex;\n"+
				"fNormal = normal;\n"+
				"fVt = vtUnused;\n"+
				"gl_Position = camera * model * vec4(vertex, 1);\n"+
				"}\n";
		String fShaderContent = 
				"#version 150\n"+
				"in vec2 fVt;\n"+
				"uniform sampler2D tex;\n"+
				"out vec4 outputColor;\n"+
				"vec4 getLightColor(vec4);\n"+
				"void main(){\n"+
				"outputColor = getLightColor(texture2D(tex, fVt));\n"+
				"}\n";
		AGLVertexShader vShader = new AGLVertexShader(vShaderContent);
		AGLFragmentShader fShader = new AGLFragmentShader(fShaderContent);
		AGLFragmentShader lightShader = new AGLFragmentShader(new AGLResource("sidescroller/shader/light.sh_g.f"));
		AGLShaderProgram program = new AGLShaderProgram(vShader, fShader, lightShader);
		AGLView world = new AGLView(getCamera());
		AGLViewPart ngonPart = new AGLViewPart(program);
		
		light1 = new Light(program, 0);
		light1.setPosition(new Vector4f(0, 0, 1.5f, 1));
		light1.setColor(new Vector3f(1, 1, 1));
		light1.setAmbient(new Vector3f(0.08f, 0.08f, 0.08f));
		light1.setAttenuation(0.1f);
		light1.updateAll();
		
		new AGLUniform(AGLUniformType.INTEGER, program, "lightCount").putData(true, 2);
		
		AGLMesh ngonData = new AGLMesh(AGLModelLoader.loadObj(new AGLResource("ngon.obj"), true), AGLDrawMode.TRIANGLES);
		AGLAsset ngonAsset = ngonPart.createAsset(ngonData);
		AGLTexture tex = new AGLTexture(new AGLResource("ngon.png"));
		AGLRenderObject ngon1 = new AGLRenderObject(ngonAsset){
			public void update(){
				rotateY((float) Math.toRadians(AGLRenderController.getDeltaS() * 20)); 
				rotateX((float) Math.toRadians(AGLRenderController.getDeltaS() * 15)); 
			}
		};
		ngon1.setTexture(tex);
		ngonPart.addRenderObjects(ngon1);
		world.addViewPart(ngonPart);
		getCamera().lookAt(new Vector3f(0, 1, 2.5f), new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
		Mouse.setGrabbed(true);
		AGLRenderController.bindViews(world);
		AGLRenderController.init(false, true);
		runGameLoop(3);
	}
	
	Vector3f p1 = new Vector3f(0, 0, 1);
	float h;
	public void run(){
		int dx = Mouse.getDX();
		int dy = Mouse.getDY();
		p1.x += dx * AGLRenderController.getDeltaS();
		p1.y += dy * AGLRenderController.getDeltaS();
		light1.setPosition(p1);
		light1.updatePosition();
		h += Mouse.getDWheel() * AGLRenderController.getDeltaS();
		if(h > 1) h = 0; 
		if(h < 0) h = 1;
		Color color = Color.getHSBColor(h, 1, 1);
		light1.setColor(new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
		light1.updateColor();
		super.run();
	}
	
	public AGL3dCamera buildCamera(){
		return new AGL3dCamera(new AGLProjection.PerspectiveProjection(45, 0.1f, 10.0f));
	}
	
	public AGLDisplay buildDisplay(){
		return new AGLDisplay(AGLDisplayCap.FULLSCREEN);
	}
	
	
	public static void main(String[] args){
		new Test();
	}
}
