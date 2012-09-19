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
package mwisbest.openbase.opengl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.EmptyImageData;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.ImageDataFactory;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.util.ResourceLoader;

/**
 * A texture loaded based on many old versions that will load image data from a file and produce OpenGL textures.
 * 
 * @see ImageData
 * 
 * @author kevin
 */
public class TextureLoader
{
	/**
	 * Load a texture with a given format from the supplied input stream
	 * 
	 * @param format The format of the texture to be loaded (something like "PNG" or "TGA")
	 * @param in The input stream from which the image data will be read
	 * @return The newly created texture
	 * @throws IOException Indicates a failure to read the image data
	 */
	public static Texture getTexture( String format, InputStream in ) throws IOException
	{
		return getTexture( format, in, false, GL11.GL_LINEAR );
	}
	
	/**
	 * Load a texture with a given format from the supplied input stream
	 * 
	 * @param format The format of the texture to be loaded (something like "PNG" or "TGA")
	 * @param in The input stream from which the image data will be read
	 * @param flipped True if the image should be flipped vertically on loading
	 * @return The newly created texture
	 * @throws IOException Indicates a failure to read the image data
	 */
	public static Texture getTexture( String format, InputStream in, boolean flipped ) throws IOException
	{
		return getTexture( format, in, flipped, GL11.GL_LINEAR );
	}
	
	/**
	 * Load a texture with a given format from the supplied input stream
	 * 
	 * @param format The format of the texture to be loaded (something like "PNG" or "TGA")
	 * @param in The input stream from which the image data will be read
	 * @param filter The GL texture filter to use for scaling up and down
	 * @return The newly created texture
	 * @throws IOException Indicates a failure to read the image data
	 */
	public static Texture getTexture( String format, InputStream in, int filter ) throws IOException
	{
		return getTexture( format, in, false, filter );
	}
	
	/**
	 * Load a texture with a given format from the supplied input stream
	 * 
	 * @param format The format of the texture to be loaded (something like "PNG" or "TGA")
	 * @param in The input stream from which the image data will be read
	 * @param flipped True if the image should be flipped vertically on loading
	 * @param filter The GL texture filter to use for scaling up and down
	 * @return The newly created texture
	 * @throws IOException Indicates a failure to read the image data
	 */
	public static Texture getTexture( String format, InputStream in, boolean flipped, int filter ) throws IOException
	{
		return TextureLoader.get().getTexture( in, in.toString() + "." + format, flipped, filter );
	}
	
	/** Useful for debugging; keeps track of the current number of active textures. */
	static int textureCount = 0;
	
	private static boolean forcePOT = false;
	
	public static boolean isPowerOfTwo( int n )
	{
		return ( n & -n ) == n;
	}
	
	/**
	 * Returns true if we are forcing loaded image data into power-of-two OpenGL textures (by default, this is true). If non-power-of-two textures is not supported in hardware (i.e. isNPOTSupported returns false), then the image data will be forced into POT textures regardless of isForcePOTSize().
	 * 
	 * @return true if we should ensure POT sized textures, flase if we should attempt to use NPOT if supported
	 */
	public static boolean isForcePOT()
	{
		return forcePOT;
	}
	
	/**
	 * Set whether we are forcing loaded image data into power-of-two OpenGL textures (by default, this is true). If non-power-of-two textures is not supported in hardware (i.e. isNPOTSupported returns false), then the image data will be forced into POT textures regardless of isForcePOTSize().
	 * 
	 * @param b true if we should ensure POT sized textures, flase if we should attempt to use NPOT if supported
	 */
	public static void setForcePOT( boolean b )
	{
		forcePOT = b;
	}
	
	/**
	 * Returns the current number of active textures. Calling InternalTextureLoader.createTextureID increases this number. Calling TextureImpl.release or InternalTextureLoader.deleteTextureID decreases this number.
	 * 
	 * @return the number of active OpenGL textures
	 */
	public static int getTextureCount()
	{
		return textureCount;
	}
	
	/**
	 * Create a new texture ID; will increase the value for getTextureCount.
	 * 
	 * @return A new texture ID
	 */
	public static int createTextureID()
	{
		IntBuffer tmp = createIntBuffer( 1 );
		GL11.glGenTextures( tmp );
		textureCount++;
		return tmp.get( 0 );
	}
	
	/**
	 * Used internally; call TextureImpl.release.
	 * 
	 * @param id the id of the OpenGL texture
	 */
	public static void deleteTextureID( int id )
	{
		IntBuffer texBuf = createIntBuffer( 1 );
		texBuf.put( id );
		texBuf.flip();
		GL11.glDeleteTextures( texBuf );
		textureCount--;
	}
	
