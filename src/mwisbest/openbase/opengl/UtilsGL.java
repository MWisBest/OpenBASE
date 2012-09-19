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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import mwisbest.openbase.Common;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.util.ResourceLoader;

public class UtilsGL
{
	public static ByteBuffer loadIcon( String path ) throws IOException
	{
		try( InputStream is = ResourceLoader.getResourceAsStream( path ) )
		{
			PNGDecoder decoder = new PNGDecoder( is );
			ByteBuffer bb = ByteBuffer.allocateDirect( decoder.getWidth() * decoder.getHeight() * 4 );
			decoder.decode( bb, decoder.getWidth() * 4, PNGDecoder.Format.RGBA );
			bb.flip();
			return bb;
		}
	}
	
	public static void takeScreenshot( String filename )
	{
		GL11.glReadBuffer( GL11.GL_FRONT );
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bpp = 4;
		ByteBuffer buffer = BufferUtils.createByteBuffer( width * height * bpp );
		GL11.glReadPixels( 0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
		File file = new File( filename );
		BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
		
		for( int x = 0; x < width; x++ )
		{
			for( int y = 0; y < height; y++ )
			{
				int i = ( x + width * y ) * bpp;
				int r = buffer.get( i ) & 0xFF;
				int g = buffer.get( i + 1 ) & 0xFF;
				int b = buffer.get( i + 2 ) & 0xFF;
				image.setRGB( x, height - ( y + 1 ), 0xFF << 24 | r << 16 | g << 8 | b );
			}
		}
		
		try
		{
			ImageIO.write( image, "PNG", file );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Draws the specified texture to positions x and y on the screen and scaled with the specified width and height
	 * 
	 * @param texture
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawTexture( Texture texture, int x, int y, int width, int height )
	{
		GL11.glPushMatrix();
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
		GL11.glDepthMask( false );
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		if( Common.currentTextureID != texture.getTextureID() )
		{
			int texID = texture.getTextureID();
			GL11.glBindTexture( GL11.GL_TEXTURE_2D, texID );
			Common.currentTextureID = texID;
		}
		GL11.glTranslatef( x, y, 0 );
		GL11.glBegin( GL11.GL_QUADS );
		GL11.glTexCoord2f( 0, 0 );
		GL11.glVertex2f( 0, 0 );
		GL11.glTexCoord2f( 0, texture.getHeight() );
		GL11.glVertex2f( 0, height );
		GL11.glTexCoord2f( texture.getWidth(), texture.getHeight() );
		GL11.glVertex2f( width, height );
		GL11.glTexCoord2f( texture.getWidth(), 0 );
		GL11.glVertex2f( width, 0 );
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}