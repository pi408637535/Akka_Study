package com.example.supervise

import akka.actor.Actor.Receive
import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import com.example.supervise.common.{Initialize, PingMessage, SomeException}

import scala.concurrent.duration._
/**
  * Created by piguanghua on 3/12/18.
  */
class Supervisor(other: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {
    case Initialize => {
      log.info(" Supervisor Initialize")
      context.watch(other)
      other ! PingMessage("ping")
    }
    case Terminated(actor: ActorRef) =>{
        log.info("Supervisor" + actor.path)
    }
  }

}


object Supervisor{
  def props(other: ActorRef): Props = Props(new Supervisor(other))
}