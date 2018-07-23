package models.services

import javax.inject.Inject
import models.Header
import scorex.crypto.encode.Base16

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class HistoryControllerService @Inject()(service: ServicesHandler)(implicit ec: ExecutionContext) {

  def getHeaderR(id: String): Future[Header] = {
    if (Base16.decode(id).isSuccess) service.hs.getHeaderById(id).unsafeToFuture()
    else Future.failed(new IllegalArgumentException)
  }

  def getHeadersAtHeightR(height: String): Future[List[Header]] = {
    Try(height.toInt).filter(_ >= 0) match {
    case Success(h) => service.hs.getHeadersAtHeight(h).unsafeToFuture()
    case Failure(t: Throwable) => Future.failed(new IllegalArgumentException)}
  }
}
