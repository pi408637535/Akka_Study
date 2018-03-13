package actors

/**
  * Created by piguanghua on 3/13/18.
  */
sealed trait Message

case class Work(taskNb: Int) extends Message
case class WorkCompleted(workerNb: Int, taskNb: Int, duration: Int) extends Message

case class Dispatch(taskNb: Int) extends Message
