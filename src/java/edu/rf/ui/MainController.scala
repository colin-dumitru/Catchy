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
import edu.rf.model.{Feed, CellType, Folder}
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
  private val folderList: ListView[CellType] = null

  final val listFolderFeeds: ObservableList[CellType] = FXCollections.observableArrayList(new util.ArrayList[CellType]);


  def initialize(p1: URL, p2: ResourceBundle) {
    initFolderList()
  }


  def initFolderList() {
    val folders: util.ArrayList[CellType] = Folder.getFolders.asInstanceOf[util.ArrayList[CellType]];
    folderList.setItems(listFolderFeeds)
    listFolderFeeds.addAll(folders);
    folderList.setCellFactory(new Callback[ListView[CellType],
      ListCell[CellType]]() {
      def call(folderList: ListView[CellType]): ListCell[CellType] = {
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
    listFolderFeeds.get(index).asInstanceOf[Folder].setNoOfFeeds(noOfFeeds)
    listFolderFeeds.get(index).asInstanceOf[Folder].setExpanding(true);
  }

  def hideFeedsForFolder(start: Int, end: Int) = {
    listFolderFeeds.remove(start, end)
    listFolderFeeds.get(start - 1).asInstanceOf[Folder].setExpanding(false)
  }


}
