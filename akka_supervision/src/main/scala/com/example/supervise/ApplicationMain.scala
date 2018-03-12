package com.example.supervise

import akka.actor.ActorSystem
import com.example.supervise.common.Initialize
import scala.concurrent.duration._

/**
  * Created by piguanghua on 3/12/18.
  */
object ApplicationMain extends App {
  val system = ActorSystem("MyActorSystem")
  val supervisor = system.actorOf(Supervisor.props, "supervisor")
  supervisor ! Initialize


  try {
    system.awaitTermination(160 seconds)
  } catch {
    case e: Exception => {
      println("ApplicationMain" + e.getMessage)
      system.shutdown()
    }
  }

}
