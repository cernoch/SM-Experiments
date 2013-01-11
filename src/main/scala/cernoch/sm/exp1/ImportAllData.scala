package cernoch.sm.exp1

import cernoch.sm.sql._

/**
 *
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
object ImportAllData {

  def main(args: Array[String]) = {

    for ((name,(schema,data)) <- Datasets.all) {
      println("Importing '" + name + "'")

      val importer
      = new SqlStorage(
          new MysqlConnection(
            user = "sm", pass = "sm", dtbs = "sm",
            pfix = name.toUpperCase + "_"
          ),
          schema toList
        ).reset

      data.par.foreach(importer.put)
      importer.close

      println("Dataset '" + name + "' imported.")
    }
  }
}