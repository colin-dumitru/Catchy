package edu.rf.model

import javafx.beans.property.{SimpleObjectProperty, ObjectProperty}
import javafx.collections.{FXCollections, ObservableList}
import scala.beans.BeanProperty
import javafx.collections

/**
 * irina
 * Date: 6/26/13
 * Time: 6:48 PM
 */
class Model {
  @BeanProperty val folders: collections.ObservableList[Cell] = FXCollections.observableArrayList()
  val foldersProperty: ObjectProperty[ObservableList[Cell]] = new SimpleObjectProperty[ObservableList[Cell]](this, "folders")


}
