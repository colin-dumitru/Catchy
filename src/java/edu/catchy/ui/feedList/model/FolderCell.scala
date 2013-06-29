package edu.catchy.ui.model

import javafx.scene.layout.Pane
import edu.catchy.ui.feedList.model.Listable
import javafx.scene.Node
import javafx.fxml.FXMLLoader
import edu.catchy.ui.feedList.controller.FolderCellController

/**
 * irina
 * Date: 6/26/13
 * Time: 6:48 PM
 */
trait FolderCell extends Listable {

  def id: Int

  def name: String

  def getRoot: Node = {
    val loader = new FXMLLoader(getClass.getResource("/layout/folder_cell.fxml"))

    val page: Pane = loader.load().asInstanceOf[Pane]
    val controller = loader.getController[FolderCellController]

    controller.initDate(this)

    page
  }
}
