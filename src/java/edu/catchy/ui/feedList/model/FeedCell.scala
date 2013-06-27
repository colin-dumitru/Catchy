package edu.catchy.ui.model

import javafx.scene.control.Label
import edu.catchy.ui.feedList.model.Listable
import javafx.scene.Node

/**
 * irina
 * Date: 6/24/13
 * Time: 6:28 PM
 */
trait FeedCell extends Listable {
  def name: String

  def getRoot: Node = {
    val rootLabel: Label = new Label()
    rootLabel.setStyle("-fx-background-color: white;")
    rootLabel.setText(this.name)
    rootLabel
  }
}
