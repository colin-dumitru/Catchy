package edu.rf.db

import org.srhea.scalaqlite.SqliteDb

/**
 * Created with IntelliJ IDEA.
 * User: irina
 * Date: 6/23/13
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
object DbConnection {
  val db = new SqliteDb(getClass.getResource("/db/main.sqlite").getPath)
}
