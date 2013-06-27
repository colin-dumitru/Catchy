package edu.catchy.model

import edu.catchy.R


/**
 * irina
 * Date: 6/26/13
 * Time: 6:48 PM
 */
case class Folder(id: Int, name: String) {
  def this(other: Folder) = this(other.id, other.name)
}

object Folder {

  def getAll: Seq[Folder] = {
    R.connection.query("SELECT * from folder") {
      stmt => new Folder(stmt.columnInt(0), stmt.columnString(1))
    }.toSeq
  }
}
