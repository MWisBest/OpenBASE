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

import mwisbest.openbase.Common;
import mwisbest.openbase.opengl.Texture;

import org.lwjgl.opengl.GL11;

public class TextureWidget extends Widget
{
	protected Texture texture = null;
	protected int width = 0, height = 0;
	
	public TextureWidget( Texture texture )
	{
		this.type = WidgetType.TEXTURE;
		this.texture = texture;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}
	
	public Texture getTexture()
	{
		return this.texture;
	}
	
	public TextureWidget setTexture( Texture texture )
	{
		this.texture = texture;
		return this;
	}
	
	public int getRenderWidth()
	{
		return this.width;
	}
	
	public TextureWidget setRenderWidth( int width )
	{
		this.width = width;
		return this;
	}
	
	public int getRenderHeight()
	{
		return this.height;
	}
	
	public TextureWidget setRenderHeight( int height )
	{
		this.height = height;
		return this;
	}
	
	@Override
	public void render()
	{
		GL11.glPushMatrix();
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
		GL11.glDepthMask( false );
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		if( Common.currentTextureID != this.texture.getTextureID() )
		{
			int texID = this.texture.getTextureID();
			GL11.glBindTexture( GL11.GL_TEXTURE_2D, texID );
			Common.currentTextureID = texID;
		}
		GL11.glTranslatef( this.x, this.y, 0 );
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
}