package controllers

import javax.inject.{Inject, _}
import play.api.mvc._

@Singleton
class HomeController @Inject()( cc: ControllerComponents)
  extends AbstractController(cc) with ControllerHelpers {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
