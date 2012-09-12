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
package mwisbest.openbase.openal;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class UtilsAL
{
	public static Audio loadSound( String pathToSound )
	{
		return loadSound( pathToSound, "OGG" );
	}
	
	public static Audio loadSound( String pathToSound, String soundType )
	{
		try
		{
			return AudioLoader.getStreamingAudio( soundType, ResourceLoader.getResource( pathToSound ) );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static int playAudio( Audio audio )
	{
		return playAudio( audio, false );
	}
	
	public static int playAudio( Audio audio, boolean loop )
	{
		return audio.playAsMusic( 1.0F, 1.0F, loop );
	}
	
	public static int playEffect( Audio audio )
	{
		return playEffect( audio, false );
	}
	
	public static int playEffect( Audio audio, boolean loop )
	{
		return audio.playAsSoundEffect( 1.0F, 1.0F, loop );
	}
}