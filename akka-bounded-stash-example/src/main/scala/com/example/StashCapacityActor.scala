package com.example

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props, Stash}

/**
  * Created by piguanghua on 3/12/18.
  */
class StashCapacityActor  extends Actor with Stash with ActorLogging{
  var msgs = List.empty[String]

  override def receive: Receive = {
    case "open" =>
      context.become(open)
      unstashAll()
    case msg: String =>{
      log.info(s"stash $msg")
      stash()
    }
  }

  def open: Receive = {
    case msg: String =>
      msgs = msgs :+ msg
  }
}

object StashCapacityActor {
  def props = Props(classOf[StashCapacityActor]).withMailbox("stash-capacity-mailbox")
}
