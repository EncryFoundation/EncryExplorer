package models.services

import javax.inject.Inject
import models.Header
import scorex.crypto.encode.Base16

import scala.concurrent.{ExecutionContext, Future}

class HistoryControllerService @Inject()(service: ServicesHandler)(implicit ec: ExecutionContext) {

  def getHeaderR(id: String): Future[Header] = {
    if (Base16.decode(id).isSuccess) service.hs.getHeaderById(id).unsafeToFuture()
    else Future.failed(new IllegalArgumentException)
  }
}
