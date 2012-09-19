/*
 * This file is part of OpenBASE.
 *
 * Copyright © 2012, Kyle Repinski
 * OpenBASE is licensed under the GNU Lesser General Public License.
 *
 * OpenBASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenBASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package mwisbest.openbase.opengl;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.ImageData;

public class TextureImplementation implements Texture
{
	/** The last texture that was bound to */
	static Texture lastBind;
	
	/**
	 * Retrieve the last texture bound through the texture interface
	 * 
	 * @return The last texture bound
	 */
	public static Texture getLastBind()
	{
		return lastBind;
	}
	
	/** The GL target type */
	private int target;
	/** The GL texture ID */
	private int textureID = 0;
	/** The height of the image */
	private int height;
	/** The width of the image */
	private int width;
	/** The width of the texture */
	private int texWidth;
	/** The height of the texture */
	private int texHeight;
	/** The ratio of the width of the image to the texture */
	private float widthRatio;
	/** The ratio of the height of the image to the texture */
	private float heightRatio;
	/** The format of this image. */
	private ImageData.Format format;
	/** The reference this texture was loaded from */
	private String ref;
	
	/**
	 * Create a new texture
	 * 
	 * @param ref The reference this texture was loaded from
	 * @param target The GL target
	 * @param textureID The GL texture ID
	 */
	public TextureImplementation( String ref, int target, int textureID )
	{
		this.target = target;
		this.ref = ref;
		this.textureID = textureID;
		lastBind = this;
	}
	
	@Override
	public boolean hasAlpha()
	{
		return this.format.hasAlpha();
	}
	
	@Override
	public String getTextureRef()
	{
		return this.ref;
	}
	
	/**
	 * Set the format of the image
	 * 
	 * @param imageFormat the format of the image this texture displays
	 */
	public void setImageFormat( final ImageData.Format imageFormat )
	{
		this.format = imageFormat;
	}
	
	public static void bindNone()
	{
		lastBind = null;
		GL11.glDisable( GL11.GL_TEXTURE_2D );
	}
	
	/**
	 * Clear caching of the last bound texture so that an external texture binder can play with the context before returning control
	 */
	public static void unbind()
	{
		lastBind = null;
	}
	
	@Override
	public void bind()
	{
		if( lastBind != this )
		{
			lastBind = this;
			GL11.glEnable( GL11.GL_TEXTURE_2D );
			GL11.glBindTexture( this.target, this.textureID );
		}
	}
	
	/**
	 * Set the height of the image
	 * 
	 * @param height The height of the image
	 */
	public void setHeight( int height )
	{
		this.height = height;
		this.setHeight();
	}
	
	/**
	 * Set the width of the image
	 * 
	 * @param width The width of the image
	 */
	public void setWidth( int width )
	{
		this.width = width;
		this.setWidth();
	}
	
	public ImageData.Format getImageFormat()
	{
		return this.format;
	}
	
	@Override
	public int getImageHeight()
	{
		return this.height;
	}
	
	@Override
	public int getImageWidth()
	{
		return this.width;
	}
	
	@Override
	public float getHeight()
	{
		return this.heightRatio;
	}
	
	@Override
	public float getWidth()
	{
		return this.widthRatio;
	}
	
	@Override
	public int getTextureHeight()
	{
		return this.texHeight;
	}
	
	@Override
	public int getTextureWidth()
	{
		return this.texWidth;
	}
	
	/**
	 * Set the height of this texture
	 * 
	 * @param texHeight The height of the texture
	 */
	public void setTextureHeight( int texHeight )
	{
		this.texHeight = texHeight;
		this.setHeight();
	}
	
	/**
	 * Set the width of this texture
	 * 
	 * @param texWidth The width of the texture
	 */
	public void setTextureWidth( int texWidth )
	{
		this.texWidth = texWidth;
		this.setWidth();
	}
	
	/**
	 * Set the height of the texture. This will update the ratio also.
	 */
	private void setHeight()
	{
		if( this.texHeight != 0 ) this.heightRatio = (float)this.height / this.texHeight;
	}
	
	/**
	 * Set the width of the texture. This will update the ratio also.
	 */
	private void setWidth()
	{
		if( this.texWidth != 0 ) this.widthRatio = (float)this.width / this.texWidth;
	}
	
	@Override
	public int getTextureID()
	{
		return this.textureID;
	}
	
	@Override
	public byte[] getTextureData()
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer( this.format.getColorComponents() * this.texWidth * this.texHeight );
		this.bind();
		GL11.glGetTexImage( GL11.GL_TEXTURE_2D, 0, this.format.getOGLType(), GL11.GL_UNSIGNED_BYTE, buffer );
		byte[] data = new byte[buffer.limit()];
		buffer.get( data );
		buffer.clear();
		
		return data;
	}
	
	@Override
	public void setTextureFilter( int textureFilter )
	{
		this.bind();
		GL11.glTexParameteri( this.target, GL11.GL_TEXTURE_MIN_FILTER, textureFilter );
		GL11.glTexParameteri( this.target, GL11.GL_TEXTURE_MAG_FILTER, textureFilter );
	}
}