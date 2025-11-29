package com.anax.spwlib.input

import com.anax.spwlib.render.Camera

object EmptyInputSubscriber extends InputSubscriber{
	override def handleInput(state: InputState, tick: Long): Unit = {}
}
