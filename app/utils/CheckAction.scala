package utils

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import play.api.mvc._
import protocol.crypto.Base58Check
import scorex.crypto.encode.Base16
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.Future.{successful => resolve}
import scala.util.Try

class Base16CheckAction(parser: BodyParsers.Default, modifierId: String) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] =
    if (Base16.decode(modifierId).isSuccess) block(request) else resolve(Results.BadRequest)
}

class Base16CheckActionFactory @Inject()(parser: BodyParsers.Default) {
  def apply(modifierId: String): Base16CheckAction = new Base16CheckAction(parser, modifierId)
}

class HeightCheckAction(parser: BodyParsers.Default, height: Int) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] =
    if (height >= 0) block(request) else resolve(Results.BadRequest)
}

class HeightCheckActionFactory @Inject()(parser: BodyParsers.Default) {
  def apply(height: Int): HeightCheckAction = new HeightCheckAction(parser, height)
}

class QtyCheckAction(parser: BodyParsers.Default, qty: Int) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] =
    if (qty > 0) block(request) else resolve(Results.BadRequest)
}

class QtyCheckActionFactory @Inject()(parser: BodyParsers.Default) {
  def apply(qty: Int): QtyCheckAction = new QtyCheckAction(parser, qty)
}

class FromToCheckAction(parser: BodyParsers.Default, from: Int, to: Int) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] =
    if (from >= 0 && to >= from) block(request) else resolve(Results.BadRequest)
}

class FromToCheckActionFactory @Inject()(parser: BodyParsers.Default) {
  def apply(from: Int, to: Int): FromToCheckAction = new FromToCheckAction(parser, from, to)
}

class FromCountCheckAction(parser: BodyParsers.Default, from: Int, count: Int) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] =
    if (from >= 0 && count > 0) block(request) else resolve(Results.BadRequest)
}

class FromCountCheckActionFactory @Inject()(parser: BodyParsers.Default) {
  def apply(from: Int, count: Int): FromCountCheckAction = new FromCountCheckAction(parser, from, count)
}

class DateFromCountAction(parser: BodyParsers.Default, date: String, count: Int) extends ActionBuilderImpl(parser) {
  private val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    val parsedDate: Try[Date] = Try(sdf.parse(date + " 0:0:0"))
    if (parsedDate.isSuccess && count > 0) block(request) else resolve(Results.BadRequest)
  }
}

class DateFromCountActionFactory @Inject()(parser: BodyParsers.Default) {
  def apply(date: String, count: Int): DateFromCountAction = new DateFromCountAction(parser, date, count)
}

class Base58CheckAction(parser: BodyParsers.Default, address: String) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] =
    if (Base58Check.decode(address).isSuccess) block(request) else resolve(Results.BadRequest)
}

class Base58CheckActionFactory @Inject()(parser: BodyParsers.Default) {
  def apply(address: String): Base58CheckAction = new Base58CheckAction(parser, address)
}