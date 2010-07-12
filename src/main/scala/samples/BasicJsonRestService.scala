package samples

import scala.reflect.BeanInfo
import java.net.URI
import javax.ws.rs._
import javax.ws.rs.core.MediaType._
import javax.ws.rs.core.Response
import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.actor.ActorRegistry.actorsFor
import se.scalablesolutions.akka.serialization.Serializer
import collection.mutable.HashMap

@BeanInfo
case class User(id:String, name:String) {
  def this() = this(null, null)
}

object Commands {

  case class GetUserById(id:String)
  case object GetUserList
  case class CreateNewUser(user:Array[Byte])
  case class UpdateUser(id:String, user:Array[Byte])
}

class UserService extends Actor {
  import Commands._

  private val users = new HashMap[String,User]


  def receive = {

    case GetUserList =>
      val userList = users map { u => u._2 }
      reply(Serializer.ScalaJSON.toBinary(userList.toList))

    case CreateNewUser(userBytes) =>
      val newUser = Serializer.ScalaJSON.fromJSON[User]( new String(userBytes)).asInstanceOf[User]
      users += (newUser.id -> newUser)
      reply(newUser.id)

    case UpdateUser(id, user) =>
      users(id) = Serializer.ScalaJSON.fromJSON[User]( new String(user)).asInstanceOf[User]
      reply("done")

    case GetUserById(id) =>
      reply(Serializer.ScalaJSON.toBinary(users(id)))

    case x =>
      reply("not implemented.. really!".getBytes)
  }
}

@Path("/users")
class BasicJsonRestService {
  import Commands._

  @GET
  @Produces(Array(APPLICATION_JSON))
  def get = {
    val result = for{a <- actorsFor(classOf[UserService]).headOption
                     r <- (a !! GetUserList)} yield r
    result match {
      case Some(data) => Response.ok(data).build
      case None => Response.noContent.build
    }
  }


  @PUT
  @Consumes(Array(APPLICATION_JSON))
  def post(userBytes:Array[Byte]) = {
    val result = for{a <- actorsFor(classOf[UserService]).headOption
                     r <- (a !! CreateNewUser(userBytes))} yield r
    result match {
      case Some(id) => Response.created(new URI("/api/users/"+id)).build
      case None => Response.notModified.build
    }
  }

  @GET @Path("/{id}")
  @Produces(Array(APPLICATION_JSON))
  def get(@PathParam("id") id:String) = {
    val result = for{a <- actorsFor(classOf[UserService]).headOption
                     r <- (a !! GetUserById(id))} yield r
    result match {
      case Some(data) => Response.ok(data).build
      case None => Response.noContent.build
    }
  }

  @PUT @Path("/{id}")
  @Consumes(Array(APPLICATION_JSON))
  def put(@PathParam("id") id:String, userBytes:Array[Byte]) = {
    val result = for{a <- actorsFor(classOf[UserService]).headOption
                     r <- (a !! UpdateUser(id, userBytes))} yield r

   if(result.isDefined)
     Response.ok.build
   else
     Response.notModified.build
  }

}
