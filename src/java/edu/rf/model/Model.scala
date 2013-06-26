package edu.rf.model

import javafx.beans.property.{SimpleObjectProperty, ObjectProperty}
import javafx.collections.ObservableList

/**
 * Created with IntelliJ IDEA.
 * User: irina
 * Date: 6/26/13
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
class Model {
  val folders: ObjectProperty[ObservableList[Cell]] = new SimpleObjectProperty[ObservableList[Cell]](this, "list")

  final def getFolders(): ObservableList[Cell] = {
    folders.get()
  }

  final def setFolders(folderList: ObservableList[Cell]) {
    folders.set(folderList)
  }

  final def cellProperty(): ObjectProperty[ObservableList[Cell]] = {
    folders
  }


}
