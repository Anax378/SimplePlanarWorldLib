package com.anax.spwlib.world

object EmptyTickable extends Tickable{
	override def onTick(tick: Long): Unit = {}
}
