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

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import mwisbest.mwtils.thread.SleepThreadHackery;
import mwisbest.openbase.math.FloatMatrix;
import mwisbest.openbase.util.LoggerHelper;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opencl.CL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public abstract class OpenBASEApplet extends Applet
{
	private static final long serialVersionUID = -3837899243800598866L;
	private int canvasWidth = 640, canvasHeight = 360, framerateLimit = -1;
	@SuppressWarnings( "unused" )
	private String canvasTitle = "OpenBASE";
	@SuppressWarnings( "unused" )
	private String canvasIcon16Loc = "GLicon16.png";
	@SuppressWarnings( "unused" )
	private String canvasIcon32Loc = "GLicon32.png";
	@SuppressWarnings( "unused" )
	private boolean canvasVSync = false, running = false;
	private Canvas displayParent = null;
	private Thread theThread = null;
	
	public OpenBASEApplet()
	{
		this( 640, 360, "OpenBASE", "GLicon16.png", "GLicon32.png", false, -1 );
	}
	
	public OpenBASEApplet( int width, int height )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", false, -1 );
	}
	
	public OpenBASEApplet( int width, int height, int framerateLimit )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", false, framerateLimit );
	}
	
	public OpenBASEApplet( int width, int height, boolean vSync )
	{
		this( width, height, "OpenBASE", "GLicon16.png", "GLicon32.png", vSync, -1 );
	}
	
	public OpenBASEApplet( int width, int height, String title )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", false, -1 );
	}
	
	public OpenBASEApplet( int width, int height, String title, int framerateLimit )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", false, framerateLimit );
	}
	
	public OpenBASEApplet( int width, int height, String title, boolean vSync )
	{
		this( width, height, title, "GLicon16.png", "GLicon32.png", vSync, -1 );
	}
	
	public OpenBASEApplet( int width, int height, String icon16Loc, String icon32Loc )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, false, -1 );
	}
	
	public OpenBASEApplet( int width, int height, String icon16Loc, String icon32Loc, int framerateLimit )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, false, framerateLimit );
	}
	
	public OpenBASEApplet( int width, int height, String icon16Loc, String icon32Loc, boolean vSync )
	{
		this( width, height, "OpenBASE", icon16Loc, icon32Loc, vSync, -1 );
	}
	
	public OpenBASEApplet( int width, int height, String title, String icon16Loc, String icon32Loc )
	{
		this( width, height, title, icon16Loc, icon32Loc, false, -1 );
	}
	
	public OpenBASEApplet( int width, int height, String title, String icon16Loc, String icon32Loc, int framerateLimit )
	{
		this( width, height, title, icon16Loc, icon32Loc, false, framerateLimit );
	}
	
	public OpenBASEApplet( int width, int height, String title, String icon16Loc, String icon32Loc, boolean vSync )
	{
		this( width, height, title, icon16Loc, icon32Loc, vSync, -1 );
	}
	
	public OpenBASEApplet( int width, int height, String title, String icon16Loc, String icon32Loc, boolean vSync, int framerateLimit )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasTitle = title;
		this.canvasIcon16Loc = icon16Loc;
		this.canvasIcon32Loc = icon32Loc;
		this.canvasVSync = vSync;
		this.framerateLimit = framerateLimit;
	}
	
	private void startLWJGL()
	{
		new SleepThreadHackery( "[OpenBASE] Sleep Thread Hackery" );
		this.theThread = new Thread( "[OpenBASE] Main" )
			{
				@Override
				public void run()
				{
					OpenBASEApplet.this.running = true;
					try
					{
						System.setProperty( "org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true" );
						Display.setParent( OpenBASEApplet.this.displayParent );
						Display.create();
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
						GL11.glLoadMatrix( FloatMatrix.glOrtho( 0, OpenBASEApplet.this.canvasWidth, OpenBASEApplet.this.canvasHeight, 0, 1, -1 ).asFloatBuffer() );
					}
					catch( LWJGLException e )
					{
						e.printStackTrace();
						System.exit( 0 );
					}
					
					OpenBASEApplet.this.loadResources();
					OpenBASEApplet.this.mainLoop();
				}
			};
		this.theThread.start();
	}
	
	private void stopLWJGL()
	{
		this.running = false;
		try
		{
			this.theThread.join();
		}
		catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void start()
	{
	}
	
	@Override
	public void stop()
	{
	}
	
	@Override
	public void destroy()
	{
		this.remove( this.displayParent );
		super.destroy();
	}
	
	@Override
	public void init()
	{
		this.setLayout( new BorderLayout() );
		try
		{
			this.displayParent = new Canvas()
				{
					private static final long serialVersionUID = 2601628115991718459L;
					
					@Override
					public final void addNotify()
					{
						super.addNotify();
						OpenBASEApplet.this.startLWJGL();
					}
					
					@Override
					public final void removeNotify()
					{
						OpenBASEApplet.this.stopLWJGL();
						super.removeNotify();
					}
				};
			this.displayParent.setSize( this.canvasWidth, this.canvasHeight );
			this.add( this.displayParent );
			this.displayParent.setFocusable( true );
			this.displayParent.requestFocus();
			this.displayParent.setIgnoreRepaint( true );
			this.setVisible( true );
		}
		catch( Exception e )
		{
			System.err.println( e );
			throw new RuntimeException( "Unable to create the display!" );
		}
	}
	
	private void mainLoop()
	{
		while( this.running )
		{
			this.render();
			this.audio();
			this.input();
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