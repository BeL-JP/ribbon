package main

import java.io._

object Ribbon {
  val command = ""

  def main(args: Array[String]): Unit = {
    println("Ribbon ver 0.1.0")
    Config.load match {
      case Left (x) =>
        println(x)
      case Right(_) =>
        (for{
          a <- validateArg(args)
          err <- argParser(a)
        } yield err) match {
          case Left(x) =>
            println(x)
            showHelp
          case Right(_) =>{

          }
        }
    }
  }

  private def validateArg(args: Array[String]): Either[String,Array[String]] = {
    if (args.length == 0)
      Left("Command invalid.")
    else
      Right(args)
  }

  private def argParser(args: Array[String]): Either[String,Unit] = {
    args(0) match {
      case "snapshot" | "-s" => Right()
      case "restore" | "-r" => Right()
      case "help" | "-h" => Right(showHelp)
      case "daemon_run" => Right()
      case x => Left(s"Command invalid: $x")
    }
  }

  private def showHelp: Unit = {
    using(new BufferedReader(new InputStreamReader(
      getClass.getClassLoader.getResourceAsStream("help.txt")))) {
      lines => lines.foreach(println)
    }
  }

  private def using[A](br: BufferedReader)(body: Iterator[String] => A): A = {
    try {
      body(Iterator.continually(br.readLine).takeWhile(_ != null))
    } finally {
      br.close
    }
  }
}