package de.skysoldier.agl2test.bonetest;

import java.util.ArrayList;

import de.skysoldier.abstractgl2.mklmbversion.lib.render.AGLRenderController;

public class BoneAngleAnimation {
	
	private float currentTime;
	private float valueDelta, timeDelta;
	private float previousTime;
	private ArrayList<Keyframe> keyframes;
	private int currentKeyFrameIndex;
	private Keyframe from, to;
	private Bone toAnimate;
	
	public BoneAngleAnimation(){
		keyframes = new ArrayList<>();
	}
	
	public void addKeyframe(Keyframe k){
		keyframes.add(k);
	}
	
	public void setup(Bone b, float startTime){
		this.toAnimate = b;
		currentTime = startTime;
		for(int i = 0; i < keyframes.size(); i++){
			Keyframe k = keyframes.get(i);
			if(k.getTime() >= startTime) break;
			currentKeyFrameIndex = i;
		}
		nextKeyframe();
	}
	
	public void update(){
		float linearAnimationState = (currentTime - previousTime) / timeDelta;
		float easedAnimationState = (float) (-2 * Math.pow(linearAnimationState, 3) + 3 * Math.pow(linearAnimationState, 2));
		float newAngle = from.getValue() + valueDelta * easedAnimationState;
		toAnimate.rotateToZAbout((float) Math.toRadians(newAngle), toAnimate.getJointShift());
		currentTime += AGLRenderController.getDeltaS();
		if(currentTime >= to.getTime()){
			currentKeyFrameIndex++;
			if(currentKeyFrameIndex == keyframes.size() - 1){
				currentTime = 0;
				currentKeyFrameIndex = 0;
			}
			nextKeyframe();
		}
		for(Bone b : toAnimate.getChildren()){
			float endx = toAnimate.getPosition().getX() + (float) Math.sin(toAnimate.getRotation().getZ()) * toAnimate.getLength();
			float endy = toAnimate.getPosition().getY() - (float) Math.cos(toAnimate.getRotation().getZ()) * toAnimate.getLength();
			if(toAnimate.debug()){
				endx = (toAnimate.getPosition().getX() + (float) Math.sin(toAnimate.getRotation().getZ())) + (float) Math.sin(toAnimate.getRotation().getZ()) * toAnimate.getLength();
			}
			
			float joinx = endx;
			float joiny = endy;
			b.getAnimation().update();
			b.translateToGlobal(joinx, joiny, 0);
		}
	}
	
	private void nextKeyframe(){
		this.from = keyframes.get(currentKeyFrameIndex);
		this.to = keyframes.get(currentKeyFrameIndex + 1);
		valueDelta = to.getValue() - from.getValue();
		timeDelta = to.getTime() - from.getTime();
		previousTime = keyframes.get(currentKeyFrameIndex).getTime();
//		System.out.println("[ANIMATION-DATA Time-Line-Index: " + currentKeyFrameIndex + "]  Angle-From: " + angleFrom + " | Angle-To: " + angleTo + " | Angle-Delta: " + angleDelta + " | Pre-Animation-Step: " + preAnimationStep + " | Animation-Step-Delta: " + animationStepDelta);
	}
}
