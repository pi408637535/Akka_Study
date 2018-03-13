import actors.{Count, WordCountActor}
import akka.actor.{ActorSystem, Props}
import akka.pattern._
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

/**
  * Created by piguanghua on 3/13/18.
  */
object DispatcherExampleMain extends App {

  println("Hello, You are in Dispatcher example")

  implicit val timeOut = Timeout(10 seconds)

  //Defining actor system here
  val system = ActorSystem("word-processor")

  //getting data from text file, Change th name of the file according to your system
  val fileContent = scala.io.Source.fromFile("/Users/piguanghua/Example_Scala/study_akka/akka-dispatcher/SampleFile/fileExample.txt").getLines.mkString

  /**
    * First way to use dispatchers with conf file
    */
  val wordCounterActor = system.actorOf(Props[WordCountActor], "wordCounter")

  val actorpath = wordCounterActor.path

  println("WordCountActor path :::::::::::: " + actorpath)

  val res = wordCounterActor ? Count(fileContent)

  res.map { count =>
    println("TOTAL CHARACTERS COUNT IN FILE ::::: " + count.asInstanceOf[Int])
    Thread.sleep(200)
  }

  try {
    system.awaitTermination(16 seconds)
  } catch {
    case e: Exception => {
      println("ApplicationMain" + e.getMessage)
      system.shutdown()
    }
  }


}
