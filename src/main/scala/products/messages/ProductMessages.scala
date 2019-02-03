package products.messages

import products.models._
import akka.actor._

object ProductMessages {

  case class ProductResponse(product: Product)
  object GetProductsRequest
  
}
