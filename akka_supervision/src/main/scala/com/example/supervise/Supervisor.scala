package com.example.supervise

import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import com.example.supervise.common.{Collapse, Initialize, PingMessage, SomeException}

import scala.concurrent.duration._

/**
  * Created by piguanghua on 3/12/18.
  */
class Supervisor extends Actor with ActorLogging {

  var child: ActorRef = _
  var times: Int = 0


  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(
    maxNrOfRetries = 2,
    withinTimeRange = 5 seconds
  ) {
    case _: SomeException => {
      log.info("OneForOneStrategy SomeException")
      Restart
    }
    case _: Exception => {
      log.error("OneForOneStrategy Exception")
      Escalate
    }
  }

  override def receive: Receive = {
    case Initialize => {
      log.info(" Supervisor Initialize")
      child = context.actorOf(Child.props(self), "Child")
      child ! PingMessage("ping")
    }
    case PingMessage(text: String) => {
      times = times + 1
      log.info(s"[Supervisor] Received $text ---$times")
    //  child ! PingMessage("ping")
      if(times > 20){
        child ! Collapse
      }else{
        child ! PingMessage("ping")
      }
    }

  }
}

object Supervisor {
  val props = Props[Supervisor]
}
