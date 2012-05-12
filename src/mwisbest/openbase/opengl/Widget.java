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

import org.newdawn.slick.opengl.Texture;

//TODO: I absolutely hate this file. It is ridiculous that I can't access an abstract class's constructor without doing this.
public class Widget extends AbstractWidget
{
	public Widget( Texture texture )
	{
		super( texture );
	}
	
	public Widget( Texture texture, WidgetType type )
	{
		super( texture, type );
	}
	
	public Widget( Texture texture, int x, int y )
	{
		super( texture, x, y );
	}
	
	public Widget( Texture texture, WidgetType type, int x, int y )
	{
		super( texture, type, x, y );
	}
	
	public Widget( Texture texture, int x, int y, int width, int height )
	{
		super( texture, x, y, width, height );
	}
	
	public Widget( Texture texture, WidgetType type, int x, int y, int width, int height )
	{
		super( texture, type, x, y, width, height );
	}
}