	/**
	 * Returns true if non-power-of-two textures are supported in hardware via the GL_ARB_texture_non_power_of_two extension.
	 * 
	 * @return true if the extension is listed
	 */
	public static boolean isNPOTSupported()
	{
		// don't check GL20, nvidia/ATI usually don't advertise this extension
		// if it means requiring software fallback
		return GLContext.getCapabilities().GL_ARB_texture_non_power_of_two;
	}
	
	/** The standard texture loaded used everywhere */
	private static final TextureLoader loader = new TextureLoader();
	
	/**
	 * Get the single instance of this texture loader
	 * 
	 * @return The single instance of the texture loader
	 */
	public static TextureLoader get()
	{
		return loader;
	}
	
	/** The table of textures that have been loaded in this loader */
	private HashMap<String, Object> texturesLinear = new HashMap<>();
	/** The table of textures that have been loaded in this loader */
	private HashMap<String, Object> texturesNearest = new HashMap<>();
	/** The destination pixel format */
	private int dstPixelFormat = GL11.GL_RGBA;
	
	/**
	 * Create a new texture loader based on the game panel
	 */
	private TextureLoader()
	{
	}
	
	/**
	 * Remove a particular named image from the cache (does not release the OpenGL texture)
	 * 
	 * @param name The name of the image to be cleared
	 */
	public void clear( String name )
	{
		this.texturesLinear.remove( name );
		this.texturesNearest.remove( name );
	}
	
	/**
	 * Clear out the cached textures (does not release the OpenGL textures)
	 */
	public void clear()
	{
		this.texturesLinear.clear();
		this.texturesNearest.clear();
	}
	
	/**
	 * Tell the loader to produce 16 bit textures
	 */
	public void set16BitMode()
	{
		this.dstPixelFormat = GL11.GL_RGBA16;
	}
	
	/**
	 * Get a texture from a specific file
	 * 
	 * @param source The file to load the texture from
	 * @param flipped True if we should flip the texture on the y axis while loading
	 * @param filter The filter to use
	 * @return The texture loaded
	 * @throws IOException Indicates a failure to load the image
	 */
	public Texture getTexture( File source, boolean flipped, int filter ) throws IOException
	{
		String resourceName = source.getAbsolutePath();
		InputStream in = new FileInputStream( source );
		
		return this.getTexture( in, resourceName, flipped, filter, null );
	}
	
	/**
	 * Get a texture from a specific file
	 * 
	 * @param source The file to load the texture from
	 * @param flipped True if we should flip the texture on the y axis while loading
	 * @param filter The filter to use
	 * @param transparent The colour to interpret as transparent or null if none
	 * @return The texture loaded
	 * @throws IOException Indicates a failure to load the image
	 */
	public Texture getTexture( File source, boolean flipped, int filter, int[] transparent ) throws IOException
	{
		String resourceName = source.getAbsolutePath();
		InputStream in = new FileInputStream( source );
		
		return this.getTexture( in, resourceName, flipped, filter, transparent );
	}
	
	/**
	 * Get a texture from a resource location
	 * 
	 * @param resourceName The location to load the texture from
	 * @param flipped True if we should flip the texture on the y axis while loading
	 * @param filter The filter to use when scaling the texture
	 * @return The texture loaded
	 * @throws IOException Indicates a failure to load the image
	 */
	public Texture getTexture( String resourceName, boolean flipped, int filter ) throws IOException
	{
		InputStream in = ResourceLoader.getResourceAsStream( resourceName );
		
		return this.getTexture( in, resourceName, flipped, filter, null );
	}
	
	/**
	 * Get a texture from a resource location
	 * 
	 * @param resourceName The location to load the texture from
	 * @param flipped True if we should flip the texture on the y axis while loading
	 * @param filter The filter to use when scaling the texture
	 * @param transparent The colour to interpret as transparent or null if none
	 * @return The texture loaded
	 * @throws IOException Indicates a failure to load the image
	 */
	public Texture getTexture( String resourceName, boolean flipped, int filter, int[] transparent ) throws IOException
	{
		InputStream in = ResourceLoader.getResourceAsStream( resourceName );
		
		return this.getTexture( in, resourceName, flipped, filter, transparent );
	}
	
	/**
	 * Get a texture from a image file
	 * 
	 * @param in The stream from which we can load the image
	 * @param resourceName The name to give this image in the internal cache
	 * @param flipped True if we should flip the image on the y-axis while loading
	 * @param filter The filter to use when scaling the texture
	 * @return The texture loaded
	 * @throws IOException Indicates a failure to load the image
	 */
	public Texture getTexture( InputStream in, String resourceName, boolean flipped, int filter ) throws IOException
	{
		return this.getTexture( in, resourceName, flipped, filter, null );
	}
	
