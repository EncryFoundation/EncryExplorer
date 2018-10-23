package controllers

import javax.inject.Inject
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.ws.WSClient
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import views.html.getNodeInfo

class NodeController @Inject()(cc: ControllerComponents, ws: WSClient)
                              (implicit ex: ExecutionContext) extends AbstractController(cc) with Circe {


  def nodeinfo(id: Int) = Action.async {
    ws.url(s"http://testnet${if(id!=10) 0 + s"$id" else 10}.encry.ru:9051/info").get().map { response =>
      val a: JsValue = Json.parse(response.body)
      //val b: Seq[String] = a.toString.split(",")
      val name: JsLookupResult = a \ "name"
      val fullHeight: JsLookupResult = a \ "fullHeight"
      val headersHeight: JsLookupResult = a \ "headersHeight"
      val isMining: JsLookupResult = a \ "isMining"
      var info: Seq[String] = Seq()

      name match {
        case JsDefined(name) => info = info :+ s"${name.toString()}"
        case undefined: JsUndefined => println(undefined.validationError)
      }

      fullHeight match {
        case JsDefined(fullHeight) => info = info :+ s"${fullHeight.toString()}"
        case undefined: JsUndefined => println(undefined.validationError)
      }

      headersHeight match {
        case JsDefined(headersHeight) => info = info :+ s"${headersHeight.toString()}"
        case undefined: JsUndefined => println(undefined.validationError)
      }

      isMining match {
        case JsDefined(isMining) => info = info :+ s"${isMining.toString()}"
        case undefined: JsUndefined => println(undefined.validationError)
      }

      Ok(getNodeInfo(info))

    }
  }
}

