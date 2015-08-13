package de.skysoldier.agl2test.bonetest;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderObject;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLView;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLViewPart;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun.AGLGlslAttribute;

public class BoneTest {
	
	private AGLGlslAttribute attributes[];
	private AGLShaderProgram program;
	private AGLViewPart part;
	private ArrayList<Bone> rootBones;
	
	public AGLShaderProgram getShader(){
		return program;
	}
	
	public BoneTest(AGLView view, AGLDisplay display){
		attributes = new AGLGlslAttribute[]{
			AGLGlslAttribute.createAttributeVec2("vertexIn"),
			AGLGlslAttribute.createAttributeVec3("vertexColor")
		};		
		this.program = new AGLShaderProgram(new AGLResource("bonetest.shader"), "###", attributes);
		this.part = new AGLViewPart(program);
		view.addViewPart(part);
		part.addRenderObjects(new AGLRenderObject(new AGLAsset(new AGLMesh(new AGLMesh(new float[]{
				0, 0.7f, 1, 1, 1,
				0, -0.7f, 1, 1, 1,
				0.7f, 0, 1, 1, 1,
				-0.7f, 0,1, 1, 1
		}, AGLDrawMode.LINES), attributes), null)));
//		float[][] thighAnimationTimeline = new float[][]{
//				{0, 0.7f, 1, 2},
//				{30, -30, -30, 30},
//		};
//		float[][] shinAnimationTimeline = new float[][]{
//				{0, 1, 2},
//				{0, -120, 0},
//		};
//		float[][] footAnimationTimeline = new float[][]{
//				{0, 0.7f, 1, 2},
//				{90, -40, -45, 90},
//		};
//		Bone leftThigh = new Bone(0f, 0, 0.3f, thighAnimationTimeline, line, true);
//		Bone leftShin = new Bone(0f, -0.3f, 0.3f, shinAnimationTimeline, line, false);
//		Bone leftFoot = new Bone(0f, -0.6f, 0.1f, footAnimationTimeline, line, false);
//		leftThigh.addChildren(leftShin);
//		leftShin.addChildren(leftFoot);
//		this.thigh = leftThigh;
//		leftFoot.rotateZ((float) Math.toRadians(-20), 1);
//		foot.moveX(0.1f, 1);
//		part.addRenderObjects(leftThigh);
//		part.addRenderObjects(leftShin);
//		part.addRenderObjects(leftFoot);
		rootBones = new ArrayList<>();
		init(0.0f);
		init(1.0f);
	}
	
	private void init(float startTime){
		Bone backbone = new Bone(0, 0.5f, 0.5f, attributes);
		backbone.setJointShift(new Vector3f(0, -0.5f, 0));
		backbone.setDebug(true);
//		Bone head = new Bone(0, 0.5f, 0.1f, attributes, JoinType.JOIN_END);
		Bone thigh = new Bone(0, 0, 0.3f, attributes);
		Bone shin = new Bone(0, -0.3f, 0.3f, attributes);
		Bone foot = new Bone(0, -0.6f, 0.1f, attributes);
		backbone.addChildren(thigh);
//		backbone.addChildren(head);
		thigh.addChildren(shin);
		shin.addChildren(foot);
		rootBones.add(backbone);
		
		BoneAngleAnimation backboneAnimation = new BoneAngleAnimation();
		backboneAnimation.addKeyframe(new Keyframe(0, 5));
		backboneAnimation.addKeyframe(new Keyframe(0.5f, -5));
		backboneAnimation.addKeyframe(new Keyframe(1, 5));
		backbone.setAnimation(backboneAnimation, startTime);
		
//		BoneAngleAnimation headAnimation = new BoneAngleAnimation();
//		headAnimation.addKeyframe(new Keyframe(0, 0));
//		headAnimation.addKeyframe(new Keyframe(1, 0));
//		head.setAnimation(headAnimation, startTime);
		
		BoneAngleAnimation thighAnimation = new BoneAngleAnimation();
		thighAnimation.addKeyframe(new Keyframe(0.0f, 0));
		thighAnimation.addKeyframe(new Keyframe(1.0f, 30));
		thighAnimation.addKeyframe(new Keyframe(1.5f, 20));
		thighAnimation.addKeyframe(new Keyframe(2.0f, 0));
		thigh.setAnimation(thighAnimation, startTime);
		
		BoneAngleAnimation shinAnimation = new BoneAngleAnimation();
		shinAnimation.addKeyframe(new Keyframe(0.0f, 0));
		shinAnimation.addKeyframe(new Keyframe(0.75f, -50));
		shinAnimation.addKeyframe(new Keyframe(1.5f, 20));
		shinAnimation.addKeyframe(new Keyframe(2.0f, 0));
		shin.setAnimation(shinAnimation, startTime);
		
		BoneAngleAnimation footAnimation = new BoneAngleAnimation();
		footAnimation.addKeyframe(new Keyframe(0.0f, 90));
		footAnimation.addKeyframe(new Keyframe(0.75f, 60));
		footAnimation.addKeyframe(new Keyframe(1.5f, 110));
		footAnimation.addKeyframe(new Keyframe(2.0f, 90));
		foot.setAnimation(footAnimation, startTime);
		
		part.addRenderObjects(backbone);
//		part.addRenderObjects(head);
		part.addRenderObjects(thigh);
		part.addRenderObjects(shin);
		part.addRenderObjects(foot);
	}
	
	public void update(){
//		thigh.animate();
		for(Bone b : rootBones){
			b.getAnimation().update();
			//b.translateAbout(0.1f * AGLRenderController.getDeltaS(), 0, 0);
			//if(b.getPosition().getX() > display.getAspect() + 0.1f) b.translateTo(-display.getAspect() - 0.1f, b.getPosition().getY(), 0);
		}
	}
}