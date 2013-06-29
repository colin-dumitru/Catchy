package edu.catchy.ui.feedList.controller

import javafx.fxml.FXML
import javafx.scene.control.Label
import edu.catchy.ui.model.FolderCell

/**
 * Catalin Dumitru
 * Date: 6/29/13
 * Time: 1:53 PM
 */
class FolderCellController {
  @FXML
  private val folderName: Label = null

  def initDate(cell: FolderCell) {
    folderName.setText(cell.name)
  }

  def initialize() {

  }

}
