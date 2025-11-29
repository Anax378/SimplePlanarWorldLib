package com.anax.spwlib.thread

import com.anax.spwlib.input.{EmptyInputSubscriber, InputProvider, InputSubscriber}
import com.anax.spwlib.world.{EmptyTickable, Tickable}

class UpdateThread (
	                   manager: ThreadManager,
	                   inputProvider: InputProvider,
	                   tickable: Tickable = EmptyTickable,
	                   inputSubscriber: InputSubscriber = EmptyInputSubscriber,
	                   private var tps: Int = 100,
                   ) extends Thread{
	
	private var tickDuration = 1000/tps
	var tick = 0
	
	def setTargetTPS(tps: Int): Unit = {
		this.tps = tps
		tickDuration = 1000/tps
	}
	
	override def run(): Unit = {
		manager.register()
		var last: Long = 0
		while(manager.running){
			last = System.currentTimeMillis()
			tickable.onTick(tick)
			inputSubscriber.handleInput(inputProvider.getInputState, tick)
			tick += 1
			while(last+tickDuration > System.currentTimeMillis()){}
		}
		manager.deregister()
	
	}

}
