package de.skysoldier.abstractgl2.mklmbversion.lib.base;

import java.util.HashMap;
import java.util.Random;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource.LineFeedback;

public class AGL {

	private static int genId;
	private static Random random = new Random();
	private static HashMap<Property, String> properties = new HashMap<>();
	private static final String BASE_CONFIG_PATH = "base.cfg";
	
	static {
		AGLResource baseConfigResource = new AGLResource(BASE_CONFIG_PATH);
		baseConfigResource.toTextResource(new LineFeedback(){
			public void onLineRead(String line){
				try{
					String parts[] = line.replaceAll(" ", "").split("=");
					properties.put(Property.valueOf(parts[0]), parts[1]);
				}
				catch(Exception e){
					throw new AGLException("base config file broken.", e);
				}
			}
		});
		System.out.println(properties);
	}
	
	public static String getProperty(Property property){
		return properties.get(property);
	}
	
	public static Random getRandom(){
		return random;
	}
	
	public static long getTime(){
		return System.nanoTime();
	}
	
	public static int genId(){
		return genId++;
	}

	public static float[] getMatrixData(Matrix4f matrix){
		return new float[]{matrix.m00, matrix.m01, matrix.m02, matrix.m03,
				matrix.m10, matrix.m11, matrix.m12, matrix.m13, matrix.m20,
				matrix.m21, matrix.m22, matrix.m23, matrix.m30, matrix.m31,
				matrix.m32, matrix.m33};
	}
	
	public static float[] getVectorData(Vector2f vector){
		return new float[]{
			vector.x, vector.y
		};
	}
	
	public static float[] getVectorData(Vector3f vector){
		return new float[]{
			vector.x, vector.y, vector.z
		};
	}
	
	public static float[] getVectorData(Vector4f vector){
		return new float[]{
			vector.x, vector.y, vector.z, vector.w	
		};
	}
	
	public enum Property {
		DEFAULT_CAMERA_UNIFORM_NAME,
		DEFAULT_MODEL_UNIFORM_NAME
	}
}