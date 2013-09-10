package nlp.a0

import nlp.a0.NGramCountingToImplement

class NGramCounting(n: Int) extends nlp.a0.NGramCountingToImplement {

	def countNGrams(tokens: Vector[String]): Map[Vector[String], Int] = {
		//tokens is the file made into a whole vector
		
		var strMap = collection.immutable.Map[Vector[String], Int]()

		val strVect = tokens.sliding(n)
		val allGrams = Vector() ++ strVect
		val strSet = Set() ++ allGrams
		
		//println(strSet)

		for(x <- strSet)
		{
			val vect = x
			//println(x)
			//println(allGrams count (_==x))
			strMap += x -> (allGrams count (_==x))
		}		

		return strMap

		
	
  	}

}



