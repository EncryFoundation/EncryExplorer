package services

import java.util.concurrent.ExecutionException

import models.{Header, Output, TransactionsDao}
import org.bouncycastle.util.encoders.DecoderException
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpecLike, Matchers}
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers.{eq => eq_}
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class TransactionsServiceSpec extends FlatSpecLike with Matchers with MockitoSugar with ScalaFutures {

  "TransactionsService#findOutput" should "find output by it's id" in new TransactionsServiceSpecWiring {
    when(transactionsDaoMock.findOutput(sampleOutputId)).thenReturn(Future.successful(Some(sampleOutput)))
    whenReady(service.findOutput(sampleOutputId)) { result =>
      verify(transactionsDaoMock).findOutput(eq_(sampleOutputId))
      result shouldBe Some(sampleOutput)
    }
  }

  it should "validate that given id is valid Base16 string" in new TransactionsServiceSpecWiring {
    whenReady(service.findOutput(invalidBase16String).failed) { ex =>
      verify(transactionsDaoMock, never()).findOutput(eq_(invalidBase16String))
      ex shouldBe a[DecoderException]
    }
  }

  it should "validate that given id is valid Base58 string" in new TransactionsServiceSpecWiring {
    whenReady(service.listOutputsByAddress(invalidBase58String).failed) { ex =>
      verify(transactionsDaoMock, never()).listOutputsByContractHash(eq_(invalidBase58String), eq_(false))
      ex shouldBe a[ExecutionException]
    }
  }

  private trait TransactionsServiceSpecWiring {
    val transactionsDaoMock: TransactionsDao = mock[TransactionsDao]
    val service = new TransactionsService(transactionsDaoMock)
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
    val sampleOutputId: String = "010000691b35d6eaae31a43a2327f58a78f47293a03715821cf83399e4e3a0b0"
    val sampleTxId: String = "0b6df74842f4088b8ba3b6ad7b744cd415769b4a27470f993699c3827a98030c"
    val sampleOutput: Output = Output(
      sampleOutputId,
      sampleTxId,
      100L,
      "487291c237b68dd2ab213be6b5d1174666074a5afab772b600ea14e8285affab",
      "ede3fb8cbace04e878a0207aeac9bd3ffb3754c84a25eaabe27d17e2493a0092",
      ""
    )
    val invalidBase16String: String = "ййй"
    val invalidBase58String: String = invalidBase16String
    val sampleAddress: String = "4Etkd64NNYEDt8TZ21Z3jNHPvfbvEksmuuTwRUtPgqGH"
  }

}
