package edu.catchy.ui.feedList

import javafx.fxml.FXML
import javafx.scene.control.{ListCell, ListView}
import edu.catchy.ui.feedList.model.{FeedListCell, Listable, FeedListContext}
import edu.catchy.model.Folder
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
  private val context: FeedListContext = null

  final val feedList = Folder.getAll map (folder => new Folder(folder) with FolderCell)

  def initialize() {
    initFolderList()
    context.feedList.addAll(seqAsJavaList(feedList))
  }

  def initFolderList() {
    folderList.setCellFactory(new Callback[ListView[Listable], ListCell[Listable]]() {
      def call(folderList: ListView[Listable]): ListCell[Listable] = {
        new FeedListCell()
      }
    })
  }
}
