package edu.rf.ui

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.event.ActionEvent
import com.sun.deploy.uitoolkit.impl.fx.ui.FXMessageDialog

/**
 * Catalin Dumitru
 * Date: 6/15/13
 * Time: 5:13 PM
 */
class MainController{
  @FXML
  private val someFucker:Button = null

  def handleClickAction(event:ActionEvent) {
    someFucker.setText("Fuck yea it works")
  }


}
