/**
  * Created by piguanghua on 3/12/18.
  */
import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.example.StashCapacityActor
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class StashCapacityActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("MySpec"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "Bounded actor" must {
    "gracefully drop messages" in {
      val stashActor:TestActorRef[StashCapacityActor] =
        TestActorRef(StashCapacityActor.props)

      (1 to 50).foreach { i =>
        stashActor ! "msg" + i
        //(??)I thought the test actor ref should default to calling thread dispatcher?
        Thread.sleep(10) //sleep to maintain ordering for the assertions
      }

      stashActor ! "open"

      Thread.sleep(10)

      val msgs = stashActor.underlyingActor.msgs

      msgs.foreach(ele=>{
        println("ele=" + ele)
      })

      msgs.size should equal(50)
      msgs.head should equal("msg1")
      msgs.last should equal("msg50")

    }
  }
}
