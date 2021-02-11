package controllers

import models.ItemObj
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.ItemService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ItemController  @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val is = new ItemService

  def allItems = Action.async { implicit req: Request[AnyContent] =>
    val all = is.readAll()
    all.map { items =>
      Ok(views.html.items(items, ItemObj.createItemForm))
    }
  }

  def createForm = Action.async { implicit req: Request[AnyContent] =>
    ItemObj.createItemForm.bindFromRequest.fold({withErrors =>
      println(withErrors)
      Future.successful(BadRequest(views.html.index("T")))
    }, { item =>
      is.create(item).map(_ => {
        Ok(views.html.index("T"))
      })
    })
  }

}
