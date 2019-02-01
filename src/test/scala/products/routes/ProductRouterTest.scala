package products.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class ProductRouterTest extends WordSpec with Matchers with ScalatestRouteTest {

  "A Product Router" should {

    "list all products" in {
      Get("/product") ~> ProductRouter.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "La liste de produit"
      }
    }

    "create a product" in {
      Post("/product") ~> ProductRouter.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Le produit a été créer"
      }
    }

    "Get product with id = 1 in the list" in {
      Get("/product/1") ~> ProductRouter.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Un produit spécifique 1"
      }
    }

    "Delete product with id = 1 in the list" in {
      Delete("/product/1") ~> ProductRouter.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "le produit 1 a été supprimer"
      }
    }

    "Change the label of product with id = 1 in the list" in {
      Put("/product/1/changeName") ~> ProductRouter.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Changement du Label du produit 1"
      }
    }

    "Change the Price of product with id = 1 in the list" in {
      Put("/product/1/changePrice") ~> ProductRouter.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Changement du prix du produit 1"
      }
    }

  }
}
