package LoggingSystem

import javax.inject.Inject
import play.api.Logger
import play.api.mvc.{ActionBuilderImpl, BodyParsers, Request, Result}
import scala.concurrent.{ExecutionContext, Future}

class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    Logger.info(s"Method = ${request.method},  " +
                s"URI = ${request.uri},  " +
                s"remote-address = ${request.remoteAddress},  " +
                s"domain = ${request.domain}")
    block(request)
  }
}
