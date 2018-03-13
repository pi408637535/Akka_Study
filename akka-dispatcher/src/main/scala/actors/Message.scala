package actors

/**
  * Created by piguanghua on 3/13/18.
  */
sealed class Message

case class Count(line: String) extends Message

case class Print(line: String) extends Message
