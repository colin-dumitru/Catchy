package edu.rf.model

import javafx.scene.Node
import javafx.beans.property.SimpleStringProperty

/**
 * irina
 * Date: 6/26/13
 * Time: 6:48 PM
 */
trait Cell {

  var idCell: SimpleStringProperty
  var nameCell: SimpleStringProperty

  def setIdCell(id: String) = {
    this.idCell.set(id)
  }

  def getIdCell(): String = this.idCell.get()

  def setNameCell(name: String) = {
    this.nameCell.set(name)
  }

  def getNameCell(): String = nameCell.get()

  def updateItem(index: Int): Node

}
