package controllers

import javax.inject.{Inject, _}
import play.api.Logger
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(loggingAction: LoggingAction, cc: ControllerComponents)
  extends AbstractController(cc) with ControllerHelpers {

  def index(): Action[AnyContent] = loggingAction { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}

class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    Logger.info(s"Method = ${request.method},  " +
      s"URI = ${request.uri},  " +
      s"Remote-address = ${request.remoteAddress},  " +
      s"Domain = ${request.domain},  " +
      s"User-agent = [${request.headers.get("user-agent").getOrElse("N/A")}]")
    block(request)
  }
}