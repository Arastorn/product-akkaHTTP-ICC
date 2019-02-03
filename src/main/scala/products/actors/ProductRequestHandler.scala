package products.actors

import akka.actor.{Actor, ActorLogging, Props}
import products.models._
import products.messages.ProductMessages._

class ProductRequestHandler extends Actor with ActorLogging{

  var product: Product = Product(0,"Voiture",5000)

  override def receive: Receive = {
    case GetProductsRequest =>
      log.debug("Received GetProductsRequest")
      sender() ! ProductResponse(product)
  }
}

object ProductRequestHandler {
  def props(): Props = {
    Props(classOf[ProductRequestHandler])
  }
}
