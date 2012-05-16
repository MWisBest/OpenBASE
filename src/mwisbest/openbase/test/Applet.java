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

import mwisbest.openbase.OpenBASEApplet;

public class Applet extends OpenBASEApplet
{
	public static Applet testsApplet;
	
	public Applet()
	{
		super( 640, 360, false );
	}
	
	@Override
	public void loadResources()
	{
	}
	
	@Override
	public void customRender()
	{
	}
	
	@Override
	public void customAudio()
	{
	}
}