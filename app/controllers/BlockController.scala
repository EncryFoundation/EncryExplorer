package controllers

import io.circe.syntax._
import io.circe.generic.auto._
import javax.inject.{Inject, Singleton}
import models._
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import utils.Base16CheckActionFactory
import views.html.getBlock
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BlockController @Inject()(cc: ControllerComponents,
                                historyDao: HistoryDao,
                                transactionsDao: TransactionsDao,
                                base16Check: Base16CheckActionFactory)
                               (implicit ex: ExecutionContext) extends AbstractController(cc) with Circe {

  def findBlockView(id: String): Action[AnyContent] = base16Check(id).async {
    findBlock(id)
      .map {
        case Some(block) => Ok(getBlock(block))
        case None => NotFound
      }
  }

  def findBlockApi(id: String): Action[AnyContent] = base16Check(id).async {
    findBlock(id)
      .map {
        case Some(block) => Ok(block.asJson)
        case None => NotFound
      }
  }

  def findBlock(id: String): Future[Option[Block]] = {
    val headerFuture: Future[Option[Header]] = historyDao.findHeader(id)
    val payloadFuture: Future[List[Transaction]] = transactionsDao.listByBlockId(id)
    for {
      headerOpt <- headerFuture
      payload <- payloadFuture
    } yield headerOpt match {
      case Some(header) => Some(Block(header, payload))
      case None => None
    }
  }

}
