package controllers

import loggingSystem.LoggingAction
import javax.inject.{Inject, _}
import play.api.mvc._

@Singleton
class HomeController @Inject()(loggingAction: LoggingAction, cc: ControllerComponents)
  extends AbstractController(cc) with ControllerHelpers {

  def index(): Action[AnyContent] = loggingAction { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
