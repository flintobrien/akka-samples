package samples

import scala.reflect.BeanInfo
import java.net.URI
import javax.ws.rs._
import javax.ws.rs.core.MediaType._
import javax.ws.rs.core.Response
import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.serialization.Serializer
import collection.mutable.HashMap

@BeanInfo
case class User(id:String, name:String) {
	def this() = this(null, null)
}

trait UserService extends Actor {

	private val users = new HashMap[String,User]

	trait Command

	case class GetUserById(id:String) extends Command
	case object GetUserList extends Command
	case class CreateNewUser(user:Array[Byte]) extends Command
	case class UpdateUser(id:String, user:Array[Byte]) extends Command
	
	def receive = {
		case GetUserList => 
			val userList = users map { u => u._2 }
			reply(Serializer.ScalaJSON.out(userList.toList))
		case CreateNewUser(userBytes) => 
			val newUser = Serializer.ScalaJSON.in[User](userBytes).asInstanceOf[User]
			users += (newUser.id -> newUser)
			reply(newUser.id)
		case UpdateUser(id, user) => 
			users(id) = Serializer.ScalaJSON.in[User](user).asInstanceOf[User]
			reply("done")
		case GetUserById(id) => 
			reply(Serializer.ScalaJSON.out(users(id)))
		case x => 
			reply("not implemented".getBytes)
	}
}

@Path("/users")
class BasicJsonRestService extends UserService {

	@GET 
	@Produces(Array(APPLICATION_JSON))
	def get = this.!![Array[Byte]](GetUserList) match {
		case Some(data) => Response.ok(data).build
		case None => Response.noContent.build
	}

	@PUT
	@Consumes(Array(APPLICATION_JSON))
	def post(userBytes:Array[Byte]) = this.!![String](CreateNewUser(userBytes)) match {
		case Some(id) => Response.created(new URI("/api/users/"+id)).build
		case None => Response.notModified.build
	}

	@GET @Path("/{id}")
	@Produces(Array(APPLICATION_JSON))
	def get(@PathParam("id") id:String) = this.!![Array[Byte]](GetUserById(id)) match {
		case Some(data) => Response.ok(data).build
		case None => Response.noContent.build
	}

	@PUT @Path("/{id}")
	@Consumes(Array(APPLICATION_JSON))
	def put(@PathParam("id") id:String, userBytes:Array[Byte]) = 
		if(this.!![Boolean](UpdateUser(id, userBytes)).isDefined)
			Response.ok.build
		else
			Response.notModified.build

}
