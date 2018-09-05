package main

import java.io.File

import com.google.gson.Gson
import org.apache.commons.io.FileUtils

import scala.util._

case class Config (
  val markedFile: String,
  val backupDir: String,
  val chainFile: String,
)

object Config {

  def load: Either[String,Unit] = {
    val gson: Gson = new Gson
    (Try {
      val data = FileUtils.readFileToString(new File("config.txt"))
      val loaded: Config = gson.fromJson(data, this.getClass)
      this.apply(loaded.markedFile, loaded.backupDir, loaded.chainFile)
      Config.
    } match {
      case Failure(_) =>
        this.apply("marked.file", "backup_dir/", "chains.txt")
        println(this.)
        Try(FileUtils.writeStringToFile(new File("config.txt"), gson toJson this))
    }) match {
      case Success(_) => Right()
      case Failure(_) => Left("Config file is not created.")
    }
  }
}