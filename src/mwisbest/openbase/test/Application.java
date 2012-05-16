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
package mwisbest.openbase.test;

import org.newdawn.slick.opengl.TextureImpl;

import mwisbest.openbase.OpenBASE;
import mwisbest.openbase.ResourceManager;
import mwisbest.openbase.opengl.UtilsGL;
import mwisbest.openbase.opengl.Widget;

// TODO: Don't include this in releases I would assume?
public class Application extends OpenBASE
{
	public static Application tests;
	
	public Application()
	{
		super( 640, 360, false );
	}
	
	@Override
	public void loadResources()
	{
		ResourceManager.addWidget( "test", new Widget( UtilsGL.loadTexture( "GLicon256.png" ) ) );
		ResourceManager.addFont( "BNE12", UtilsGL.loadFont( "BraveNewEra.ttf", 12.0F, true ) );
		ResourceManager.addFont( "BNE24", UtilsGL.loadFont( "BraveNewEra.ttf", 24.0F, true ) );
	}
	
	@Override
	public void customRender()
	{
		TextureImpl.bindNone();
		ResourceManager.getFont( "BNE24" ).drawString( 400, 300, "Hello!" );
	}
	
	@Override
	public void customAudio()
	{
	}
	
	@Override
	public void customInput()
	{
	}
	
	public static void main( String[] argv )
	{
		tests = new Application();
	}
}