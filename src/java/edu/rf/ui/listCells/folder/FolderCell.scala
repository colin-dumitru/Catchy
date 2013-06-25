package edu.rf.ui.listCells.folder

import javafx.scene.layout.HBox
import javafx.scene.control.{Label, Button}
import java.net.URL
import javafx.scene.image.{ImageView, Image}
import javafx.event.{EventHandler, ActionEvent}
import edu.rf.model.Feed
import edu.rf.ui.Main

/**

  */
class FolderCell extends HBox {

  def init {
    this.getChildren.addAll(showFeedsButton, folderName, optionsFeedButton)
    addStyling
    addActions
  }

  private def addStyling {
    addCellStyleClass
    addExpandButtonStyleClass
    addFolderLabelStyleClass
    addOptionsButtonStyleClass
  }

  private def addCellStyleClass {
    this.getStyleClass.add("hbox-cell")
  }

  private def addExpandButtonStyleClass {
    showFeedsButton.getStyleClass.add("expand-folder-button")
  }

  private def addFolderLabelStyleClass {
    folderName.setMinWidth(145)
    folderName.setMaxWidth(145)
  }

  private def addOptionsButtonStyleClass {
    optionsFeedButton.getStyleClass.add("options-folder-button")
    val path: URL = getClass.getResource("/img/rightArrow.png")
    val rightArrogImg: Image = new Image(path.toString)
    optionsFeedButton.setGraphic(new ImageView(rightArrogImg))
  }


  private def addActions() {
    addShowFeedsButtonAction
  }

  private def addShowFeedsButtonAction() {
    showFeedsButton.setOnAction(new EventHandler[ActionEvent]() {
      override def handle(e: ActionEvent) = {
        if (showFeedsButton.getText.equals("+")) {
          val feeds = Feed.getFeedByFolder(folderId)
          Main.getController().showFeedsForFolder(feeds, folderIndexInList, feeds.size())
          showFeedsButton.setText("-")
        }
        else {
          showFeedsButton.setText("+")
          Main.getController().hideFeedsForFolder(folderIndexInList + 1, folderIndexInList + 1 + noOfFeeds)
        }
      }
    })
  }

  def changeShowFeedsButton() {
    showFeedsButton.setText("-");
  }

  private[listCells] var showFeedsButton: Button = new Button("+")
  private[listCells] var optionsFeedButton: Button = new Button
  private[listCells] var folderName: Label = new Label
  private[listCells] var folderId: Int = 0;
  private[listCells] var folderIndexInList: Int = 0;
  private[listCells] var noOfFeeds: Int = 0;
}
