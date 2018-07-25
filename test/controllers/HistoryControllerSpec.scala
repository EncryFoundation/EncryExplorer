package controllers

import models.Header
import org.mockito.Mockito._
import org.scalatestplus.play.PlaySpec
import org.mockito.ArgumentMatchers.{eq => eq_}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.{FakeRequest, Injecting}
import play.api.test.Helpers._
import services.HistoryService
import org.scalatest.Matchers._
import org.scalatest.mockito.MockitoSugar
import play.api.mvc.Result
import scala.concurrent.{ExecutionContext, Future}

class HistoryControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  "HistoryController#findHeader" should {

    "process header requests by id" in new HistoryControllerSpecWiring {
      when(historyServiceMock.findHeader(sampleHeaderId)).thenReturn(Future.successful(Some(sampleHeader)))
      val result: Future[Result] = controller.findHeader(sampleHeaderId).apply(FakeRequest())
      verify(historyServiceMock).findHeader(eq_(sampleHeaderId))
      status(result) shouldBe OK
    }

  }

  "HistoryController#findHeaderAtHeight" should {
    "return headers at given height" in new HistoryControllerSpecWiring {
      when(historyServiceMock.listHeadersAtHeight(sampleHeight)).thenReturn(Future.successful(List(sampleHeader)))
      val result: Future[Result] = controller.findHeaderAtHeight(sampleHeight).apply(FakeRequest())
      verify(historyServiceMock).listHeadersAtHeight(eq_(sampleHeight))
      status(result) shouldBe OK
    }
  }

  "HistoryController#findBestHeaderAtHeight" should {
    "return header with \"bestChain\" equal true" in new HistoryControllerSpecWiring {
      when(historyServiceMock.findBestHeaderAtHeight(sampleHeight)).thenReturn(Future.successful(Some(sampleHeader)))
      val result: Future[Result] = controller.findBestHeaderAtHeight(sampleHeight).apply(FakeRequest())
      verify(historyServiceMock).findBestHeaderAtHeight(eq_(sampleHeight))
      status(result) shouldBe OK
    }
  }

  "HistoryController#listLastHeaders" should {
    "return header with \"bestChain\" equal true and lowest height" in new HistoryControllerSpecWiring{
      when(historyServiceMock.listLastHeaders(5)).thenReturn(Future.successful(List(oneLevelLowerHeader)))
      val result: Future[Result] = controller.listLastHeaders(5).apply(FakeRequest())
      verify(historyServiceMock).listLastHeaders(eq_(5))
      status(result) shouldBe OK
    }
  }

  "HistoryController#listHeadersByHeightRange" should {
    "return headers in given height range" in new HistoryControllerSpecWiring {
      when(historyServiceMock.listHeadersByHeightRange(oneLevelLowerHeader.height, sampleHeight)).thenReturn(Future.successful(List(oneLevelLowerHeader, sampleHeader)))
      val result: Future[Result] = controller.listHeadersByHeightRange(oneLevelLowerHeader.height, sampleHeight).apply(FakeRequest())
      verify(historyServiceMock).listHeadersByHeightRange(eq_(oneLevelLowerHeader.height), eq_(sampleHeight))
      status(result) shouldBe OK
    }
  }

  private trait HistoryControllerSpecWiring {
    val historyServiceMock: HistoryService = mock[HistoryService]
    val controller: HistoryController = new HistoryController(stubControllerComponents(), historyServiceMock)(inject[ExecutionContext])
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
  }

}
