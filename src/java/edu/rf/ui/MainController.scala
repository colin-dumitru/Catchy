package edu.rf.ui

import javafx.fxml.{Initializable, FXML}
import javafx.scene.layout.AnchorPane
import javafx.animation.TranslateTransition
import javafx.animation.Interpolator._
import javafx.util.{Callback, Duration}
import javafx.scene.input.MouseEvent
import javafx.scene.control.{ListCell, ListView}
import javafx.collections.{ObservableList, FXCollections}
import edu.rf.ui.listCells.FolderListCellScala
import edu.rf.model.{Model, Cell, Feed, Folder}
import java.net.URL
import java.util.ResourceBundle
import java.util

/**
 * Catalin Dumitru
 * Date: 6/15/13
 * Time: 5:13 PM
 */

class MainController extends Initializable {
  @FXML
  private val contentPane: AnchorPane = null

  @FXML
  private val folderList: ListView[Cell] = null

  @FXML
  private val modelCell: Model = new Model();

  final val listFolderFeeds: ObservableList[Cell] = FXCollections.observableArrayList(Folder.getFolders());


  def initialize(p1: URL, p2: ResourceBundle) {
    //initFolderList()
    modelCell.setFolders(listFolderFeeds)
  }


  def initFolderList() {
    val folders: util.ArrayList[Cell] = Folder.getFolders.asInstanceOf[util.ArrayList[Cell]];
    folderList.setItems(listFolderFeeds)
    //listFolderFeeds.addAll(folders);
    folderList.setCellFactory(new Callback[ListView[Cell],
      ListCell[Cell]]() {
      def call(folderList: ListView[Cell]): ListCell[Cell] = {
        val listOfFolders: FolderListCellScala = new FolderListCellScala();
        listOfFolders.init;
        listOfFolders;
      }
    }
    );
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

  def showFeedsForFolder(feeds: util.ArrayList[Feed], index: Int, noOfFeeds: Int) = {
    listFolderFeeds.addAll(index + 1, feeds)
  }

  def hideFeedsForFolder(start: Int, end: Int) = {
    listFolderFeeds.remove(start, end)
  }


}
