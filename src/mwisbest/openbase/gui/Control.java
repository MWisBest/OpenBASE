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

import mwisbest.openbase.event.Listener;
import mwisbest.openbase.opengl.Texture;

public abstract class Control extends Widget implements Listener
{
	protected Texture texture = null;
	protected int width = 0, height = 0;
	
	public Texture getTexture()
	{
		return this.texture;
	}
	
	public Control setTexture( Texture texture )
	{
		this.texture = texture;
		return this;
	}
	
	public int getRenderWidth()
	{
		return this.width;
	}
	
	public Control setRenderWidth( int width )
	{
		this.width = width;
		return this;
	}
	
	public int getRenderHeight()
	{
		return this.height;
	}
	
	public Control setRenderHeight( int height )
	{
		this.height = height;
		return this;
	}
}