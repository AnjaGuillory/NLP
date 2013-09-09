package nlp.a0

trait NGramCountingToImplement {

  /**
   * Given a vector of tokens, return a mapping from ngrams 
   * to their counts.
   */
  def countNGrams(tokens: Vector[String]): Map[Vector[String], Int]

}

class NGramCounting(n: Int) extends NGramCountingToImplment {

	def countNGrams(tokens: Vector[String]): Map[Vector[String], Int] = {
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
	
		for(i <- 0 to count.length-3) 
		{ 
			if(count(i) != "" && count(i+1) != "" && count(i+2) != "") 
				newArr = newArr ++ Array(count(i).toLowerCase(),count(i+1).toLowerCase(),count(i+2).toLowerCase())
			i = i + 3
		}
	
		//Creat vector to count exact number of words
		//Create set to count exact number of distinct words
		//Create array of Tuples to store count of each word
		val countWords = Vector() ++ newArr
		val distWords = Set() ++ newArr
		var countArr = new Array[(String,Int)](0)

		//Count number of times each word shows
		for(x <- distWords) 
		{
	
			var word = x
			var counter = (countWords count (_ == x))
			countArr = countArr ++ Array((x,counter))	
		}
		
		//Sort tuple by their maxes
		countArr = countArr.sortWith((e1: (String, Int), e2: (String, Int)) => e1._2 > e2._2)

		for(i <- 0 to countArr.length-1)
		{
			val x = countArr(i)
			println(x._1 + " 	" + x._2)
		}	
	
		}
	}


	def main(args: Array[String]) = {

		//Make sure your file is existing file
		val file: String = args(0) match {
			case file => file
			case "" => sys.error("You didn't put a file")
			case _ => sys.error("You put too many files")		
			}
		
				countWords(file)
		}
}


