package edu.rf.db

import org.srhea.scalaqlite.SqliteDb

/**
 * irina
 * Date: 6/23/13
 * Time: 2:08 PM
 */
object DbConnection {
  val db = new SqliteDb(getClass.getResource("/db/main.sqlite").getPath)
}
