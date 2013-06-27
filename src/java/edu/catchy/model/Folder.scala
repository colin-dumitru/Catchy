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
    R.connection.prepare[Iterator[Folder]]("SELECT * from folder") {
      stmt => stmt.query() {
        iter => iter.map(row => new Folder(row(0).toInt, row(1).toString))
      }
    }.toSeq
  }
}
