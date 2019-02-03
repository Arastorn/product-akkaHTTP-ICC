package products.services

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import products.routes.ProductRouter


object ApplicationServer {

  val host="localhost"
  val port = 9000

  def main(args: Array[String]) : Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    

    val bindingFuture = Http().bindAndHandle(ProductRouter.route, host, port)
    println(s"Server online at http://localhost:9000/")
  }
}
