package products.services

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import scala.util.Random
import scala.io.StdIn

object AkkaHttpMicroservice extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

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
    val route =
        pathPrefix("product") { // the products
          pathEndOrSingleSlash { // /product or /product/
            get {
              complete("La liste de produit")
            } ~
            post {
              complete("Créer un produit")
            }
          } ~
          pathPrefix(IntNumber) { id => // /product/:id our /product/:id/
            pathEndOrSingleSlash {
              get {
                complete(s"Un produit spécifique $id")
              } ~
              delete {
                complete("produit supprimé")
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
        } ~
        pathEndOrSingleSlash {
            complete(
              HttpEntity(
                ContentTypes.`text/plain(UTF-8)`,
                "<html><body>Hello ! Welcome on the product API</body></html>"
              )
            )
        }


    val bindingFuture = Http().bindAndHandle(route, "localhost", 9000)
    println(s"Server online at http://localhost:9000/")

}
