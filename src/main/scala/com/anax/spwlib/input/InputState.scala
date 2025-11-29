package com.anax.spwlib.input

import com.anax.spwlib.math.Vec

import java.awt.event.{KeyEvent, MouseEvent}

class InputState {
	
	val keysDown: Array[Boolean] = new Array(KeyEvent.KEY_LAST)
	val unprocessedKeyPresses: Array[Boolean] = new Array(KeyEvent.KEY_LAST)
	val mouseButtonsDown: Array[Boolean] = new Array(MouseEvent.MOUSE_LAST)
	val unprocessedMouseButtonPresses: Array[Boolean] = new Array(MouseEvent.MOUSE_LAST)
	
	@volatile var unprocessedMouseScroll: Int = 0
	@volatile var unprocessedMouseMovementX: Int = 0
	@volatile var unprocessedMouseMovementY: Int = 0
	@volatile var isMouseOnWindow = false
	@volatile var hasFocus = false
	@volatile var lastMouseX = 0
	@volatile var lastMouseY = 0
	
	def consumeKeyPress(key: Int): Boolean = {
		val ret = unprocessedKeyPresses(key)
		unprocessedKeyPresses(key) = false
		ret
	}
	
	def consumeMousePress(button: Int): Boolean = {
		val ret = unprocessedMouseButtonPresses(button)
		unprocessedMouseButtonPresses(button) = false
		ret
	}
	
	def consumeMouseMovementX(): Int = {
		val ret = unprocessedMouseMovementX
		unprocessedMouseMovementX = 0
		ret
	}
	def consumeMouseMovementY(): Int = {
		val ret = unprocessedMouseMovementY
		unprocessedMouseMovementY = 0
		ret
	}
	def consumeMouseMovement(): Vec = Vec(consumeMouseMovementX(), consumeMouseMovementY())
	def lastMousePosition(): Vec = Vec(lastMouseX, lastMouseY)
	
	def consumeMouseScroll(): Int = {
		val ret = unprocessedMouseScroll
		unprocessedMouseScroll = 0
		ret
	}
	
}

object InputState {
}
