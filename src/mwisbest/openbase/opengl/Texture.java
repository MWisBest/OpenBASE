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

public interface Texture
{
	/**
	 * Check if the texture has alpha
	 * 
	 * @return True if the texture has alpha
	 */
	public boolean hasAlpha();
	
	/**
	 * Get the reference from which this texture was loaded
	 * 
	 * @return The reference from which this texture was loaded
	 */
	public String getTextureRef();
	
	/**
	 * Bind the GL context to a texture
	 */
	public void bind();
	
	/**
	 * Get the height of the original image (in pixels; e.g. 200px)
	 * 
	 * @return The height of the original image
	 */
	public int getImageHeight();
	
	/**
	 * Get the width of the original image (in pixels; e.g. 200px)
	 * 
	 * @return The width of the original image
	 */
	public int getImageWidth();
	
	/**
	 * Get the height of the physical texture (the normalized value between 0.0 and 1.0, attained by dividing the image height by the texture height).
	 * 
	 * @return The height of physical texture
	 */
	public float getHeight();
	
	/**
	 * Get the width of the physical texture (the normalized value between 0.0 and 1.0, attained by dividing the image width by the texture width).
	 * 
	 * @return The width of physical texture
	 */
	public float getWidth();
	
	/**
	 * Get the power-of-two height of the actual texture (in pixels, e.g. 256)
	 * 
	 * @return The height of the actual texture
	 */
	public int getTextureHeight();
	
	/**
	 * Get the power-of-two width of the actual texture (in pixels, e.g. 256)
	 * 
	 * @return The width of the actual texture
	 */
	public int getTextureWidth();
	
	/**
	 * Get the OpenGL texture ID for this texture
	 * 
	 * @return The OpenGL texture ID
	 */
	public int getTextureID();
	
	/**
	 * Get the pixel data from the card for this texture
	 * 
	 * @return The texture data from the card for this texture
	 */
	public byte[] getTextureData();
	
	/**
	 * Apply a given texture filter to the texture
	 * 
	 * @param textureFilter The texture filter to apply (GL_LINEAR, GL_NEAREST, etc..)
	 */
	public void setTextureFilter( int textureFilter );
}