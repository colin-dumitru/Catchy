package edu.rf.model

import javafx.scene.Node
import javafx.beans.property.SimpleStringProperty

/**
 * Created with IntelliJ IDEA.
 * User: irina
 * Date: 6/24/13
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
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
