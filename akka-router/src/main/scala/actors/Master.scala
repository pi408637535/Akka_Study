package actors

import akka.actor.{Actor, Props}
import akka.routing.RoundRobinPool
import akka.util.Timeout

import scala.concurrent.duration.DurationInt
/**
  * Created by piguanghua on 3/13/18.
  */
class Master extends Actor{

  implicit val timeout = Timeout(2 seconds)

  val router = context.actorOf(Props[Worker].withRouter(RoundRobinPool(nrOfInstances = 5)), "router")


  override def receive: Receive = {
    case Dispatch(taskNb) => {
      println("MASTER: Dispatching task #" + taskNb)
      router ! Work(taskNb)
    }
    case WorkCompleted(getIdentifier, taskNb, time) => {
      println("MASTER: Worker #" + getIdentifier + " completed task #" + taskNb + " in " + time + " ms")
    }
    case _ => Unit // Do nothing
  }


}
