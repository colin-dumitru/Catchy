package edu.catchy.db

import com.almworks.sqlite4java.{SQLiteStatement, SQLiteConnection}
import java.io.File
import scala.collection.mutable

/**
 * Catalin Dumitru
 * Date: 6/27/13
 * Time: 8:38 PM
 */
class Sqlite3 {
  val connection = new SQLiteConnection(new File(getClass.getResource("/db/main.sqlite").getPath)).open(true)

  def query[R](query: String, params: Any*)(f: (SQLiteStatement) => R): Seq[R] = {
    var ret = new mutable.MutableList[R]()
    val statement = connection.prepare(query)

    bindParameters(statement, params)

    while (statement.step()) {
      ret += f(statement)
    }
    ret.toSeq
  }


  def bindParameters[R](statement: SQLiteStatement, params: Seq[Any]) {
    for (param <- params.zipWithIndex) param._1 match {
      case p: String => statement.bind(param._2, p)
      case p: Double => statement.bind(param._2, p)
      case p: Long => statement.bind(param._2, p)
      case p: Int => statement.bind(param._2, p)
      case _ => throw new RuntimeException("Unsupported parameter type: " + param._1.getClass.getName)
    }
  }

  override def finalize() {
    super.finalize()
    connection.dispose()
  }
}
