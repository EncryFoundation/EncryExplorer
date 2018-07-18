package crypto

import scorex.crypto.encode.Base58
import scorex.crypto.hash.Blake2b256
import scala.util.{Failure, Success, Try}


object Base58Check {

  val version: Byte = 1

  val checkSumLength: Int = 4

  private def getCheckSum(bytes: Array[Byte]): Array[Byte] = Blake2b256.hash(bytes).take(checkSumLength)

  def encode(input: Array[Byte]): String = Base58.encode((version +: input) ++ getCheckSum(input))

  def decode(input: String): Try[Array[Byte]] = Base58.decode(input).flatMap { bytes =>
    val checksum: Array[Byte] = bytes.takeRight(checkSumLength)
    val checksumActual: Array[Byte] = getCheckSum(bytes.dropRight(checkSumLength).tail)

    if (checksum.sameElements(checksumActual)) Success(bytes.dropRight(checkSumLength).tail)
    else Failure(new Exception("Wrong checkSum"))
  }
}
