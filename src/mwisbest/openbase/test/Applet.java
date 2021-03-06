/*
 * This file is part of OpenBASE.
 *
 * Copyright � 2012, Kyle Repinski
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
import mwisbest.openbase.event.EventHandler;
import mwisbest.openbase.event.EventManager;
import mwisbest.openbase.event.EventPriority;
import mwisbest.openbase.event.Listener;
import mwisbest.openbase.event.input.KeyboardEvent;
import mwisbest.openbase.event.input.MouseEvent;
import mwisbest.openbase.gui.Button;
import mwisbest.openbase.gui.Checkbox;
import mwisbest.openbase.gui.Label;
import mwisbest.openbase.gui.TextureWidget;
import mwisbest.openbase.input.KeyboardKey;
import mwisbest.openbase.input.KeyboardKeyState;
import mwisbest.openbase.input.MouseButton;
import mwisbest.openbase.input.MouseButtonState;
import mwisbest.openbase.resource.ResourceLoader;
import mwisbest.openbase.resource.ResourceManager;

public class Applet extends OpenBASEApplet implements Listener
{
	private static final long serialVersionUID = 2189135685442424091L;
	public static Applet applet;
	
	public Applet()
	{
		super( 640, 360, false );
	}
	
	@Override
	public void loadResources()
	{
		OpenBASEApplet.logFPS = true;
		ResourceManager.addWidget( "test", new TextureWidget( ResourceLoader.loadTexture( "GLicon256.png" ) ) );
		ResourceManager.addFont( "BNE12", ResourceLoader.loadFont( "BraveNewEra.ttf", 12 ) );
		ResourceManager.addFont( "BNE24", ResourceLoader.loadFont( "BraveNewEra.ttf", 24 ) );
		ResourceManager.addWidget( "testLabel", new Label( ResourceManager.getFont( "BNE24" ), "Hello!" ).setX( 200 ).setY( 200 ) );
		ResourceManager.addTexture( "testButtonOff", ResourceLoader.loadTexture( "TestButtonOff.png" ) );
		ResourceManager.addTexture( "testButtonOn", ResourceLoader.loadTexture( "TestButtonOn.png" ) );
		ResourceManager.addWidget( "testButton", new Button( ResourceManager.getTexture( "testButtonOff" ) )
			{
				@Override
				public void onButtonClick()
				{
					if( this.getTexture().equals( ResourceManager.getTexture( "testButtonOff" ) ) ) this.setTexture( ResourceManager.getTexture( "testButtonOn" ) );
					else this.setTexture( ResourceManager.getTexture( "testButtonOff" ) );
				}
			}.setRenderHeight( 16 ).setRenderWidth( 16 ) );
		ResourceManager.addWidget( "testCheckbox", new Checkbox( ResourceManager.getTexture( "testButtonOff" ) )
			{
				@Override
				public void onStateChanged( boolean state )
				{
					if( state ) this.setTexture( ResourceManager.getTexture( "testButtonOn" ) );
					else this.setTexture( ResourceManager.getTexture( "testButtonOff" ) );
				}
			}.setPosition( 384, 128 ) );
		EventManager.registerEvents( this, this );
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
	
	@EventHandler( EventPriority.MONITOR )
	public void handleKeyboardEvent( KeyboardEvent event )
	{
		if( !event.getRepeatEvent() ) if( event.getState() == KeyboardKeyState.PRESSED && event.getKey() != KeyboardKey.KEY_UNKNOWN ) System.out.println( "Key pressed!" );
		else if( event.getState() == KeyboardKeyState.RELEASED && event.getKey() != KeyboardKey.KEY_UNKNOWN ) System.out.println( "Key released!" );
	}
	
	@EventHandler( EventPriority.MONITOR )
	public void handleMouseEvent( MouseEvent event )
	{
		if( event.getState() == MouseButtonState.PRESSED && event.getButton() != MouseButton.BUTTON_UNKNOWN ) System.out.println( "Mouse Button " + event.getButton() + " pressed!" );
		else if( event.getState() == MouseButtonState.RELEASED && event.getButton() != MouseButton.BUTTON_UNKNOWN ) System.out.println( "Mouse Button " + event.getButton() + " released!" );
	}
	
	public static void main( String[] argv ) // TODO: Get this working!
	{
		applet = new Applet();
	}
}