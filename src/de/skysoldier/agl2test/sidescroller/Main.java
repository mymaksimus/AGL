package de.skysoldier.agl2test.sidescroller;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGL3dApplication;
import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderController;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderObject;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLView;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLViewPart;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGL3dCamera;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLProjection;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLFragmentShader;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLVertexShader;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniform;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniformBinding;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLUniformType;
import de.skysoldier.agl2test.sidescroller.light.Light;

public class Main extends AGL3dApplication {
	
	private Vector3f eye = new Vector3f(0, 2f, 2), at = new Vector3f(0, 1.5f, -2), up = new Vector3f(0, 1, 0);
	private AGLRenderObject player;
	private AGLViewPart simplePart;
	private AGLUniform colorUniform;
	
	private AGLFragmentShader lightShaderGlobal;
	
	private Light light1, light2;
	
	public Main(){
		lightShaderGlobal = new AGLFragmentShader(getShaderResource("light.sh_g.f"));
		
		AGLView world = new AGLView(getCamera());
		world.addViewPart(buildLevelPart());
//		world.addViewPart(buildSimplePart());
		
		getCamera().lookAt(eye, at, up);
		GL11.glLineWidth(1);
		AGLRenderController.bindViews(world);
		AGLRenderController.init(true, true);
		runGameLoop(3);
	}
	
	private AGLViewPart buildLevelPart(){
		AGLGlslAttribute attributes[] = new AGLGlslAttribute[]{
			AGLGlslAttribute.createAttributeVec3("vertexIn"),
			AGLGlslAttribute.createAttributeVec3("normalIn"),
			AGLGlslAttribute.createAttributeVec2("texCoords")
		};
		AGLVertexShader quadVertexShader = new AGLVertexShader(getShaderResource("quad.sh.v"));
		AGLFragmentShader quadFragmentShader = new AGLFragmentShader(getShaderResource("quad.sh.f"));
		AGLShaderProgram quadProgram = new AGLShaderProgram(attributes, lightShaderGlobal, quadVertexShader, quadFragmentShader);
		
		AGLViewPart levelPart = new AGLViewPart(quadProgram);
		Assets.BACKGROUND.create(attributes, Texture.BACKGROUND);
		Assets.GROUND.create(attributes, Texture.GROUND);		
		Assets.PLAYER.create(attributes, Texture.PLAYER);
		
		AGLRenderObject ground = new AGLRenderObject(Assets.GROUND.getAsset());
		levelPart.addRenderObjects(ground);
		AGLRenderObject background = new AGLRenderObject(Assets.BACKGROUND.getAsset());
		levelPart.addRenderObjects(background);
		player = new AGLRenderObject(Assets.PLAYER.getAsset());
		player.translateTo(0, 0, -2);
		player.rotateY((float) Math.toRadians(90));
		levelPart.addRenderObjects(player);
		
		light1 = new Light(quadProgram, 0);
		light1.setPosition(new Vector4f(0, 1, -2, 1));
		light1.setColor(new Vector3f(1.0f, 1.0f, 1.0f));
		light1.setAmbient(new Vector3f(0.02f, 0.02f, 0.02f));
		light1.setAttenuation(0.2f);
		light1.updateAll();
		
		light2 = new Light(quadProgram, 1);
		light2.setPosition(new Vector4f(0, 10, -100, 0));
		light2.setColor(new Vector3f(1.0f, 1.0f, 0.8f));
		light2.setAmbient(new Vector3f(0.2f, 0.2f, 0.2f));
		light2.updateAll();
		
		new AGLUniform(AGLUniformType.INTEGER, quadProgram, "lightCount").putData(true, 2);
		
		return levelPart;
	}
	
//	private AGLViewPart buildSimplePart(){
//		AGLGlslAttribute attributes2[] = new AGLGlslAttribute[]{
//				AGLGlslAttribute.createAttributeVec3("vertexIn")
//		};
//		AGLVertexShader simpleVertexShader = new AGLVertexShader(getShaderResource("simple.sh.v"));
//		AGLFragmentShader simpleFragmentShader = new AGLFragmentShader(getShaderResource("simple.sh.f"));
//		AGLShaderProgram simpleProgram = new AGLShaderProgram(attributes2, simpleVertexShader, simpleFragmentShader);
//		Assets.TEST_PLAYER.create(attributes2);
//		colorUniform = new AGLUniform(AGLUniformType.VEC4, simpleProgram, "color");
//		simplePart = new AGLViewPart(simpleProgram);
//		playerTest = new AGLRenderObject(Assets.TEST_PLAYER.getAsset());
//		playerTest.translate(new Vector3f(0, 0.11f, -2));
//		AGLUniformBinding binding = colorUniform.createBinding();
//		binding.putData(1.0f, 0.0f, 0.0f, 1.0f);
//		playerTest.addUniformBinding(binding);
//		simplePart.addRenderObjects(playerTest);
//		
//		return simplePart;
//	}
	
