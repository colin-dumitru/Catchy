package edu.rf.ui

import javafx.application.Application
import javafx.fxml.{JavaFXBuilderFactory, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.net.URL

/**
 * irina
 * Date: 6/22/13
 * Time: 6:48 PM
 */


object Main {

  private val location: URL = getLocation()
  private val fxmlLoader: FXMLLoader = getFXMLLoader()

  def main(args: Array[String]) {
    Application.launch(classOf[Main], args: _*)
  }

  private def getLocation(): URL = {
    getClass().getResource("/layout/trial.fxml")
  }

  private def getFXMLLoader(): FXMLLoader = {
    val fxmlLoader: FXMLLoader = new FXMLLoader()
    fxmlLoader.setLocation(location)
    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory())
    fxmlLoader
  }

  def getController(): MainController = {
    fxmlLoader.getController.asInstanceOf[MainController]
  }

  def getParent(): Parent = {
    fxmlLoader.load(location.openStream).asInstanceOf[Parent]
  }
}

class Main extends Application {

  override def start(primaryStage: Stage) {
    val root: Parent = Main.getParent()
    primaryStage.setTitle("RichFeed")
    primaryStage.setScene(new Scene(root, 300, 275))
    primaryStage.setMinWidth(500)
    primaryStage.setMinHeight(500)
    primaryStage.show
  }

}

