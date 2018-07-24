package services

import models.{Header, HistoryDao}
import org.bouncycastle.util.encoders.DecoderException
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpecLike, Matchers}
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers.{eq => eq_}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class HistoryServiceSpec extends FlatSpecLike with Matchers with MockitoSugar with ScalaFutures {

  "HistoryService#getHeader" should "find header by is's id if exists" in new HistoryServiceSpecWiring {
      when(historyDaoMock.findHeader(sampleHeaderId)).thenReturn(Future.successful(Some(sampleHeader)))
      whenReady(service.getHeader(sampleHeaderId)) { result =>
        verify(historyDaoMock).findHeader(eq_(sampleHeaderId))
        result shouldBe Some(sampleHeader)
      }
    }

  it should "validate that header id is valid" in new HistoryServiceSpecWiring {
    whenReady(service.getHeader(incorrectHeaderId).failed) { ex =>
      verify(historyDaoMock, never()).findHeader(eq_(sampleHeaderId))
      ex shouldBe a[DecoderException]
    }
  }

  private trait HistoryServiceSpecWiring {
    val historyDaoMock: HistoryDao = mock[HistoryDao]
    val service = new HistoryService(historyDaoMock)
    val sampleHeaderId: String = "000097b22265ddb9197a49ff3ed21ce8dc21dc0fa51cb0f2ba2fbe326bbe175a"
    val sampleHeight: Int = 2134
    val sampleHeader: Header = Header(
      sampleHeaderId,
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
    val oneLevelLowerHeader: Header = sampleHeader.copy(
      id = "000540338775ab9dad5effefe993c43d8ab2cf66f8a26b883c980d5e10d4899a",
      height = sampleHeight - 1
    )
    val incorrectHeaderId = "qqq"
  }

}
