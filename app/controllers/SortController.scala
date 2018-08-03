package controllers

import javax.inject.{Inject, Singleton}
import models.Header
import play.api.mvc._
import views.html.{getHeaderList => getHeaderListView}
import scala.concurrent.ExecutionContext

@Singleton
class SortController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  def sortByDate(list: List[Header]): Action[AnyContent] = Action {
    val sortList = list.sortWith(_.timestamp > _.timestamp)
    Ok(getHeaderListView(sortList))
  }
}
