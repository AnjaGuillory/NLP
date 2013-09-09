package nlp.a0

import scala.io.Source
import scala.util.matching.Regex
import java.util.Arrays.sort

object WordCount {
	
	def countWords(file: String) = {
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
				newArr = newArr ++ Array(count(i).toLowerCase())
		}
	
		//Creat vector to count exact number of words
		//Create set to count exact number of distinct words
		//Create array of Tuples to store count of each word
		val countWords = Vector() ++ newArr
		val distWords = Set() ++ newArr
		var countArr = new Array[(String,Int)](0)
	
		println("Total number of words: " + countWords.size)
		println("Number of distinct words: " + distWords.size)
		println("Top 10 words: ")

		//Count number of times each word shows
		for(x <- distWords) 
		{
	
			var word = x
			var counter = (countWords count (_ == x))
			countArr = countArr ++ Array((x,counter))	
		}
		
		//Sort tuple by their maxes
		countArr = countArr.sortWith((e1: (String, Int), e2: (String, Int)) => e1._2 > e2._2)	
	
		//Print the top ten words and their corresponding counts
		if(countArr.length < 10)
		{
			for(i <- 0 to countArr.length-1)
			{
				var a = ((countArr(i)._2: Double)/countWords.size)*100
				println(countArr(i)._1 + " 	" + countArr(i)._2 + " 	" + a)
			}
		}	
		else
		{
			for(i <- 0 to 9)
			{
				var a = ((countArr(i)._2: Double)/countWords.size)*100
				println(countArr(i)._1 + " 	" + countArr(i)._2 + " 	" + a)
			}
		}	

	}


	def stopWords(file: String, nFile: String) = {
		//Create a string from your file
		val source = Source.fromFile(file)
		val lines = source.mkString
		
		val sourceStop = Source.fromFile(nFile)
		val stopString = sourceStop.mkString
		val stopWords = stopString.split("\\n") 

		for(i <- 0 to stopWords.length-1)
		{
			stopWords(i) = stopWords(i).toLowerCase()
			//println(stopWords(i))
		}
		//Create a regular expression to split file by
		//Find all patterns
		val pattern = new Regex("[A-Za-z]*")
		val words = (pattern findAllIn lines).mkString(",")
		val text = words.split(",")
		
		//Create a vector to hold list and size of all words
		//Create an array to lowercase all words for counting
		val count = Vector() ++ text
		var newArr = new Array[String](0)
		var allWords = new Array[String](0)
		for(i <- 0 to count.length-1) 
		{ 
			if(count(i) != "")
			{
				val newStr = count(i).toLowerCase()
				allWords = allWords ++ Array(newStr)
				if(stopWords.contains(newStr) == false)
				{
					newArr = newArr ++ Array(newStr)
					//println(newStr)
				}
			}
		}
	
		//Creat vector to count exact number of words
		//Create set to count exact number of distinct words
		//Create array of Tuples to store count of each word
		val countWords = Vector() ++ newArr
		val distWords = Set() ++ newArr
		val total = Vector() ++ allWords
		val dist = Set() ++ allWords
		var countArr = new Array[(String,Int)](0)
	
		println("Total number of words: " + total.size)
		println("Number of distinct words: " + dist.size)
		println("Top 10 words: ")

		//Count number of times each word shows
		for(x <- distWords) 
		{
	
			var word = x
			var counter = (countWords count (_ == x))
			countArr = countArr ++ Array((x,counter))	
		}
		
		//Sort tuple by their maxes
		countArr = countArr.sortWith((e1: (String, Int), e2: (String, Int)) => e1._2 > e2._2)	
	
		//Print the top ten words and their corresponding counts
		if(countArr.length < 10)
		{
			for(i <- 0 to countArr.length-1)
			{
				var a = ((countArr(i)._2: Double)/allWords.size)*100
				println(countArr(i)._1 + " 	" + countArr(i)._2 + " 	" + a)
			}
		}	
		else
		{
			for(i <- 0 to 9)
			{
				var a = ((countArr(i)._2: Double)/allWords.size)*100
				println(countArr(i)._1 + " 	" + countArr(i)._2 + " 	" + a)
			}
		}	

	}

	def main(args: Array[String]) = {

		if(args.length  == 3) {
			if(args(1) == "--stopwords")
			{
				//Make sure your file is existing file
				val file: String = args(0) match {
				case file => file
				case "" => sys.error("You didn't put a file")
				case _ => sys.error("You put too many files")
				}
				
				val nFile: String = args(2) match {
					case nFile => nFile
					case "" => sys.error("You didn't put a file")
					case _ => sys.error("You put too many files")
				}
				stopWords(file, nFile)
			}
		}
		else	{
			//Make sure your file is existing file
			val file: String = args(0) match {
				case file => file
				case "" => sys.error("You didn't put a file")
				case _ => sys.error("You put too many files")		
				}
		
				countWords(file)
		}
		
		
	}
		
}



