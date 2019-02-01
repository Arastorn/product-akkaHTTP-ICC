package products.routes

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import scala.util.Random
import scala.io.StdIn

object ProductRouter {

/*
  def product(id : Int) : Route {
    pathEndOrSingleSlash{
      get {
        complete(s"Un produit spécifique $id")
      } ~
      delete {
        complete(s"le produit numéro $id a été supprimé")
      }
    } ~
    path("changePrice") { // /product/:id/changePrice
      put {
        complete("Changement du prix du produit")
      }
    } ~
    path("changeName") { // /product/:id/changeName
      put {
        complete("Changement du Label du produit")
      }
    }
  }
*/
  // TO DO : Factorization
  def route: Route =
    pathPrefix("product") { // the products
      pathEndOrSingleSlash { // /product or /product/
        get {
          complete("La liste de produit")
        } ~
        post {
          complete("Le produit a été créer")
        }
      } ~
      pathPrefix(IntNumber) { id => // /product/:id our /product/:id/
        pathEndOrSingleSlash {
          get {
            complete(s"Un produit spécifique $id")
          } ~
          delete {
            complete(s"le produit $id a été supprimer")
          }
        } ~
        path("changePrice") { // /product/:id/changePrice
          put {
            complete(s"Changement du prix du produit $id")
          }
        } ~
        path("changeName") { // /product/:id/changeName
          put {
            complete(s"Changement du Label du produit $id")
          }
        }
      }
    } ~
    pathEndOrSingleSlash {
        complete(
          HttpEntity(
            ContentTypes.`text/plain(UTF-8)`,
            "<html><body>Hello ! Welcome on the product API</body></html>"
          )
        )
    }

}
