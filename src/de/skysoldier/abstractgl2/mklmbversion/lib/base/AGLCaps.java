package de.skysoldier.abstractgl2.mklmbversion.lib.base;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector3f;

public class AGLCaps {

	public enum AGLDisplayCap {
		FULLSCREEN,
		NONE
	}
	
	public enum AGLShaderType {
		VERTEX,
		FRAGMENT
	}
	
	public enum AGLProjectionType {
		PERSPECTIVE,
		ORTHOGONAL
	}
	
	public enum AGLAxis {
		X(new Vector3f(1.0f, 0.0f, 0.0f)),
		Y(new Vector3f(0.0f, 1.0f, 0.0f)),
		Z(new Vector3f(0.0f, 0.0f, 1.0f));
		
		Vector3f vector;
		
		private AGLAxis(Vector3f vector){
			this.vector = vector;
		}
		
		public Vector3f getVector(){
			return vector;
		}
		
		public Vector3f getVectorCopy(){
			return new Vector3f(vector);
		}
	}
	
	public enum AGLBufferMode {
		DRAW(GL15.GL_STATIC_DRAW),
		READ(GL15.GL_STATIC_READ),
		COPY(GL15.GL_STATIC_COPY);
		
		int rawMode;
		
		private AGLBufferMode(int rawMode){
			this.rawMode = rawMode;
		}
		
		public int getRawMode(){
			return rawMode;
		}
	}

	public enum AGLDrawMode {
		TRIANGLES(GL11.GL_TRIANGLES),
		TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP),
		TRIANGLE_FAN(GL11.GL_TRIANGLE_FAN),
		LINES(GL11.GL_LINES),
		LINE_STRIP(GL11.GL_LINE_STRIP),
		POINTS(GL11.GL_POINTS);
		
		int rawMode;
		
		private AGLDrawMode(int rawMode){
			this.rawMode = rawMode;
		}
		
		public int getRawMode(){
			return rawMode;
		}
	}
	
	public enum AGLGlslVariableType {
		
		INT(1),
		FLOAT(1),
		VEC2(2),
		VEC3(3),
		VEC4(4),
		MAT2(4),
		MAT3(9),
		MAT4(16);
		
		private int size;
		
		AGLGlslVariableType(int size){
			this.size = size;
		}
		
		public int getSize(){
			return size;
		}
	}
	
	public enum AGLMouseButton {
		BUTTON0,
		BUTTON1,
		BUTTON2,
		BUTTON3,
		BUTTON4,
		BUTTON5,
		BUTTON6,
		BUTTON7,
		BUTTON8,
		BUTTON9,
		BUTTON10,
		BUTTON11,
		BUTTON12,
		BUTTON13,
		BUTTON14,
		BUTTON15
	}
}