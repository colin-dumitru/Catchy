package edu.rf.model

import edu.rf.db.DbConnection
import java.util
import edu.rf.ui.listCells.folder.FolderCell


/**

  */
case class Folder(id: Int, name: String) extends Cell {

  private val folderCell: FolderCell = generateFolderCell()

  def updateItem(index: Int): FolderCell = {
    folderCell.setIndex(index)
    folderCell.setId(this.id)
    folderCell.setName(this.name)
    folderCell
  }

  private def generateFolderCell(): FolderCell = {
    val cell = new FolderCell()
    cell.init
    cell
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
