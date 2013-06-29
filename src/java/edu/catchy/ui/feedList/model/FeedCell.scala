package edu.catchy.ui.model

import edu.catchy.ui.feedList.model.Listable
import javafx.scene.Node
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import edu.catchy.ui.feedList.controller.FeedCellController

/**
 * irina
 * Date: 6/24/13
 * Time: 6:28 PM
 */
trait FeedCell extends Listable {
  def id: Int

  def title: String

  def url: String

  def getRoot: Node = {
    val loader = new FXMLLoader(getClass.getResource("/layout/feed_cell.fxml"))

    val page: Pane = loader.load().asInstanceOf[Pane]
    val controller = loader.getController[FeedCellController]

    controller.initDate(this)

    page
  }
}
