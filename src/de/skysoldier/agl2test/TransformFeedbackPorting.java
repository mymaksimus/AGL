package de.skysoldier.agl2test;
 
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL30.GL_TRANSFORM_FEEDBACK_BUFFER;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Matrix4f;

import de.skysoldier.abstractgl2.mklmbversion.lib.application.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLCamera;
import de.skysoldier.abstractgl2.mklmbversion.lib.render.camera.AGLProjection;
 
public class TransformFeedbackPorting {
 
	private int programId;
	private int floatSizeInBytes = Float.SIZE / 8;
	private int argumentCount = 10000000, floatsPerArgument = 9;
	private int firstVao, secondVao;
	private int firstBuffer, secondBuffer;
	
	public TransformFeedbackPorting() throws LWJGLException {
		argumentCount = Integer.parseInt(JOptionPane.showInputDialog("wie viele partikel? (maximal 238000000)"));
		
    	AGLDisplay d = new AGLDisplay(AGLDisplayCap.NONE);
//    	Display.setDisplayMode(new DisplayMode(800, 600));
//    	Display.setDisplayMode(Display.getDesktopDisplayMode());
//    	Display.setFullscreen(true);
//		Display.create();
    	
    	AGLCamera cam = new AGLCamera(new AGLProjection.OrthogonalProjection(1));
    	System.out.println(cam.getProjectionMatrix());
    	System.exit(0);
    	
    	
    	
    	AGLResource vertexShaderResource = new AGLResource("tf/TFvert.shader");
    	final int vshaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vshaderId, vertexShaderResource.toStringResource());
        GL20.glCompileShader(vshaderId);
        checkCompileErrors(vshaderId);
        
