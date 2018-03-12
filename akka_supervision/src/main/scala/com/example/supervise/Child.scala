package com.example.supervise

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.example.supervise.common.{Collapse, PingMessage, SomeException}

import scala.util.Random

/**
  * Created by piguanghua on 3/12/18.
  */
class Child(supervisor: ActorRef) extends Actor with ActorLogging {


  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info(s"[Child] Pre restart - Restarting because of exception with message: $message")
  }


  override def postRestart(reason: Throwable): Unit = {
    log.info("[Child] Post restart - sending a pong")
    supervisor ! PingMessage("pong")
  }


  override def preStart(): Unit = {
    log.info(s"[Child] preStart")
  }


  override def postStop(): Unit = {
    log.info(s"[Child] postStop")
  }

  override def receive: Receive = {
    case PingMessage(text)=>{
      log.info(s"[Child] Received $text")
      if (rand < 0.1) {
        log.info("[Child] Throwing some exception")
        throw new SomeException("Some exception")
      }
      log.info("[Child] Sending pong to supervisor")
      supervisor ! PingMessage("pong")
    }
    case Collapse=>{
      throw new Exception
    }


  }

  def rand = new Random().nextDouble
}

object Child{
  def props(supervisor: ActorRef): Props = Props(new Child(supervisor))
}
