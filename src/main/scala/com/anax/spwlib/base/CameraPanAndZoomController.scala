package com.anax.spwlib.base

import com.anax.spwlib.input.{Controller, InputState, InputSubscriber}
import com.anax.spwlib.math.Vec
import com.anax.spwlib.render.Camera

import java.awt.event.MouseEvent

class CameraPanAndZoomController(val camera: Camera) extends Controller{
	var zoom = 0.0
	var sensitivity: Double = -0.1
	
	def shouldDoPan(state: InputState, cam: Camera, tick: Long): Boolean = true
	def shouldDoZoom(state: InputState, cam: Camera, tick: Long): Boolean = true
	
	override def handleInput(state: InputState, cam: Camera, tick: Long): Unit = {
		if(shouldDoZoom(state, cam, tick) && state.unprocessedMouseScroll != 0){
			zoom += state.consumeMouseScroll() * sensitivity
			val lastPos = camera.toWorldSpace(state.lastMousePosition())
			
			camera.scale = Math.pow(Math.E, zoom)
			val newPos = camera.toWorldSpace(state.lastMousePosition())
			val correction = newPos.vecTo(lastPos)
			
			camera.position += correction
		}
	
		if(shouldDoPan(state, cam, tick)){
			val movement: Vec = state.consumeMouseMovement()
			if (state.mouseButtonsDown(MouseEvent.BUTTON1)) {
				camera.position -= camera.toWorldScale(movement)
			}
		}
	}
}
