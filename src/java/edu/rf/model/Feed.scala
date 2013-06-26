package edu.rf.model

import java.util
import edu.rf.db.DbConnection
import org.srhea.scalaqlite.SqlInt
import javafx.scene.control.Label
import javafx.beans.property.SimpleStringProperty

/**
 * Created with IntelliJ IDEA.
 * User: irina
 * Date: 6/24/13
 * Time: 6:14 PM
 * To change this template use File | Settings | File Templates.
 */
case class Feed(id: Int, folderId: Int, name: String, url: String, showNotifications: Int) extends Cell {
  override def updateItem(index: Int): Label = {
    var feedName: Label = new Label();
    feedName.setStyle("-fx-background-color: white;")
    feedName.setText(this.name);
    feedName
  }

  var idCell: SimpleStringProperty = null
  var nameCell: SimpleStringProperty = null
}

object Feed {
  def getFeedByFolder(folderId: Int): util.ArrayList[Feed] = {
    val feeds = new util.ArrayList[Feed]
    DbConnection.db.foreachRow("SELECT * from feed WHERE folder_id=" + SqlInt(folderId) + ";") {
      row => feeds.add(new Feed(row(0).toInt, row(1).toInt, row(2).toString, row(3).toString, row(4).toInt))
    }
    feeds
  }
}