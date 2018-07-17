package controllers

import com.typesafe.scalalogging.StrictLogging
import javax.inject.{Inject, _}
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with StrictLogging {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

  def index() = Action { implicit request: Request[AnyContent] =>
    logger.info(s"Method = ${request.method},  " +
      s"URI = ${request.uri},  " +
      s"Remote-address = ${request.remoteAddress},  " +
      s"Domain = ${request.domain},  " +
      s"User-agent = [${request.headers.get("user-agent").getOrElse("N/A")}]")
    Ok(views.html.index())
  }
}
