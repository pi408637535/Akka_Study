/**
  * Created by piguanghua on 3/13/18.
  */
import actors.{Dispatch, Master}
import akka.actor.{ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration.DurationInt

object Boot extends App{
  println("SYSTEM: System started, starting work dispatcher simulation...")
  implicit val system = ActorSystem("Routing")

  implicit val timeout = Timeout(2 seconds)

  val dispatcher = system.actorOf(Props[Master], name = "master")

  for (i <- 1 to 20) {
    dispatcher ! Dispatch(i)
  }
}