	/**
	 * Get a texture from a image file
	 * 
	 * @param in The stream from which we can load the image
	 * @param resourceName The name to give this image in the internal cache
	 * @param flipped True if we should flip the image on the y-axis while loading
	 * @param filter The filter to use when scaling the texture
	 * @param transparent The colour to interpret as transparent or null if none
	 * @return The texture loaded
	 * @throws IOException Indicates a failure to load the image
	 */
	public TextureImplementation getTexture( InputStream in, String resourceName, boolean flipped, int filter, int[] transparent ) throws IOException
	{
		HashMap<String, Object> hash = this.texturesLinear;
		if( filter == GL11.GL_NEAREST ) hash = this.texturesNearest;
		
		String resName = resourceName;
		if( transparent != null ) resName += ":" + transparent[0] + ":" + transparent[1] + ":" + transparent[2];
		resName += ":" + flipped;
		
		@SuppressWarnings( "unchecked" )
		SoftReference<TextureImplementation> ref = (SoftReference<TextureImplementation>)hash.get( resName );
		if( ref != null )
		{
			TextureImplementation tex = ref.get();
			if( tex != null ) return tex;
			hash.remove( resName );
		}
		
		// horrible test until I can find something more suitable
		try
		{
			GL11.glGetError();
		}
		catch( NullPointerException e )
		{
			throw new RuntimeException( "Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation." );
		}
		
		TextureImplementation tex = this.getTexture( in, resourceName, GL11.GL_TEXTURE_2D, filter, filter, flipped, transparent );
		
		hash.put( resName, new SoftReference<>( tex ) );
		
		return tex;
	}
	
