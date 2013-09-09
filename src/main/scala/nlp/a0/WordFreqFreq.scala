package nlp.a0

import scala.io.Source
import scala.util.matching.Regex
import java.util.Arrays.sort

object WordFreqFreq {

	def freqCount(file: String) = {
		//Create a string from your file
		val source = Source.fromFile(file)
		val lines = source.mkString
		
		//Create a regular expression to split file by
		//Find all patterns
		val pattern = new Regex("[A-Za-z]*")
		val words = (pattern findAllIn lines).mkString(",")
		val text = words.split(",")
		
		//Create a vector to hold list and size of all words
		//Create an array to lowercase all words for counting
		val count = Vector() ++ text
		var newArr = new Array[String](0)

		for(i <- 0 to count.length-1) 
		{ 
			if(count(i) != "")
			{
				val newStr = count(i).toLowerCase()
				newArr = newArr ++ Array(newStr)
				
			}
		}
	
		//Creat vector to count exact number of words
		//Create set to count exact number of distinct words
		//Create array of Tuples to store count of each word
		val countWords = Vector() ++ newArr
		val distWords = Set() ++ newArr

		var countArr = new Array[Int](0)
	
		var freqFreq = new Array[(Int, Int)](0)

		//Count number of times each word shows
		for(x <- distWords) 
		{
	
			var word = x
			var counter = (countWords count (_ == x))
			countArr = countArr ++ Array(counter)	
		}
		
		for(i <- 0 to countArr.length-1)
		{
			//println(countArr(i))
		}
		
		var freqPairs = new Array[(Int,Int)](0)
		for(i <- 0 to countArr.length-1)
		{
			//Num is word frequency
			//counter is frequency frequency
			var num = countArr(i)
			var counter = (countArr count (_ == num))
			freqPairs = freqPairs ++ Array((num,counter))
			//println(freqPairs(i))
			
		}


		val freqSet = Set() ++ freqPairs
		var freqVect = Vector() ++ freqSet

		freqVect = freqVect.sortWith((e1: (Int, Int), e2: (Int, Int)) => (e1._2 > e2._2))

		println("Top 10 most frequent frequencies: ")		

		for(i <- 0 to 9)
		{
			val x = freqVect(i)
			println(x._2 + " words appears " + x._1 + " times")
		}
		

		println("Bottom 5 most frequent frequencies: ")
			
		for(i <- 1 to 5)
		{
			val x = freqVect(freqVect.size-i)
	
			
				println(x._2 + " words appears " + x._1 + " times")
			
		}

	}


	def main(args: Array[String]) = {
		val file: String = args(0) match {
				case file => file
				case "" => sys.error("You didn't put a file")
				case _ => sys.error("You put too many files")		
				}
		
				freqCount(file)
	}
}
