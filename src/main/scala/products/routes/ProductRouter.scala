package products.routes

import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import akka.actor.{Props, ActorLogging, Actor, ActorRef}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import products.actors.ProductRequestHandler
import products.messages.ProductMessages._

object ProductRouter {

  implicit val system = context.system
  implicit val timeout: Timeout = 20.seconds

  val productRequestHandler = system.actorOf(ProductRequestHandler.props(), "productRequestHandler")

  def putInProductIdChangePrice(id : Int) : Route = {
    path("changePrice") { // /product/:id/changePrice
      put {
        complete(StatusCodes.OK, s"Changement du prix du produit $id")
      }
    }
  }


  def putInProductIdChangeLabel(id : Int) : Route = {
    path("changeName") { // /product/:id/changeName
      put {
        complete(StatusCodes.OK, s"Changement du Label du produit $id")
      }
    }
  }

  def putInProductId(id : Int) : Route = {
    putInProductIdChangePrice(id) ~
    putInProductIdChangeLabel(id)
  }

  def getByProductId(id: Int) : Route = {
    get {
      complete(StatusCodes.OK, s"Un produit spécifique $id")
    }
  }

  def deleteByProductId(id: Int) : Route = {
    delete {
      complete(StatusCodes.OK, s"le produit $id a été supprimer")
    }
  }

  def productId(id : Int) : Route = {
    pathEndOrSingleSlash{ // /product/:id/
      getByProductId(id) ~
      deleteByProductId(id)
    } ~
    putInProductId(id)
  }


  def getProducts: Route =
    get {
      onSuccess(productRequestHandler ? GetProductsRequest) {
        case response: ProductResponse =>
          complete(StatusCodes.OK, "La liste de produit")
        case _ =>
          complete(StatusCodes.InternalServerError)
      }
    }

  def postProduct: Route =
    post {
      complete(StatusCodes.OK, "Le produit a été créer")
    }

  def product: Route =
    pathPrefix("product") { // the products
      pathEndOrSingleSlash { // /product or /product/
        getProducts ~
        postProduct
      } ~
      pathPrefix(IntNumber) {id => productId(id)}
    }

  def welcomeOnApiPath: Route =
    pathEndOrSingleSlash {
      complete(
        HttpEntity(
          ContentTypes.`text/plain(UTF-8)`,
          "<html><body>Hello ! Welcome on the product API</body></html>"
        )
      )
    }

  def route: Route =
    product ~
    welcomeOnApiPath

}
