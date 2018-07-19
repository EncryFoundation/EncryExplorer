package controllers

import javax.inject.{Inject, Singleton}
import loggingSystem.LoggingAction
import play.api.db.DBApi
import play.api.libs.json.JsValue
import play.api.mvc._

@Singleton
class HistoryController @Inject() (dpApi: DBApi, loggingAction: LoggingAction, cc: ControllerComponents)
  extends AbstractController(cc) {

//  val postgresConnection = new

//  val test = new DBHandler()

  def getHeaderR(id: Long): Action[AnyContent] = loggingAction { implicit request: Request[AnyContent] =>
    println(id)
    val requestBody: Option[JsValue] = request.body.asJson
    println(requestBody)
    val requestBodyText = request.body.asText
    println(requestBodyText)



    Ok("good")
//    requestBody.map { id =>
    }
}
