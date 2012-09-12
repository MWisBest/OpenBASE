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

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public class Label extends Widget
{
	protected Font font = null;
	protected int width = 0, height = 0;
	protected String text = null;
	protected Color color = null;
	
	public Label( Font font, String text )
	{
		this( font, text, Color.black );
	}
	
	public Label( Font font, String text, Color color )
	{
		this.type = WidgetType.LABEL;
		this.font = font;
		this.text = text;
		this.color = color;
		this.width = font.getWidth( text );
		this.height = font.getHeight( text );
	}
	
	public Font getFont()
	{
		return font;
	}
	
	public Widget setFont( Font font )
	{
		this.font = font;
		this.width = font.getWidth( text );
		this.height = font.getHeight( text );
		return this;
	}
	
	public String getText()
	{
		return text;
	}
	
	public Widget setText( String text )
	{
		this.text = text;
		this.width = font.getWidth( text );
		this.height = font.getHeight( text );
		return this;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public Widget setColor( Color color )
	{
		this.color = color;
		return this;
	}
	
	@Override
	public void render()
	{
		font.drawString( x, y, text, color );
	}
}
