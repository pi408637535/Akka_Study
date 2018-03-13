package actors

import akka.actor.Actor
import akka.actor.Actor.Receive

import scala.util.Random

/**
  * Created by piguanghua on 3/13/18.
  */
class Worker extends Actor{



  override def receive: Receive = {
    case Work(taskNb) =>{
      println("WORKER" + getIdentifier + ": started working on task #" + taskNb)
      val time = randomTime
      doWork(time)
      sender ! WorkCompleted(getIdentifier, taskNb, time)
    }
  }

  private def randomTime: Int = {
    val rnd = new Random()
    1000 + rnd.nextInt((5000 - 1000) + 1)
  }

  private def doWork(time: Int) = Thread sleep time


  private def getIdentifier: Int = self.path.toString match {
    case (x) if x contains """$a""" => 1
    case (x) if x contains """$b""" => 2
    case (x) if x contains """$c""" => 3
    case (x) if x contains """$d""" => 4
    case (x) if x contains """$e""" => 5
  }
}
