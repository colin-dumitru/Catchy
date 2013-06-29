package edu.catchy.ui.feedList.model

import javafx.scene.control.ListCell

/**
 * Catalin Dumitru
 * Date: 6/27/13
 * Time: 7:20 AM
 */
class FeedListCell extends ListCell[Listable] {
  override def updateItem(item: Listable, empty: Boolean) {
    super.updateItem(item, empty)

    if (!empty) {
      setGraphic(item.getRoot)
    }
  }
}
