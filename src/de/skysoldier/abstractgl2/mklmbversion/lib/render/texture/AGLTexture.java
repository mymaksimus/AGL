package de.skysoldier.abstractgl2.mklmbversion.lib.render.texture;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;

public class AGLTexture {
	
	private int id;
	private static int active;
	
	public AGLTexture(AGLResource imageResource){
		init(imageResource, null);
	}
	
	public AGLTexture(AGLResource imageResource, AGLTextureUnit unit){
		init(imageResource, unit);
	}
	
	private void init(AGLResource imageResource, AGLTextureUnit unit){
		if(unit != null) unit.bind();
		BufferedImage source = imageResource.toImageResource();
		id = GL11.glGenTextures();
        int textureWidth = 2;
        int textureHeight = 2;
        while(textureWidth < source.getWidth()) textureWidth *= 2;
        while (textureHeight < source.getHeight()) textureHeight *= 2;
        BufferedImage textureImage;
        textureImage = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = (Graphics2D) textureImage.getGraphics();
        graphics.drawImage(source, 0, 0, textureImage.getWidth(), textureImage.getHeight(), null);
        graphics.dispose();
        byte[] data = (byte[]) textureImage.getRaster().getDataElements(0, 0, textureWidth, textureHeight, null);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(textureImage.getColorModel().getPixelSize() / 8 * textureWidth * textureHeight);
        byteBuffer.put(data);
        byteBuffer.rewind();
        bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 3);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureWidth, textureHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
        unbind();
	}
	
	//only test purposes
	public void setFilterMode(int filter){
		bind();
//		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
		unbind();
	}

	public void generateMipmaps(int count){
        bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, count);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
        unbind();
	}
	
	public void bind(){
		if(id != AGLTexture.active){
			AGLTexture.active = id; 
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		}
	}
	
	public static void unbind(){
		AGLTexture.active = 0;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}