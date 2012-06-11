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
package mwisbest.openbase;

import java.io.IOException;
import java.nio.ByteBuffer;

import mwisbest.openbase.math.FloatMatrix;
import mwisbest.openbase.opengl.UtilsGL;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opencl.CL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public abstract class OpenBASE
{
	private Thread theThread = null;
	private int windowWidth = 640, windowHeight = 360;
	private String windowTitle = "OpenBASE", windowIcon16Loc = "GLicon16.png", windowIcon32Loc = "GLicon32.png";
	private boolean windowVSync = false, running = false;
	
	public OpenBASE()
	{
		this( 640, 360, "OpenBASE", "GLicon16.png", "GLicon32.png", false );
	}
	
	public OpenBASE( int width, int height )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", false );
	}
	
	public OpenBASE( int width, int height, boolean vSync )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", vSync );
	}
	
	public OpenBASE( int width, int height, String title )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", false );
	}
	
	public OpenBASE( int width, int height, String title, boolean vSync )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", vSync );
	}
	
	public OpenBASE( int width, int height, String icon16Loc, String icon32Loc )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, false );
	}
	
	public OpenBASE( int width, int height, String icon16Loc, String icon32Loc, boolean vSync )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, vSync );
	}
	
	public OpenBASE( int width, int height, String title, String icon16Loc, String icon32Loc )
	{
		this( width, height, title, icon16Loc, icon32Loc, false );
	}
	
	public OpenBASE( int width, int height, String title, String icon16Loc, String icon32Loc, boolean vSync )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowTitle = title;
		this.windowIcon16Loc = icon16Loc;
		this.windowIcon32Loc = icon32Loc;
		this.windowVSync = vSync;
		this.start();
	}
	
	private void start()
	{
		theThread = new Thread()
			{
				@Override
				public void run()
				{
					running = true;
					init();
				}
			};
		theThread.start();
	}
	
	private void init()
	{
		try
		{
			Display.setDisplayMode( new DisplayMode( windowWidth, windowHeight ) );
			Display.setTitle( windowTitle );
			Display.setIcon( new ByteBuffer[] { UtilsGL.loadIcon( windowIcon16Loc ), UtilsGL.loadIcon( windowIcon32Loc ) } );
			Display.setFullscreen( false );
			Display.create();
			Display.setVSyncEnabled( windowVSync );
			Keyboard.enableRepeatEvents( true );
			AL.create();
			CL.create();
			GL11.glEnable( GL11.GL_TEXTURE_2D );
			GL11.glDisable( GL11.GL_DEPTH_TEST );
			GL11.glMatrixMode( GL11.GL_PROJECTION );
			GL11.glLoadMatrix( FloatMatrix.glOrtho( 0, windowWidth, windowHeight, 0, 1, -1 ).asFloatBuffer() );
		}
		catch( LWJGLException e )
		{
			e.printStackTrace();
			System.exit( 0 );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		loadResources();
		mainLoop();
	}
	
	private void mainLoop()
	{
		while( running )
		{
			render();
			audio();
			input();
		}
		Display.destroy();
		CL.destroy();
		AL.destroy();
	}
	
	private void render()
	{
		Common.render();
		customRender();
		Display.update();
		if( Display.isCloseRequested() ) running = false;
	}
	
	private void audio()
	{
		Common.audio();
		customAudio();
	}
	
	private void input()
	{
		Common.input();
		customInput();
	}
	
	public abstract void loadResources();
	
	public abstract void customRender();
	
	public abstract void customAudio();
	
	public abstract void customInput();
}