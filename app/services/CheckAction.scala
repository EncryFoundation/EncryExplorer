package services

import javax.inject.Inject
import scorex.crypto.encode.Base16

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.Future.{successful => resolve}
import play.api.mvc._

class CheckAction(modifierId: String) extends ActionBuilder[Request] {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    println(request.headers)
    println(request.uri)
    println(modifierId)

    if (Base16.decode(modifierId).isSuccess) block(request) else resolve(Results.Forbidden)
  }
}

class CheckActionFactory {
  def apply(modifierId: String): CheckAction = new CheckAction(modifierId)
}


