package actors

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging}

/**
  * Created by piguanghua on 3/13/18.
  */
class PrinterActor  extends Actor with ActorLogging {

  override def receive: Receive = {
    case Print(line: String) => {
      log.info("PrinterActor called")
      log.info("Thread name for printer actor::::::::::::::: " + Thread.currentThread().getName)
      val linesToPrint = line.split(" ").take(300).mkString(" ")
      log.info("First 300 words from the file you entered is :::: " + linesToPrint)
    }
    case _ => log.debug("Oops..!! I did'nt understand the message..!!")
  }
}
