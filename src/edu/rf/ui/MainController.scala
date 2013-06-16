package edu.rf.ui

import javafx.fxml.FXML
import javafx.scene.control.{TextField, Button}
import javafx.event.ActionEvent
import edu.rf.notification.Notifier

/**
 * Catalin Dumitru
 * Date: 6/15/13
 * Time: 5:13 PM
 */
class MainController {
  @FXML
  private val someFucker: Button = null
  @FXML
  private val someFuckingInput: TextField = null

  def handleClickAction(event: ActionEvent) {
    Notifier.displayNotification("Does this work?", someFuckingInput.getText)
    someFucker.setText("Fuck yea it works")
  }


}
