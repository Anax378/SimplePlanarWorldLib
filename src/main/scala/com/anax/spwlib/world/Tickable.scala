package com.anax.spwlib.world

trait Tickable {
	var removeAfterTick: Boolean = false
	def onTick(tick: Long): Unit
}
