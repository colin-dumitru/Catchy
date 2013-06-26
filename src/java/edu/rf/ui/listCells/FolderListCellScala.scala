package edu.rf.ui.listCells

import edu.rf.model.Cell
import javafx.scene.control.ListCell


/**

  */
/**
 *
 */
class FolderListCellScala() extends ListCell[Cell]() {

  def init {
    addStyleClass()
  }

  private def addStyleClass() {
    this.getStyleClass.add("folder-list-cell")
  }

  protected override def updateItem(item: Cell, empty: Boolean) {
    super.updateItem(item, empty)
    if (empty) {
      setGraphic(null)
    }
    else {
      setGraphic(item.updateItem(this.getIndex()))
    }
  }

}
