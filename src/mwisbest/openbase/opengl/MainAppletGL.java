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

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public abstract class MainAppletGL extends Applet
{
	private int canvasWidth = 640;
	private int canvasHeight = 360;
	@SuppressWarnings( "unused" )
	private String canvasTitle = "OpenBASE";
	@SuppressWarnings( "unused" )
	private String canvasIcon16Loc = "/GLicon16.png";
	@SuppressWarnings( "unused" )
	private String canvasIcon32Loc = "/GLicon32.png";
	@SuppressWarnings( "unused" )
	private boolean canvasVSync = false;
	private Canvas displayParent = null;
	private Thread theThread = null;
	private boolean running = false;
	
	public MainAppletGL()
	{
	}
	
	public MainAppletGL( int width, int height )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
	}
	
	public MainAppletGL( int width, int height, boolean vSync )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasVSync = vSync;
	}
	
	public MainAppletGL( int width, int height, String title )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasTitle = title;
	}
	
	public MainAppletGL( int width, int height, String title, boolean vSync  )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasTitle = title;
		this.canvasVSync = vSync;
	}
	
	public MainAppletGL( int width, int height, String icon16loc, String icon32loc )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasIcon16Loc = icon16loc;
		this.canvasIcon32Loc = icon32loc;
	}
	
	public MainAppletGL( int width, int height, String icon16loc, String icon32loc, boolean vSync )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasIcon16Loc = icon16loc;
		this.canvasIcon32Loc = icon32loc;
		this.canvasVSync = vSync;
	}
	
	public MainAppletGL( int width, int height, String title, String icon16loc, String icon32loc )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasTitle = title;
		this.canvasIcon16Loc = icon16loc;
		this.canvasIcon32Loc = icon32loc;
	}
	
	public MainAppletGL( int width, int height, String title, String icon16loc, String icon32loc, boolean vSync )
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.canvasTitle = title;
		this.canvasIcon16Loc = icon16loc;
		this.canvasIcon32Loc = icon32loc;
		this.canvasVSync = vSync;
	}
	
	public void startLWJGL()
	{
		theThread = new Thread()
			{
				public void run()
				{
					running = true;
					try
					{
						Display.setParent( displayParent );
						Display.create();
					}
					catch( LWJGLException e )
					{
						e.printStackTrace();
					}
					mainLoop();
				}
			};
		theThread.start();
	}
	
	public void stopLWJGL()
	{
		running = false;
		try
		{
			theThread.join();
		}
		catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
	
	public void start()
	{
	}
	
	public void stop()
	{
	}
	
	public void destroy()
	{
		remove( displayParent );
		super.destroy();
	}
	
	public void mainLoop()
	{
		while( running )
		{
			Display.sync( 60 );
			Display.update();
		}
		
		Display.destroy();
	}
	
	public void init()
	{
		setLayout( new BorderLayout() );
		try
		{
			displayParent = new Canvas()
				{
					public final void addNotify()
					{
						super.addNotify();
						startLWJGL();
					}
					
					public final void removeNotify()
					{
						stopLWJGL();
						super.removeNotify();
					}
				};
			displayParent.setSize( this.canvasWidth, this.canvasHeight );
			add( displayParent );
			displayParent.setFocusable( true );
			displayParent.requestFocus();
			displayParent.setIgnoreRepaint( true );
			setVisible( true );
		}
		catch( Exception e )
		{
			System.err.println( e );
			throw new RuntimeException( "Unable to create the display!" );
		}
	}
}