	private TextureImplementation getTexture( InputStream in, String resourceName, int target, int minFilter, int magFilter, boolean flipped, int[] transparent ) throws IOException
	{
		// create the texture ID for this texture
		ByteBuffer textureBuffer;
		
		LoadableImageData imageData = ImageDataFactory.getImageDataFor( resourceName );
		textureBuffer = imageData.loadImage( new BufferedInputStream( in ), flipped, transparent );
		
		int textureID = createTextureID();
		TextureImplementation texture = new TextureImplementation( resourceName, target, textureID );
		// bind this texture
		GL11.glEnable( target );
		GL11.glBindTexture( target, textureID );
		
		int width;
		int height;
		int texWidth;
		int texHeight;
		
		ImageData.Format format;
		
		width = imageData.getWidth();
		height = imageData.getHeight();
		format = imageData.getFormat();
		
		texture.setTextureWidth( imageData.getTexWidth() );
		texture.setTextureHeight( imageData.getTexHeight() );
		
		texWidth = texture.getTextureWidth();
		texHeight = texture.getTextureHeight();
		
		IntBuffer temp = BufferUtils.createIntBuffer( 16 );
		GL11.glGetInteger( GL11.GL_MAX_TEXTURE_SIZE, temp );
		int max = temp.get( 0 );
		if( texWidth > max || texHeight > max ) throw new IOException( "Attempt to allocate a texture to big for the current hardware" );
		
		int srcPixelFormat = format.getOGLType();
		
		texture.setWidth( width );
		texture.setHeight( height );
		texture.setImageFormat( format );
		
		GL11.glTexParameteri( target, GL11.GL_TEXTURE_MIN_FILTER, minFilter );
		GL11.glTexParameteri( target, GL11.GL_TEXTURE_MAG_FILTER, magFilter );
		
		ContextCapabilities capabilities = GLContext.getCapabilities();
		
		if( capabilities.OpenGL30 || capabilities.GL_EXT_framebuffer_object || capabilities.GL_ARB_framebuffer_object || capabilities.OpenGL14 )
		{
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR );
			GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, GL11.GL_POLYGON_BIT );
			if( capabilities.OpenGL30 ) GL30.glGenerateMipmap( GL11.GL_TEXTURE_2D );
			else if( capabilities.GL_EXT_framebuffer_object ) EXTFramebufferObject.glGenerateMipmapEXT( GL11.GL_TEXTURE_2D );
			else if( capabilities.GL_ARB_framebuffer_object ) ARBFramebufferObject.glGenerateMipmap( GL11.GL_TEXTURE_2D );
			else if( capabilities.OpenGL14 ) GL11.glTexParameteri( GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE );
		}
		
		// produce a texture from the byte buffer
		GL11.glTexImage2D( target, 0, this.dstPixelFormat, get2Fold( width ), get2Fold( height ), 0, srcPixelFormat, GL11.GL_UNSIGNED_BYTE, textureBuffer );
		return texture;
	}
	
	/**
	 * Create an empty texture
	 * 
	 * @param width The width of the new texture
	 * @param height The height of the new texture
	 * @return The created empty texture
	 * @throws IOException Indicates a failure to create the texture on the graphics hardware
	 */
	public Texture createTexture( final int width, final int height ) throws IOException
	{
		return this.createTexture( width, height, GL11.GL_NEAREST );
	}
	
	/**
	 * Create an empty texture
	 * 
	 * @param width The width of the new texture
	 * @param height The height of the new texture
	 * @return The created empty texture
	 * @throws IOException Indicates a failure to create the texture on the graphics hardware
	 */
	public Texture createTexture( final int width, final int height, final int filter ) throws IOException
	{
		ImageData ds = new EmptyImageData( width, height );
		
		return this.getTexture( ds, filter );
	}
	
	/**
	 * Get a texture from an image file.
	 * 
	 * @param dataSource The image data to generate the texture from
	 * @param filter The filter to use when scaling the texture
	 * @return The texture created
	 * @throws IOException Indicates the texture is too big for the hardware
	 */
	public Texture getTexture( ImageData dataSource, int filter ) throws IOException
	{
		int target = GL11.GL_TEXTURE_2D;
		
		ByteBuffer textureBuffer;
		textureBuffer = dataSource.getImageBufferData();
		
		// create the texture ID for this texture
		int textureID = createTextureID();
		TextureImplementation texture = new TextureImplementation( "generated:" + dataSource, target, textureID );
		
		int minFilter = filter;
		int magFilter = filter;
		
		// bind this texture
		GL11.glEnable( target );
		GL11.glBindTexture( target, textureID );
		
		int width;
		int height;
		int texWidth;
		int texHeight;
		
		ImageData.Format format;
		
		width = dataSource.getWidth();
		height = dataSource.getHeight();
		format = dataSource.getFormat();
		
		texture.setTextureWidth( dataSource.getTexWidth() );
		texture.setTextureHeight( dataSource.getTexHeight() );
		
		texWidth = texture.getTextureWidth();
		texHeight = texture.getTextureHeight();
		
		int srcPixelFormat = format.getOGLType();
		
		texture.setWidth( width );
		texture.setHeight( height );
		texture.setImageFormat( format );
		
		IntBuffer temp = BufferUtils.createIntBuffer( 16 );
		GL11.glGetInteger( GL11.GL_MAX_TEXTURE_SIZE, temp );
		int max = temp.get( 0 );
		if( texWidth > max || texHeight > max ) throw new IOException( "Attempt to allocate a texture to big for the current hardware" );
		
		GL11.glTexParameteri( target, GL11.GL_TEXTURE_MIN_FILTER, minFilter );
		GL11.glTexParameteri( target, GL11.GL_TEXTURE_MAG_FILTER, magFilter );
		
		// produce a texture from the byte buffer
		GL11.glTexImage2D( target, 0, this.dstPixelFormat, get2Fold( width ), get2Fold( height ), 0, srcPixelFormat, GL11.GL_UNSIGNED_BYTE, textureBuffer );
		return texture;
	}
	
	/**
	 * Get the closest greater power of 2 to the fold number
	 * 
	 * @param fold The target number
	 * @return The power of 2
	 */
	public static int get2Fold( int fold )
	{
		// new algorithm? -> return 1 << (32 - Integer.numberOfLeadingZeros(n-1));
		int ret = 2;
		while( ret < fold )
			ret *= 2;
		return ret;
	}
	
	/**
	 * Creates an integer buffer to hold specified ints - strictly a utility method
	 * 
	 * @param size how many int to contain
	 * @return created IntBuffer
	 */
	public static IntBuffer createIntBuffer( int size )
	{
		ByteBuffer temp = ByteBuffer.allocateDirect( 4 * size );
		temp.order( ByteOrder.nativeOrder() );
		
		return temp.asIntBuffer();
	}
	
	/**
	 * Reload a given texture blob; used internally with setHoldTextureData. Call TextureImpl.reload instead.
	 * 
	 * @param texture The texture being reloaded
	 * @param srcPixelFormat The source pixel format
	 * @param componentCount The component count
	 * @param minFilter The minification filter
	 * @param magFilter The magnification filter
	 * @param textureBuffer The pixel data
	 * @return The ID of the newly created texture
	 */
	public int reload( TextureImplementation texture, int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer )
	{
		int target = GL11.GL_TEXTURE_2D;
		int textureID = createTextureID();
		GL11.glEnable( target );
		GL11.glBindTexture( target, textureID );
		
		GL11.glTexParameteri( target, GL11.GL_TEXTURE_MIN_FILTER, minFilter );
		GL11.glTexParameteri( target, GL11.GL_TEXTURE_MAG_FILTER, magFilter );
		
		// produce a texture from the byte buffer
		GL11.glTexImage2D( target, 0, this.dstPixelFormat, texture.getTextureWidth(), texture.getTextureHeight(), 0, srcPixelFormat, GL11.GL_UNSIGNED_BYTE, textureBuffer );
		return textureID;
	}
}