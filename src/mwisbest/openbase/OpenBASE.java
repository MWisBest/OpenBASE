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

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import mwisbest.mwtils.thread.SleepThreadHackery;
import mwisbest.openbase.math.FloatMatrix;
import mwisbest.openbase.opengl.UtilsGL;
import mwisbest.openbase.util.LoggerHelper;

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
	private int windowWidth = 640, windowHeight = 360, framerateLimit = -1, framesPerSecond = 0;
	private String windowTitle = "OpenBASE", windowIcon16Loc = "GLicon16.png", windowIcon32Loc = "GLicon32.png";
	private boolean windowVSync = false, running = false;
	private File dataFolder = new File( ".", ".openbase" );
	private long oldTime = 0, currentTime = 0;
	
	public static boolean logFPS = false;
	
	public OpenBASE()
	{
		this( 640, 360, "OpenBASE", "GLicon16.png", "GLicon32.png", false, -1 );
	}
	
	public OpenBASE( int width, int height )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", false, -1 );
	}
	
	public OpenBASE( int width, int height, int framerateLimit )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", false, framerateLimit );
	}
	
	public OpenBASE( int width, int height, boolean vSync )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", vSync, -1 );
	}
	
	public OpenBASE( int width, int height, String title )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", false, -1 );
	}
	
	public OpenBASE( int width, int height, String title, int framerateLimit )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", false, framerateLimit );
	}
	
	public OpenBASE( int width, int height, String title, boolean vSync )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", vSync, -1 );
	}
	
	public OpenBASE( int width, int height, String icon16Loc, String icon32Loc )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, false, -1 );
	}
	
	public OpenBASE( int width, int height, String icon16Loc, String icon32Loc, int framerateLimit )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, false, framerateLimit );
	}
	
	public OpenBASE( int width, int height, String icon16Loc, String icon32Loc, boolean vSync )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, vSync, -1 );
	}
	
	public OpenBASE( int width, int height, String title, String icon16Loc, String icon32Loc )
	{
		this( width, height, title, icon16Loc, icon32Loc, false, -1 );
	}
	
	public OpenBASE( int width, int height, String title, String icon16Loc, String icon32Loc, int framerateLimit )
	{
		this( width, height, title, icon16Loc, icon32Loc, false, framerateLimit );
	}
	
	public OpenBASE( int width, int height, String title, String icon16Loc, String icon32Loc, boolean vSync )
	{
		this( width, height, title, icon16Loc, icon32Loc, vSync, -1 );
	}
	
	private OpenBASE( int width, int height, String title, String icon16Loc, String icon32Loc, boolean vSync, int framerateLimit )
	{
		this.windowWidth = width;
		this.windowHeight = height;
		this.windowTitle = title;
		this.windowIcon16Loc = icon16Loc;
		this.windowIcon32Loc = icon32Loc;
		this.windowVSync = vSync;
		this.framerateLimit = framerateLimit;
		this.start();
	}
	
	private void start()
	{
		new SleepThreadHackery( "[OpenBASE] Sleep Thread Hackery" );
		this.theThread = new Thread( "[OpenBASE] Main" )
			{
				@Override
				public void run()
				{
					OpenBASE.this.running = true;
					OpenBASE.this.init();
				}
			};
		this.theThread.start();
	}
	
	private void init()
	{
		Common.extractNatives( new File( this.dataFolder, "natives" ), false );
		try
		{
			System.setProperty( "org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true" );
			Display.setDisplayMode( new DisplayMode( this.windowWidth, this.windowHeight ) );
			Display.setTitle( this.windowTitle );
			Display.setIcon( new ByteBuffer[] { UtilsGL.loadIcon( this.windowIcon16Loc ), UtilsGL.loadIcon( this.windowIcon32Loc ) } );
			Display.setFullscreen( false );
			Display.create();
			Display.setVSyncEnabled( this.windowVSync );
			Keyboard.enableRepeatEvents( true );
			AL.create();
			try
			{
				CL.create();
			}
			catch( LWJGLException e )
			{
				LoggerHelper.getLogger().info( "OpenCL not supported: Disabling OpenCL." );
			}
			GL11.glEnable( GL11.GL_TEXTURE_2D );
			GL11.glDisable( GL11.GL_DEPTH_TEST );
			GL11.glMatrixMode( GL11.GL_PROJECTION );
			GL11.glLoadMatrix( FloatMatrix.glOrtho( 0, this.windowWidth, this.windowHeight, 0, 1, -1 ).asFloatBuffer() );
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
		
		this.loadResources();
		
		if( OpenBASE.logFPS )
		{
			long startTime = System.currentTimeMillis();
			this.oldTime = startTime;
			this.currentTime = startTime;
		}
		
		this.mainLoop();
	}
	
	private void mainLoop()
	{
		while( this.running )
		{
			this.render();
			this.audio();
			this.input();
			
			if( !OpenBASE.logFPS ) continue;
			
			this.currentTime = System.currentTimeMillis();
			if( this.oldTime + 1000 < this.currentTime )
			{
				System.out.println( this.framesPerSecond );
				this.oldTime = this.currentTime;
				this.framesPerSecond = 0;
			}
			else ++this.framesPerSecond;
		}
		Display.destroy();
		try
		{
			CL.destroy();
		}
		catch( Exception e )
		{
			// CL Not supported
		}
		AL.destroy();
	}
	
	private void render()
	{
		Common.render( this.framerateLimit );
		this.customRender();
		Common.currentTextureID = GL11.glGetInteger( GL11.GL_TEXTURE_BINDING_2D );
		Display.update();
		if( Display.isCloseRequested() ) this.running = false;
	}
	
	private void audio()
	{
		Common.audio();
		this.customAudio();
	}
	
	private void input()
	{
		Common.input();
		this.customInput();
	}
	
	public abstract void loadResources();
	
	public abstract void customRender();
	
	public abstract void customAudio();
	
	public abstract void customInput();
}