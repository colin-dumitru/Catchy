package edu.rf.model

import javafx.scene.Node

/**
 * Created with IntelliJ IDEA.
 * User: irina
 * Date: 6/24/13
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
trait Cell {
  val id: Int
  val name: String

  def updateItem(index: Int): Node
}
