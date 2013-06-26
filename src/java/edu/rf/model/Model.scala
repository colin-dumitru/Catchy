package edu.rf.model

import javafx.beans.property.{SimpleObjectProperty, ObjectProperty}
import javafx.collections.{FXCollections, ObservableList}
import scala.beans.BeanProperty
import javafx.collections

/**
 * Created with IntelliJ IDEA.
 * User: irina
 * Date: 6/26/13
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
class Model {
  @BeanProperty val folders: collections.ObservableList[Cell] = FXCollections.observableArrayList()
  val foldersProperty: ObjectProperty[ObservableList[Cell]] = new SimpleObjectProperty[ObservableList[Cell]](this, "folders")


}
