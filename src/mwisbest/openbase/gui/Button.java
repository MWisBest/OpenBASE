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
package mwisbest.openbase.gui;

import mwisbest.openbase.event.EventHandler;
import mwisbest.openbase.event.EventManager;
import mwisbest.openbase.event.EventPriority;
import mwisbest.openbase.event.gui.ButtonClickEvent;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public class Button extends Control
{
	public Button( org.newdawn.slick.opengl.Texture texture )
	{
		this.type = WidgetType.BUTTON;
		this.texture = texture;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
		EventManager.registerEvents( this, this );
	}
	
	public void onButtonClick()
	{
	}
	
	@EventHandler( EventPriority.MONITOR )
	private void onClick( ButtonClickEvent event )
	{
		if( event.getButton() == this ) onButtonClick();
	}
	
	public boolean isInside( int x, int y )
	{
		return ( ( x < ( this.x + width ) && x >= this.x ) && ( y < ( this.y + height ) && y >= this.y ) );
	}
	
	public boolean isMouseTouching()
	{
		return ( ( Mouse.getX() < ( x + width ) && Mouse.getX() >= x ) && ( Mouse.getY() < ( y + height ) && Mouse.getY() >= y ) );
	}
	
	@Override
	public void render()
	{
		GL11.glTranslatef( x, y, 0 );
		GL11.glPushMatrix();
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
		GL11.glDepthMask( false );
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, texture.getTextureID() );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST );
		// Mipmap start
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, GL11.GL_POLYGON_BIT );
		ContextCapabilities capabilities = GLContext.getCapabilities();
		if( capabilities.OpenGL30 ) GL30.glGenerateMipmap( GL11.GL_TEXTURE_2D );
		else if( capabilities.GL_EXT_framebuffer_object ) EXTFramebufferObject.glGenerateMipmapEXT( GL11.GL_TEXTURE_2D );
		else if( capabilities.GL_ARB_framebuffer_object ) ARBFramebufferObject.glGenerateMipmap( GL11.GL_TEXTURE_2D );
		else if( capabilities.OpenGL14 ) GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE );
		// Mipmap end
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
