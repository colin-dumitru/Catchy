package edu.catchy.ui.feedList.controller

import javafx.fxml.FXML
import javafx.scene.control.{TextField, Label}
import edu.catchy.ui.model.FeedCell
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.animation._
import javafx.util.Duration
import javafx.beans.value.WritableValue
import javafx.scene.shape.Rectangle
import edu.catchy.model.Feed

/**
 * Catalin Dumitru
 * Date: 6/29/13
 * Time: 1:53 PM
 */
class FeedCellController {
  private val ExpandedSize = 112.0
  private val CollapsedSize = 35.0

  private val optionMask: Rectangle = new Rectangle()
  private var feedId: Int = 0

  @FXML
  private val feedName: Label = null
  @FXML
  private val nameInput: TextField = null
  @FXML
  private val urlInput: TextField = null
  @FXML
  private val optionPane: AnchorPane = null
  @FXML
  private val feedRoot: AnchorPane = null

  def initDate(cell: FeedCell) {
    feedId = cell.id

    resetValues(Feed.get(feedId))
  }

  def resetValues(feed: Feed) {
    feedName.setText(feed.title)
    nameInput.setText(feed.title)
    urlInput.setText(feed.url)
  }

  def initialize() {
    resetOptionMask

    optionPane.setClip(optionMask)
  }


  def resetOptionMask {
    optionMask.setWidth(200)
    optionMask.setHeight(0)
    optionMask.setLayoutX(0)
    optionMask.setLayoutY(0)
  }

  def expandClicked(event: MouseEvent) {
    feedRoot.setMaxHeight(200)

    if (optionPane.isVisible) {
      hideOptions()
    } else {
      showOptions()
    }
  }

  def hideOptions() {
    val timeline = new Timeline

    //wtf scala
    timeline.getKeyFrames.addAll(
      new KeyFrame(Duration.seconds(0.3),
        new KeyValue(feedRoot.prefHeightProperty().asInstanceOf[WritableValue[Double]], CollapsedSize, Interpolator.EASE_BOTH),
        new KeyValue(optionMask.heightProperty().asInstanceOf[WritableValue[Double]], 0.0, Interpolator.EASE_BOTH),
        new KeyValue(optionPane.visibleProperty().asInstanceOf[WritableValue[Boolean]], false)
      )
    )
    timeline.play()
  }

  def showOptions() {
    val timeline = new Timeline

    //wtf scala
    timeline.getKeyFrames.addAll(
      new KeyFrame(Duration.seconds(0.3),
        new KeyValue(feedRoot.prefHeightProperty().asInstanceOf[WritableValue[Double]], ExpandedSize, Interpolator.EASE_BOTH),
        new KeyValue(optionMask.heightProperty().asInstanceOf[WritableValue[Double]], ExpandedSize - CollapsedSize, Interpolator.EASE_BOTH)
      )
    )
    timeline.play()
    optionPane.setVisible(true)
  }

  def saveClicked(event: MouseEvent) {
    val feed = new Feed(feedId, -1, nameInput.getText, urlInput.getText, false)
    feedName.setText(feed.title)
    Feed.update(feed)

    hideOptions()
  }

  def cancelClicked(event: MouseEvent) {
    resetValues(Feed.get(feedId))

    hideOptions()
  }
}
