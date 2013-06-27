package edu.catchy.model

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
    R.connection.query("SELECT * from feed WHERE folder_id=?", folderId) {
      stmt => new Feed(stmt.columnInt(0), stmt.columnInt(1), stmt.columnString(2), stmt.columnString(3), stmt.columnInt(4) == 1)
    }.toSeq
  }
}