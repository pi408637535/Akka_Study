package com.example.supervise

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import com.example.supervise.common.{PingMessage, SomeException}

import scala.util.Random

/**
  * Created by piguanghua on 3/12/18.
  */
class OtherActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case PingMessage(text)=>{
      log.info(s"[Child] Received $text")

      for(i <- 1 to 200){
        if (rand < 0.1) {
          log.info("[OtherActor] Throwing some exception")
          context.stop(self)
        }
        log.info(s"[OtherActor] $i"  )
      }


    }
  }

  def rand = new Random().nextDouble
}

object OtherActor {
  val props = Props[OtherActor]
}
