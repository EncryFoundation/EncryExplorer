package controllers

import models.{Header, HistoryDao, Transaction, TransactionsDao}
import org.mockito.Mockito.{verify, when}
import play.api.test.Helpers.{status, stubControllerComponents}
import org.scalatestplus.play.PlaySpec
import org.mockito.ArgumentMatchers.{eq => eq_}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.{FakeRequest, Injecting}
import play.api.test.Helpers._
import org.scalatest.Matchers._
import org.scalatest.mockito.MockitoSugar
import play.api.mvc.Result
import scala.concurrent.{ExecutionContext, Future}

class BlockControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  "BlockController#findBlockApi" should {
    "find block by id if exists" in new BlockControllerSpecWiring {
      when(transactionsServiceMock.listByBlockId(sampleBlockId)).thenReturn(Future.successful(transactions))
      when(historyServiceMock.findHeader(sampleBlockId)).thenReturn(Future.successful(Some(sampleHeader)))
      val result: Future[Result] = controller.findBlockApi(sampleBlockId).apply(FakeRequest())
      verify(transactionsServiceMock).listByBlockId(eq_(sampleBlockId))
      verify(historyServiceMock).findHeader(eq_(sampleBlockId))
      status(result) shouldBe OK
    }

    "return 404 if block not found" in new BlockControllerSpecWiring {
      when(transactionsServiceMock.listByBlockId(sampleBlockId)).thenReturn(Future.successful(List[Transaction]()))
      when(historyServiceMock.findHeader(sampleBlockId)).thenReturn(Future.successful(None))
      val result: Future[Result] = controller.findBlockApi(sampleBlockId).apply(FakeRequest())
      verify(transactionsServiceMock).listByBlockId(eq_(sampleBlockId))
      verify(historyServiceMock).findHeader(eq_(sampleBlockId))
      status(result) shouldBe 404
    }
  }

  private trait BlockControllerSpecWiring {
    val historyServiceMock: HistoryDao = mock[HistoryDao]
    val transactionsServiceMock: TransactionsDao = mock[TransactionsDao]
    val controller: BlockController = new BlockController(stubControllerComponents(), historyServiceMock, transactionsServiceMock)(inject[ExecutionContext])
    val sampleBlockId: String = "000097b22265ddb9197a49ff3ed21ce8dc21dc0fa51cb0f2ba2fbe326bbe175a"
    val sampleHeight: Int = 2134
    val sampleHeader: Header = Header(
      sampleBlockId,
      "03380dd3dac6b2360aaf54b17f08617839453ae2532406c38761ce558c92e9fc",
      0,
      sampleHeight,
      "d122017c38e2fdc4c996fcc32c51776543ff73f929dc4ad6f02ab4fac6d16c6d",
      "69b8d7c7d8ebfe499ddaa1de177114608f4ee3c1dd61ab060ee12abbb4f767b510",
      "6955076caa10ccbac39a7fd19cede5ad058f5eaa574f76fc13ace1fcc48dfd54",
      1532087272902L,
      56,
      447,
      Array(432,11576,23592,85771,5416,55037,53893,124149,14068,25170,31805,39989,36280,101932,57359,123648,15589,125264,41411,96028,81408,122850,118361,121825,27357,84717,41906,74892,51146,100436,72845,109986).mkString(","),
      None,
      1,
      "3BfPFRFKLKxRU2TWLaVcyJgCcQBs6LBAYGeyDuun1bEfGEU8TQ",
      1991616,
      0,
      112,
      bestChain = true
    )
    val sampleTxId1: String = "0b6df74842f4088b8ba3b6ad7b744cd415769b4a27470f993699c3827a98030c"
    val sampleTxId2: String = "0b6bf74842f4088b8ba3b6ad7b744cd415769b4a27470f993699c3827a98030c"
    val sampleTransaction1: Transaction = Transaction(
      sampleTxId1,
      sampleBlockId,
      true,
      1532037033902L
    )
    val sampleTransaction2: Transaction = Transaction(
      sampleTxId2,
      sampleBlockId,
      false,
      1532036033902L
    )
    val transactions: List[Transaction] = List(sampleTransaction2, sampleTransaction1)
  }

}
