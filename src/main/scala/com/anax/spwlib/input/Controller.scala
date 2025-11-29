package com.anax.spwlib.input

import com.anax.spwlib.render.Camera

trait Controller {
	def handleInput(state: InputState, camera: Camera, tick: Long): Unit
}
