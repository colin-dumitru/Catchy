package edu.catchy.ui.model

import javafx.scene.layout.HBox
import javafx.scene.control.{Label, Button}
import java.net.URL
import javafx.scene.image.{ImageView, Image}
import javafx.event.{EventHandler, ActionEvent}
import edu.catchy.model.Feed
import edu.catchy.ui.feedList.model.Listable
import javafx.scene.Node

/**
 * irina
 * Date: 6/26/13
 * Time: 6:48 PM
 */
trait FolderCell extends Listable {

  def id: Int

  def name: String

  private val showFeedsButton: Button = new Button("+")
  private val optionsFeedButton: Button = new Button
  private val nameLabel: Label = new Label

  def init() {
    addStyling()
    addActions()
  }

  private def addStyling() {
    addCellStyleClass()
    addExpandButtonStyleClass()
    addFolderLabelStyleClass()
    addOptionsButtonStyleClass()
  }

  private def addCellStyleClass() {
    // this.getStyleClass.add("hbox-cell")
  }

  private def addExpandButtonStyleClass() {
    showFeedsButton.getStyleClass.add("expand-folder-button")
  }

  private def addFolderLabelStyleClass() {
    nameLabel.setMinWidth(145)
    nameLabel.setMaxWidth(145)
    nameLabel.setText(name)
  }

  private def addOptionsButtonStyleClass() {
    optionsFeedButton.getStyleClass.add("options-folder-button")
    val path: URL = getClass.getResource("/img/rightArrow.png")
    val rightArrowImg: Image = new Image(path.toString)
    optionsFeedButton.setGraphic(new ImageView(rightArrowImg))
  }


  private def addActions() {
    addShowFeedsButtonAction()
  }

  private def addShowFeedsButtonAction() {
    showFeedsButton.setOnAction(new EventHandler[ActionEvent]() {
      override def handle(e: ActionEvent) {
        if (showFeedsButton.getText.equals("+")) {
          val feeds = Feed.getFeedByFolderId(id)
          val noOfFeeds = feeds.size
          //crw you need to rewrite this so that you do not have a reverse dependency to the controller
          // //Main.getController().showFeedsForFolder(feeds, folderIndexInList, feeds.size())
          showFeedsButton.setText("-")
        }
        else {
          showFeedsButton.setText("+")
          //same here
          //Main.getController().hideFeedsForFolder(folderIndexInList + 1, folderIndexInList + 1 + noOfFeeds)
        }
      }
    })
  }

  def changeShowFeedsButton() {
    showFeedsButton.setText("-")
  }

  def getRoot: Node = {
    init()

    val hbox = new HBox()
    hbox.getChildren.addAll(showFeedsButton, nameLabel, optionsFeedButton)
    hbox
  }
}
