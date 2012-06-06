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
package mwisbest.openbase.gui;

public enum WidgetType
{
	/**
	 * Buttons are widgets that can do things when clicked.
	 */
	BUTTON,
	/**
	 * CheckBoxes are 
	 */
	CHECKBOX,
	/**
	 * Default. Nothing special here.
	 */
	DEFAULT,
	/**
	 * Labels are strips of text.
	 */
	LABEL,
	/**
	 * TextBoxes are places to type.
	 */
	TEXTBOX,
	/**
	 * Textures are plain images.
	 */
	TEXTURE,
	/**
	 * Unknown.
	 */
	UNKNOWN;
}