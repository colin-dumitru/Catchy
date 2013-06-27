package edu.catchy.model

import org.srhea.scalaqlite.SqlInt
import edu.catchy.R

/**
 * irina
 * Date: 6/26/13
 * Time: 6:48 PM
 */
case class Feed(id: Int, folderId: Int, name: String, url: String, showNotifications: Boolean) {
}

object Feed {
  def getFeedByFolderId(folderId: Int): Seq[Feed] = {
    //crw never construct SQL queries manually as it can lead to dependency injection, always use prepared statements
    //with parameters
    R.connection.query[Iterator[Feed]]("SELECT * from feed WHERE folder_id=" + SqlInt(folderId) + ";") {
      iter => iter.map(row => new Feed(row(0).toInt, row(1).toInt, row(2).toString, row(3).toString, row(4).toInt == 1))
    }.toSeq
  }
}