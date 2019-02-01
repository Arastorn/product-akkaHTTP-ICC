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
import products.routes.ProductRouter


object ProductMicroService extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val bindingFuture = Http().bindAndHandle(ProductRouter.route, "localhost", 9000)
  println(s"Server online at http://localhost:9000/")

}
