package edu.rf.ui.listCells

import edu.rf.model.{Feed, CellType, Folder}
import javafx.scene.control.{ListCell, Label}
import edu.rf.ui.listCells.folder.FolderCell


/**

  */
/**
 *
 */
class FolderListCellScala() extends ListCell[CellType]() {

  def init {
    addStyleClass
  }

  private def addStyleClass {
    this.getStyleClass.add("folder-list-cell")
  }

  protected override def updateItem(item: CellType, empty: Boolean) {

    super.updateItem(item, empty)
    if (empty) {
      setGraphic(null)
    }
    else {
      if (item.isInstanceOf[Folder]) {
        updateFolderItems(item, empty)
      }
      else {
        updateFeedsItems(item, empty)
      }
    }

  }

  private def updateFolderItems(item: CellType, empty: Boolean) {
    var folderCell: FolderCell = new FolderCell()
    folderCell.folderIndexInList = this.getIndex()
    folderCell.init
    if (item.asInstanceOf[Folder].getExpanding())
      folderCell.changeShowFeedsButton()
    folderCell.folderName.setText(if (item != null) item.asInstanceOf[Folder].name else "<null>")
    if (item != null)
      folderCell.folderId = item.asInstanceOf[Folder].id
    folderCell.noOfFeeds = item.asInstanceOf[Folder].getNoOfFeeds()
    setGraphic(folderCell)
  }

  private def updateFeedsItems(item: CellType, empty: Boolean) {
    // to-do create FeedCell class and manage styling there
    var feedName: Label = new Label();
    feedName.setStyle("-fx-background-color: white;")
    feedName.setText(item.asInstanceOf[Feed].title);
    setGraphic(feedName)

  }
}
