package edu.catchy.model

import edu.catchy.R

/**
 * irina
 * Date: 6/26/13
 * Time: 6:48 PM
 */
case class Feed(id: Int, folderId: Int, title: String, url: String, showNotifications: Boolean) {
  def this(other: Feed) = this(other.id, other.folderId, other.title, other.url, other.showNotifications)
}

object Feed {
  def get(id: Int): Feed = {
    R.connection.query("SELECT * from feed WHERE id=?", id) {
      stmt => new Feed(stmt.columnInt(0), stmt.columnInt(1), stmt.columnString(2), stmt.columnString(3), stmt.columnInt(4) == 1)
    }.head
  }

  def update(feed: Feed) {
    R.connection.execute("UPDATE feed SET folder_id = ?, title = ?, url = ?, show_notifications = ? WHERE id = ?",
      feed.folderId, feed.title, feed.url, if (feed.showNotifications) 1 else 0, feed.id)
  }

  def getFeedByFolderId(folderId: Int): Seq[Feed] = {
    R.connection.query("SELECT * from feed WHERE folder_id=?", folderId) {
      stmt => new Feed(stmt.columnInt(0), stmt.columnInt(1), stmt.columnString(2), stmt.columnString(3), stmt.columnInt(4) == 1)
    }.toSeq
  }
}