	public void run(){
//		super.run();
//		float newx = (float) Math.sin(AGLRenderController.getTicksInSeconds()) * 3f;
//		eye.x = newx;
//		at.x = newx;
//		System.out.println(at.x);
//		getCamera().lookAt(eye, at, up);
		float delta = AGLRenderController.getDeltaS();
		
		float sxRelative = 0, szRelative = 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) szRelative = -2f * delta;
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) szRelative = 2f * delta;
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) sxRelative = -2f * delta;
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) sxRelative = 2f * delta;
		
		Vector3f lastPosition = new Vector3f(player.getPosition());
		player.translate(sxRelative, 0, szRelative);
		float sxAbsolute = player.getPosition().x - lastPosition.x;
		float szAbsolute = player.getPosition().z - lastPosition.z;
		
		if(player.getPosition().z > -1 || player.getPosition().z < -7 || player.getPosition().x > 3 || player.getPosition().x < -3){
			player.translate(-sxRelative, 0, -szRelative);
		}
//		System.out.println(player.getPosition());
//		float bx = 0, bz = 0;
//		if(player.getPosition().x < -10 || player.getPosition().x > 5) bx = -sx;
//		if(player.getPosition().z < -9 || player.getPosition().z > -0.6f) bz = -sz;
//		player.translate(bx, 0, bz);
		
//		if(playerTest.getPosition().z < 5 || playerTest.getPosition().z > 5){
//			playerTest.translateGlobal(playerTest.getPosition().x, playerTest.getPosition().y, playerTest.getPosition().z);
//		}
		
		
//		System.out.println(player.getPosition().x - at.x);
		if(Math.abs(player.getPosition().x - at.x) > 1){
			at.x += sxAbsolute;
			eye.x += sxAbsolute;
		}
		if(Math.abs(player.getPosition().z - at.z) > 1){
			at.z += szAbsolute;
			eye.z = at.z + 4f;	
		}
		getCamera().lookAt(eye, at, up);
		
		while(Keyboard.next()){
			int key = Keyboard.getEventKey();
			if(Keyboard.getEventKeyState()){
				if(key == Keyboard.KEY_RETURN){
					AGLRenderObject poop = new AGLRenderObject(Assets.TEST_PLAYER.getAsset()){
						public void update(){
							translate(0, AGLRenderController.getDeltaS(), 0);
							if(getPosition().y > 2) setRemoveRequested(true);
						}
					};
					AGLUniformBinding binding = colorUniform.createBinding();
					binding.putData(0.0f, 0.3f, 1.0f, 1.0f);
					poop.addUniformBinding(binding);
					poop.translate(player.getPosition());
					simplePart.addRenderObjects(poop);
				}
			}
		}
		
//		Color c = Color.getHSBColor(0.5f * (float) (Math.sin(AGLRenderController.getTicksInSeconds() * 0.08f) + 1), 1, 1);
//		System.out.println(c);
//		light1.setColor(new Vector3f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f));
//		light1.updateColor();
//		light1.setAttenuation((float) (0.1f * (Math.sin(AGLRenderController.getTicksInSeconds()) + 1)));
//		light1.updateAttenuation();
		
//		Vector3f lightPosition = new Vector3f(playerTest.getPosition());
//		lightPosition.y += 2;
//		light1.setPosition(lightPosition);
//		light1.updatePosition();
	}
	
	public static AGLResource getShaderResource(String name){
		return new AGLResource("sidescroller/shader/" + name);
	}
	
	public static AGLResource getTextureResource(String name){
		return new AGLResource("sidescroller/textures/" + name);
	}
	
	public AGL3dCamera buildCamera(){
		return new AGL3dCamera(new AGLProjection.PerspectiveProjection(65, 0.5f, 1000.0f));
	}
	
	public AGLDisplay buildDisplay(){
		return new AGLDisplay(AGLDisplayCap.NONE);
	}
	
	public static void main(String[] args){
		new Main();
	}
}