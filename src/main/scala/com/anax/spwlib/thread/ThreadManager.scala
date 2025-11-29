package com.anax.spwlib.thread

import com.anax.spwlib.thread.ThreadManager.{DO_NOTHING_ON_EXIT, TERMINATE_ON_EXIT}

class ThreadManager (val onExit: Int = DO_NOTHING_ON_EXIT) {
	var running: Boolean = true
	var runningThreads: Int = 0
	val lock: AnyRef = new AnyRef
	var onExitListener: () => Unit = () => ()
	
	def register(): Unit = lock.synchronized {
		runningThreads += 1
	}
	
	def deregister(): Unit = lock.synchronized {
		runningThreads -= 1
		if(runningThreads <= 0){exit()}
	}
	
	private def exit(): Unit = {
		onExitListener()
		onExit match{
			case TERMINATE_ON_EXIT => {System.exit(0)}
			case _ => {}
		}
	}
	
}
object ThreadManager {
	val DO_NOTHING_ON_EXIT = 0
	val TERMINATE_ON_EXIT = 1
}

