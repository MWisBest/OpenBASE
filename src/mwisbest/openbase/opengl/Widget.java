/*
 * This file is part of OpenBASE.
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

import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.Texture;

public class Widget
{
	protected boolean visible = true;
	protected RenderPriority priority = RenderPriority.NORMAL;
	protected String tooltip = "";
	protected int x = 0, y = 0, width = 0, height = 0;
	protected Texture texture = null;
	protected WidgetType type = WidgetType.WIDGET;
	
	public Widget( Texture texture )
	{
		this.texture = texture;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}
	
	public Widget( Texture texture, WidgetType type )
	{
		this.texture = texture;
		this.type = type;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}
	
	public Widget( Texture texture, int x, int y )
	{
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}
	
	public Widget( Texture texture, WidgetType type, int x, int y )
	{
		this.texture = texture;
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}
	
	public Widget( Texture texture, int x, int y, int width, int height )
	{
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Widget( Texture texture, WidgetType type, int x, int y, int width, int height )
	{
		this.texture = texture;
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public Widget setX( int x )
	{
		this.x = x;
		return this;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public Widget setY( int y )
	{
		this.y = y;
		return this;
	}
	
	public int getRenderWidth()
	{
		return this.width;
	}
	
	public Widget setRenderWidth( int width )
	{
		this.width = width;
		return this;
	}
	
	public int getRenderHeight()
	{
		return this.height;
	}
	
	public Widget setRenderHeight( int height )
	{
		this.height = height;
		return this;
	}
	
	public RenderPriority getRenderPriority()
	{
		return this.priority;
	}
	
	public Widget setRenderPriority( RenderPriority priority )
	{
		this.priority = priority;
		return this;
	}
	
	public Texture getTexture()
	{
		return this.texture;
	}
	
	public Widget setTexture( Texture texture )
	{
		this.texture = texture;
		return this;
	}
	
	public WidgetType getType()
	{
		return this.type;
	}
	
	public Widget setType( WidgetType type )
	{
		this.type = type;
		return this;
	}
	
	public boolean getVisible()
	{
		return this.visible;
	}
	
	public Widget setVisible( boolean visible )
	{
		this.visible = visible;
		return this;
	}
	
	public void render()
	{
		if( this.type == WidgetType.WIDGET || this.type == WidgetType.TEXTURE || this.type == WidgetType.BUTTON )
		{
			GL11.glTranslatef( this.x, this.y, 0 );
			GL11.glPushMatrix();
			GL11.glEnable( GL11.GL_BLEND );
			GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
			GL11.glDepthMask( false );
			GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
			GL11.glBindTexture( GL11.GL_TEXTURE_2D, this.texture.getTextureID() );
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST );
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST );
			// Mipmap start
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR );
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 8 );
			ContextCapabilities capabilities = GLContext.getCapabilities();
			if( capabilities.OpenGL30 ) GL30.glGenerateMipmap( GL11.GL_TEXTURE_2D );
			else if( capabilities.GL_EXT_framebuffer_object ) EXTFramebufferObject.glGenerateMipmapEXT( GL11.GL_TEXTURE_2D );
			else if( capabilities.OpenGL14 ) GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE );
			// Mipmap end
			GL11.glBegin( GL11.GL_QUADS );
			GL11.glTexCoord2f( 0, 0 );
			GL11.glVertex2f( 0, 0 );
			GL11.glTexCoord2f( 0, this.texture.getHeight() );
			GL11.glVertex2f( 0, this.height );
			GL11.glTexCoord2f( this.texture.getWidth(), this.texture.getHeight() );
			GL11.glVertex2f( this.width, this.height );
			GL11.glTexCoord2f( this.texture.getWidth(), 0 );
			GL11.glVertex2f( this.width, 0 );
			GL11.glEnd();
			GL11.glPopMatrix();
		}
		else if( this.type == WidgetType.TEXT )
		{
			
		}
	}
}
