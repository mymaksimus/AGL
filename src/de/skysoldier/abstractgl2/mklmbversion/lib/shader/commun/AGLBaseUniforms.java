package de.skysoldier.abstractgl2.mklmbversion.lib.shader.commun;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGL;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGL.Property;
import de.skysoldier.abstractgl2.mklmbversion.lib.shader.AGLShaderProgram;

public class AGLBaseUniforms {
	
	public static AGLUniform createBaseModelUniform(AGLShaderProgram program){
		return new AGLUniform(AGLUniformType.MATRIX4, program, AGL.getProperty(Property.DEFAULT_MODEL_UNIFORM_NAME));
	}
	
	public static AGLUniform createBaseCameraUniform(AGLShaderProgram program){
		return new AGLUniform(AGLUniformType.MATRIX4, program, AGL.getProperty(Property.DEFAULT_CAMERA_UNIFORM_NAME));
	}
}