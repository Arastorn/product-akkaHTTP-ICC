package products.routes

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.testkit.TestActorRef
import akka.actor.ActorSystem

import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration._

import products.actors.ProductRequestHandler
import products.messages.ProductMessages._
import products.models._


class ProductRouterTest extends WordSpec with Matchers with ScalatestRouteTest with ProductRouter {

  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(5 seconds)
  //override def testConfigSource = "akka.loglevel = DEBUG"
  //override def config = testConfig

  val productRequestHandler: TestActorRef[ProductRequestHandler] = TestActorRef[ProductRequestHandler](new ProductRequestHandler())

  "A Product Router" should {

    "list products with no product" in {
      Get("/product") ~> route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[Seq[Product]] shouldBe Nil
      }
    }

    "create a product" in {
      Post("/product", Product(0,"stylo",5)) ~> route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe s"Le produit ${Product(0,"stylo",5)} a bien été créer"
      }
    }

    "Get product with id = 1 in the list" in {
      Get("/product/1") ~> route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Un produit spécifique 1"
      }
    }

    "Delete product with id = 1 in the list" in {
      Delete("/product/1") ~> route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "le produit 1 a été supprimer"
      }
    }

    "Change the label of product with id = 1 in the list" in {
      Put("/product/1/changeName") ~> route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Changement du Label du produit 1"
      }
    }

    "Change the Price of product with id = 1 in the list" in {
      Put("/product/1/changePrice") ~> route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Changement du prix du produit 1"
      }
    }

  }
}
