package actors

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}

/**
  * Created by piguanghua on 3/13/18.
  */
class WordCountActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case Count(line: String) => {
      log.info("WordCountActor called")
      log.info("Thread name for counter actor::::::::::::::: " + Thread.currentThread().getName)

      val printer = context.actorOf(Props[PrinterActor].withDispatcher("my-dispatcher"), "printerActor")
      log.info("PrinterActor path ::::: " + printer.path)
      printer ! Print(line)
      sender ! line.length()
    }
  }
}
