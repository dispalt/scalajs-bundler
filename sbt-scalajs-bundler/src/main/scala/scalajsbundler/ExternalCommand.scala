package scalajsbundler

import sbt._

import scalajsbundler.util.Commands

/**
  * Attempts to smoothen platform-specific differences when invoking commands.
  *
  * @param name Name of the command to run
  */
class ExternalCommand(name: String) {

  /**
    * Runs the command `cmd`
    * @param args Command arguments
    * @param workingDir Working directory of the process
    * @param logger Logger
    */
  def run(args: String*)(workingDir: File, logger: Logger): Unit =
    Commands.run(cmd ++: args, workingDir, logger)

  val cmd =
    sys.props("os.name").toLowerCase match {
      case os if os.contains("win") => Seq("cmd", "/c", name)
      case _ => Seq(name)
    }

}

object Npm extends ExternalCommand("npm")

object Yarn extends ExternalCommand("yarn")
