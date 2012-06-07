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


/**
 * Basic Widget run-down:
 * Use the constructor to create the Widget with your Texture or Font. Then,
 * set its values with the methods in here.
 * 
 * Example:
 * "Widget myWidget = new Widget( texture ).setX( 10 ).setY( 20 )" etc.
 */
public abstract class Widget
{
	protected boolean visible = true;
	protected RenderPriority priority = RenderPriority.NORMAL;
	protected String tooltip = "";
	protected int x = 0, y = 0;
	protected WidgetType type = null;
	
	public int getX()
	{
		return x;
	}
	
	public Widget setX( int x )
	{
		this.x = x;
		return this;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Widget setY( int y )
	{
		this.y = y;
		return this;
	}
	
	public Widget setPosition( int x, int y )
	{
		this.x = x;
		this.y = y;
		return this;
	}
	
	public RenderPriority getRenderPriority()
	{
		return priority;
	}
	
	public Widget setRenderPriority( RenderPriority priority )
	{
		this.priority = priority;
		return this;
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	
	public Widget setVisible( boolean visible )
	{
		this.visible = visible;
		return this;
	}
	
	public String getTooltip()
	{
		return tooltip;
	}
	
	public Widget setTooltip( String tooltip )
	{
		this.tooltip = tooltip;
		return this;
	}
	
	public WidgetType getWidgetType()
	{
		return type;
	}
	
	public abstract void render();
}