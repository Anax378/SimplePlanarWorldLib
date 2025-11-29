package com.anax.spwlib.input

import com.anax.spwlib.render.Camera

trait InputSubscriber {
	def handleInput(state: InputState, tick: Long): Unit
}
