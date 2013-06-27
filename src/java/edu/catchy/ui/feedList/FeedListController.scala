package edu.catchy.ui.feedList

import javafx.fxml.FXML
import javafx.scene.control.{ListCell, ListView}
import edu.catchy.ui.feedList.model.{FeedListCell, Listable, FeedListContext}
import java.util
import edu.catchy.model.{Folder, Feed}
import java.net.URL
import java.util.ResourceBundle
import javafx.util.Callback
import edu.catchy.ui.model.FolderCell
import scala.collection.JavaConversions._

/**
 * Catalin Dumitru
 * Date: 6/26/13
 * Time: 9:43 PM
 */
class FeedListController {
  @FXML
  private val folderList: ListView[Listable] = null
  @FXML
  private val context: FeedListContext = new FeedListContext()

  //crw you will need to move this entire logic into a new service
  final val feedList = Folder.getAll map (folder => new Folder(folder) with FolderCell)

  def initialize(url: URL, resourceBundle: ResourceBundle) {
    initFolderList()
    context.folders.addAll(seqAsJavaList(feedList))
  }


  def initFolderList() {
    val folders: util.ArrayList[Listable] = Folder.getAll.asInstanceOf[util.ArrayList[Listable]]

    //listFolderFeeds.addAll(folders);
    folderList.setCellFactory(new Callback[ListView[Listable], ListCell[Listable]]() {
      def call(folderList: ListView[Listable]): ListCell[Listable] = {
        new FeedListCell()
      }
    }
    )
  }

  def showFeedsForFolder(feeds: util.ArrayList[Feed], index: Int, noOfFeeds: Int) {
    //listFolderFeeds.addAll(index + 1, feeds)
  }

  def hideFeedsForFolder(start: Int, end: Int) = {
    feedList.remove(start, end)
  }

}
