package edu.catchy.ui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.layout.Pane
import javafx.scene.text.Font

/**
 * irina
 * Date: 6/22/13
 * Time: 6:48 PM
 */

object Main {
  def main(args: Array[String]) {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application {


  override def init() {
    super.init()

    Font.loadFont(getClass.getResource("/font/tilium.otf").toExternalForm, 12)
  }

  @Override
  def start(primaryStage: Stage) {
    primaryStage.setTitle("Catchy")
    val myPane: Pane = FXMLLoader.load(getClass.getResource("/layout/main.fxml"))
    val myScene: Scene = new Scene(myPane)
    primaryStage.setScene(myScene)
    primaryStage.show()
  }
}
