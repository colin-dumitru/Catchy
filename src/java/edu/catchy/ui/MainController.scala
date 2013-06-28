package edu.catchy.ui

import javafx.fxml.{Initializable, FXML}
import javafx.scene.layout.AnchorPane
import javafx.animation.TranslateTransition
import javafx.animation.Interpolator._
import javafx.util.Duration
import javafx.scene.input.MouseEvent
import java.net.URL
import java.util.ResourceBundle

/**
 * Catalin Dumitru
 * Date: 6/15/13
 * Time: 5:13 PM
 */

class MainController extends Initializable {
  @FXML
  private val contentPane: AnchorPane = null


  def initialize(p1: URL, p2: ResourceBundle) {
  }

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
