package controllers

import LoggingSystem.LoggingAction
import javax.inject.{Inject, _}
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() (loggingAction: LoggingAction, cc: ControllerComponents) extends AbstractController(cc) with ControllerHelpers {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

  def index() = loggingAction { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
