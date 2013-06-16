package edu.rf.ui

import javafx.fxml.FXML
import javafx.scene.layout.AnchorPane
import javafx.animation.TranslateTransition
import javafx.animation.Interpolator._
import javafx.util.Duration
import javafx.scene.input.MouseEvent

/**
 * Catalin Dumitru
 * Date: 6/15/13
 * Time: 5:13 PM
 */
class MainController {
  @FXML
  private val contentPane: AnchorPane = null

  def hoverSidePane(event: MouseEvent) {
    val transition = new TranslateTransition(Duration.millis(400), contentPane)

    transition.setToX(180.0)
    transition.setInterpolator(EASE_BOTH)

    transition.play()
  }

  def hoverSidePaneLeave(event: MouseEvent) {
    val transition = new TranslateTransition(Duration.millis(400), contentPane)

    transition.setToX(0.0)
    transition.setInterpolator(EASE_BOTH)

    transition.play()
  }


}
