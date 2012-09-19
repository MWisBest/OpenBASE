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
import mwisbest.openbase.event.EventHandler;
import mwisbest.openbase.event.EventManager;
import mwisbest.openbase.event.EventPriority;
import mwisbest.openbase.event.gui.CheckboxClickEvent;
import mwisbest.openbase.opengl.Texture;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Checkbox extends Control
{
	protected boolean state;
	
	public Checkbox( Texture texture )
	{
		this.type = WidgetType.CHECKBOX;
		this.texture = texture;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
		this.state = false;
		EventManager.registerEvents( this, this );
	}
	
	/**
	 * Called when the checkbox is clicked.
	 * 
	 * @param state True if the checkbox is now checked, false if not.
	 */
	public void onStateChanged( boolean state )
	{
	}
	
	@EventHandler( EventPriority.MONITOR )
	private void onClick( CheckboxClickEvent event )
	{
		if( event.getCheckbox() == this )
		{
			this.state = !this.state;
			this.onStateChanged( this.state );
		}
	}
	
	public boolean isInside( int x, int y )
	{
		return x < this.x + this.width && x >= this.x && y < this.y + this.height && y >= this.y;
	}
	
	public boolean isMouseTouching()
	{
		return Mouse.getX() < this.x + this.width && Mouse.getX() >= this.x && Mouse.getY() < this.y + this.height && Mouse.getY() >= this.y;
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