        AGLResource fragmentShaderResource = new AGLResource("tf/TFfrag.shader");
        final int fshaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fshaderId, fragmentShaderResource.toStringResource());
        GL20.glCompileShader(fshaderId);
        checkCompileErrors(fshaderId);
        
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vshaderId);
        GL20.glAttachShader(programId, fshaderId);
        
        GL30.glTransformFeedbackVaryings(programId, new String[] { "vOut" , "angleOut", "radiusOut", "seedOut" }, GL30.GL_INTERLEAVED_ATTRIBS);
        
        GL20.glLinkProgram(programId);
        checkLinkErrors(programId);
        GL20.glUseProgram(programId);
 
        secondVao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(secondVao);
        
        
        secondBuffer = GL15.glGenBuffers();
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, secondBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, argumentCount * floatsPerArgument * floatSizeInBytes, GL15.GL_STREAM_DRAW);
        
        final int inputvert = GL20.glGetAttribLocation(programId, "vIn");
        final int angleVert = GL20.glGetAttribLocation(programId, "angleData");
        final int radiusVert = GL20.glGetAttribLocation(programId, "radiusIn");
        final int seedVert = GL20.glGetAttribLocation(programId, "seed");
        
        GL20.glEnableVertexAttribArray(inputvert);
        GL20.glVertexAttribPointer(inputvert, 3, GL11.GL_FLOAT, false, floatsPerArgument * floatSizeInBytes, 0 * floatSizeInBytes);
        
        GL20.glEnableVertexAttribArray(angleVert);
        GL20.glVertexAttribPointer(angleVert, 2, GL11.GL_FLOAT, false, floatsPerArgument * floatSizeInBytes, 3 * floatSizeInBytes);
        
        GL20.glEnableVertexAttribArray(radiusVert);
        GL20.glVertexAttribPointer(radiusVert, 3, GL11.GL_FLOAT, false, floatsPerArgument * floatSizeInBytes, 5 * floatSizeInBytes);
        
        
        GL20.glEnableVertexAttribArray(seedVert);
        GL20.glVertexAttribPointer(seedVert, 1, GL11.GL_FLOAT, false, floatsPerArgument * floatSizeInBytes, 8 * floatSizeInBytes);
        
        GL30.glBindBufferRange(GL_TRANSFORM_FEEDBACK_BUFFER, 0, secondBuffer, 0, argumentCount * floatsPerArgument * floatSizeInBytes);

        
        firstVao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(firstVao);
 
        final FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(argumentCount * floatsPerArgument);
        
        System.out.println("-- filling buffer");
        int percent = 0;
        for(int i = 0; i < argumentCount; i++){
        	float value = (float) (((i) / (double) argumentCount) * 0.5);
        	dataBuffer.put(value); //position x
        	dataBuffer.put(0); //position y
        	dataBuffer.put(0); //position z
        	dataBuffer.put((float) Math.toRadians(Math.random() * 360));
        	dataBuffer.put((float) Math.toRadians(Math.random() * 360));
        	float rnd0 = (float) Math.random() * 10;
        	float rnd1 = (float) Math.random() * 10;
        	float rnd2 = (float) Math.random() * 10;
        	dataBuffer.put(-(rnd0 / (rnd0 + 1)) + 1f);
        	dataBuffer.put(-(rnd1 / (rnd1 + 1)) + 1);
        	dataBuffer.put(-(rnd2 / (rnd2 + 1)) + 1);
        	dataBuffer.put((float) (int) (Math.random() * 2));
        	if(i % (argumentCount / 100) == 0){
        		System.out.println(percent++);
        	}
        }
        dataBuffer.flip();
        System.out.println("-- finished initialization\n");
        
        
        firstBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, firstBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, dataBuffer, GL15.GL_STREAM_DRAW);
        
        GL20.glEnableVertexAttribArray(inputvert);
        GL20.glVertexAttribPointer(inputvert, 3, GL11.GL_FLOAT, false, floatsPerArgument * floatSizeInBytes, 0 * floatSizeInBytes);
        
        GL20.glEnableVertexAttribArray(angleVert);
        GL20.glVertexAttribPointer(angleVert, 2, GL11.GL_FLOAT, false, floatsPerArgument * floatSizeInBytes, 3 * floatSizeInBytes);
        
        GL20.glEnableVertexAttribArray(radiusVert);
        GL20.glVertexAttribPointer(radiusVert, 3, GL11.GL_FLOAT, false, floatsPerArgument * floatSizeInBytes, 5 * floatSizeInBytes);
        
        GL20.glEnableVertexAttribArray(seedVert);
        GL20.glVertexAttribPointer(seedVert, 1, GL11.GL_FLOAT, false, floatsPerArgument * floatSizeInBytes, 8 * floatSizeInBytes);
        
        GL11.glPointSize(1);
        
        testLoop();
    } 
       
    private void testLoop(){
    	long thisFrame, lastFrame = System.nanoTime();
    	float delta, time = 0;
    	Mouse.setGrabbed(true);
    	
    	GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glDepthFunc(GL11.GL_LESS);
    	
    	int globalDataUBO = GL31.glGetUniformBlockIndex(programId, "GlobalValues");
    	int uniformBufferId = GL15.glGenBuffers();
    	GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, uniformBufferId);
    	GL15.glBufferData(GL31.GL_UNIFORM_BUFFER, 2 * floatSizeInBytes, GL15.GL_STREAM_DRAW);
    	FloatBuffer globalData = BufferUtils.createFloatBuffer(2);
    	GL31.glUniformBlockBinding(programId, globalDataUBO, 0);
    	GL30.glBindBufferRange(GL31.GL_UNIFORM_BUFFER, 0, uniformBufferId, 0, 2 * floatSizeInBytes);
    	
    	float aspect = (float) Display.getWidth() / Display.getHeight(), top = 1.0f, bottom = -1.0f, left = -aspect, right = aspect, far = 10.0f, near = 0.1f;
    	Matrix4f m = new Matrix4f();
		m.m00 = 2 / (right - left);
        m.m03 = -(right + left) / (right - left);
        m.m11 = 2 / (top - bottom);  
        m.m13 = -(top + bottom) / (top - bottom);
        m.m22 = -2 / (far - near);
    	FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
    	m.store(projectionMatrixBuffer);
    	projectionMatrixBuffer.flip();
    	GL20.glUniformMatrix4(GL20.glGetUniformLocation(programId, "projectionMatrix"), false, projectionMatrixBuffer);
    	
    	GL11.glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
    	
    	float counter = 0;
    	ArrayList<Float> fpsValues = new ArrayList<>();
    	while(!Display.isCloseRequested()){
	        thisFrame = System.nanoTime();
	        delta = (thisFrame - lastFrame) / (float) 1e9;
	        lastFrame = thisFrame;
	        time += delta;
	        counter += delta;
    		
	        globalData.put(delta);
	        globalData.put(time);
	        globalData.flip();
	        //GL20.glUniform1f(deltaUniformLocation, delta);
	        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, uniformBufferId);
	        GL15.glBufferSubData(GL31.GL_UNIFORM_BUFFER, 0, globalData);
	        
    		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	        //in buffer2 soll output geschrieben werden
	        GL30.glBindBufferRange(GL_TRANSFORM_FEEDBACK_BUFFER, 0, secondBuffer, 0, argumentCount * floatsPerArgument * floatSizeInBytes);
	        GL30.glBeginTransformFeedback(GL11.GL_POINTS);
	        //von buffer1 (vao1) soll gelesen also gerendert werden
	        GL30.glBindVertexArray(firstVao);
	        GL11.glDrawArrays(GL11.GL_POINTS, 0, argumentCount);
	        GL30.glEndTransformFeedback();
	        Display.update();
	        
	        //switch alles (vao und buffer id's)
	        int temp = secondVao;
	        secondVao = firstVao;
	        firstVao = temp;
	        temp = secondBuffer;
	        secondBuffer = firstBuffer;
	        firstBuffer = temp;
	        
	        if(counter > 0.5){
	        	float fps = (1.0f / delta);
//	        	Display.setTitle("fps: " + fps);
	        	fpsValues.add(fps);
	        	counter = 0;
	        }
        }
    	Display.destroy();
    	float all = 0;
    	for(Float f : fpsValues){
    		all += f;
    	}
    	JOptionPane.showMessageDialog(null, "average fps: " + (all / (float) fpsValues.size()));
    }
       
    private void checkCompileErrors(int shaderId){
    	if (GL_FALSE == glGetShaderi(shaderId, GL_COMPILE_STATUS)){
            final int length = glGetShaderi(shaderId, GL_INFO_LOG_LENGTH);
            final String log = glGetShaderInfoLog(shaderId, length);
            throw new RuntimeException(log);
        }
    }
    
    private void checkLinkErrors(int programId){
    	if (GL_FALSE == glGetProgrami(programId, GL_LINK_STATUS)) {
            final int length = glGetProgrami(programId, GL_INFO_LOG_LENGTH);
            final String log = glGetProgramInfoLog(programId, length);
            throw new RuntimeException(log);
        }
    }
 
    public static void main(final String[] args) throws LWJGLException {
        try{
        	new TransformFeedbackPorting();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
}