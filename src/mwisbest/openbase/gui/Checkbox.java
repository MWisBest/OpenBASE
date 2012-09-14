package mwisbest.openbase.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import mwisbest.openbase.event.EventHandler;
import mwisbest.openbase.event.EventManager;
import mwisbest.openbase.event.EventPriority;
import mwisbest.openbase.event.gui.CheckboxClickEvent;

public class Checkbox extends Control
{
	protected boolean state;
	
	public Checkbox( org.newdawn.slick.opengl.Texture texture )
	{
		this.type = WidgetType.CHECKBOX;
		this.texture = texture;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
		this.state = false;
		EventManager.registerEvents( this, this );
	}
	
	/**
	 * Called when the checkbox is clicked.
	 * 
	 * @param state True if the checkbox is now checked, false if not.
	 */
	public void onStateChanged( boolean state )
	{
	}
	
	@EventHandler( EventPriority.MONITOR )
	private void onClick( CheckboxClickEvent event )
	{
		if( event.getCheckbox() == this )
		{
			state = !state;
			onStateChanged( state );
		}
	}
	
	public boolean isInside( int x, int y )
	{
		return ( ( x < ( this.x + width ) && x >= this.x ) && ( y < ( this.y + height ) && y >= this.y ) );
	}
	
	public boolean isMouseTouching()
	{
		return ( ( Mouse.getX() < ( x + width ) && Mouse.getX() >= x ) && ( Mouse.getY() < ( y + height ) && Mouse.getY() >= y ) );
	}
	
	@Override
	public void render()
	{
		GL11.glPushMatrix();
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
		GL11.glDepthMask( false );
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, texture.getTextureID() );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST );
		// Mipmap start
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, GL11.GL_POLYGON_BIT );
		ContextCapabilities capabilities = GLContext.getCapabilities();
		if( capabilities.OpenGL30 ) GL30.glGenerateMipmap( GL11.GL_TEXTURE_2D );
		else if( capabilities.GL_EXT_framebuffer_object ) EXTFramebufferObject.glGenerateMipmapEXT( GL11.GL_TEXTURE_2D );
		else if( capabilities.GL_ARB_framebuffer_object ) ARBFramebufferObject.glGenerateMipmap( GL11.GL_TEXTURE_2D );
		else if( capabilities.OpenGL14 ) GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE );
		// Mipmap end
		GL11.glTranslatef( x, y, 0 );
		GL11.glBegin( GL11.GL_QUADS );
		GL11.glTexCoord2f( 0, 0 );
		GL11.glVertex2f( 0, 0 );
		GL11.glTexCoord2f( 0, texture.getHeight() );
		GL11.glVertex2f( 0, height );
		GL11.glTexCoord2f( texture.getWidth(), texture.getHeight() );
		GL11.glVertex2f( width, height );
		GL11.glTexCoord2f( texture.getWidth(), 0 );
		GL11.glVertex2f( width, 0 );
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}