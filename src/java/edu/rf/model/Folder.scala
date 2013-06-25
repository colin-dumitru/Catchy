package edu.rf.model

import edu.rf.db.DbConnection
import java.util


/**

  */
case class Folder(id: Int, name: String) extends CellType {
  private var noOfFeeds = 0
  private var expanding = false;

  def setNoOfFeeds(feeds: Int) {
    noOfFeeds = feeds
  }

  def getNoOfFeeds(): Int = {
    noOfFeeds
  }

  def setExpanding(value: Boolean) {
    expanding = value
  }

  def getExpanding(): Boolean = {
    expanding
  }
}

object Folder {

  def getFolders(): util.ArrayList[Folder] = {
    var folders: util.ArrayList[Folder] = new util.ArrayList[Folder]()
    DbConnection.db.foreachRow("SELECT * from folder") {
      row => folders.add(new Folder(row(0).toInt, row(1).toString))
    }
    folders
  }
}
