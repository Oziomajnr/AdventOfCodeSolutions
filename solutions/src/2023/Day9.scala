// For part1 just remove teh reverse on lines 6
object Main {
  def main(args: Array[String]): Unit = {
    val input = scala.io.Source.fromFile("src/input.txt").mkString.trim
    println(input.split("\n").map { line =>
      interpolate(line.split(" ").map(_.toInt).reverse)
    }.sum)
  }

  def interpolate(input: Array[Int]): Int = {
    if (input.length == 1) input(0)
    else input.last + interpolate(
      input.drop(1).zipWithIndex.map { case (value, index) =>
        value - input(index)
      })
  }
}
