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
package mwisbest.openbase.test;

import mwisbest.openbase.OpenBASEApplet;
import mwisbest.openbase.ResourceManager;
import mwisbest.openbase.opengl.UtilsGL;
import mwisbest.openbase.opengl.Widget;

public class Applet extends OpenBASEApplet
{
	public static Applet applet;
	
	public Applet()
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
	}
	
	@Override
	public void customAudio()
	{
	}

	@Override
	public void customInput()
	{
	}
	
	public static void main( String[] argv ) // TODO: Get this working!
	{
		applet = new Applet();
	}
}