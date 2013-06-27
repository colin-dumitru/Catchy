package edu.catchy

import org.srhea.scalaqlite.SqliteDb

/**
 * irina
 * Date: 6/23/13
 * Time: 2:08 PM
 */
object R {
  val connection = new SqliteDb(getClass.getResource("/db/main.sqlite").getPath())
}
