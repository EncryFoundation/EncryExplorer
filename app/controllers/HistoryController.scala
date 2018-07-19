package controllers

import loggingSystem.LoggingAction
import javax.inject.{Inject, Singleton}
import play.api.mvc._
import play.mvc.Action

@Singleton
class HistoryController @Inject() (loggingAction: LoggingAction, cc: ControllerComponents)
  extends AbstractController(cc) {

  def getHeaderR: Action[AnyContent] = loggingAction { implicit request: Request[AnyContent] =>

  }
}
