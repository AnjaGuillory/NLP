package nlp.a0

import nlp.a0.NGramCounting
import scala.io.Source
import scala.util.matching.Regex

object CountTrigrams {
	def main(args: Array[String]) = {
		val file: String = args(0) match {
				case file => file
				case "" => sys.error("You didn't put a file")
				case _ => sys.error("You put too many files")		
				}
			countTri(file)
		}

	def countTri(file: String) = {

		//Create a string from your file
		val source = Source.fromFile(file)
		val lines = source.mkString

		//Create a regular expression to split file by
		//Find all patterns
		val pattern = new Regex("[A-Za-z]*")
		val words = (pattern findAllIn lines).mkString(",")
		val text = words.split(",")
		val vect = Vector() ++ text
		var newArr = new Array[String](0)

		for(i <- 0 to vect.length-1)
		{
			if(vect(i) != "")
			{
				newArr = newArr ++ Array(vect(i).toLowerCase())
			}
		}
		
		//Create a vector to hold list and size of all words
		//Create an array to lowercase all words for counting
		val vectText = Vector() ++ newArr
		//println(vectText)

		val ngram = new NGramCounting(3).countNGrams(vectText)
		val nGramMap = ngram.toSeq.sortWith(_._2 > _._2)
		var i = 0
		for((key,value) <- nGramMap if i != 10) {println(key.mkString(" ") + " 	" + value); i = i + 1;}
		//nGramMap foreach {case (key, value) => println(key.mkString(" ") + " 	" + value)}

		
	}
}
