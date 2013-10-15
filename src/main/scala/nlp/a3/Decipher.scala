package nlp.a3

import nlpclass._

object Decipher extends DecipherToImplement {

	def train(text: Vector[Vector[String]], n: Int, lambda: Double): NgramModelToImplement = 
	{	
		//make file set and get size for lambda
		val size = {
					var letters = Set[String]()
				for(sentence <- text)
					{
						for(word <- sentence)
						{
							for(letter <- word)
							{
								letters = letters ++ Set(letter.toString)
							}
						}
					}
					letters
		}
		//println(size)
		val defDistr = {
			var lettermap = scala.collection.mutable.Map[String,Int]()
			for(sentence <- text)
			{
				for(word <- sentence)
				{
					for(letter <- word)
					{
						lettermap = lettermap + ((letter.toString) -> 0)
					}
				}
			}

			lettermap.toMap
		}

		val mainDistr = {
			var mainmap = Map[Vector[String], NgramProbDistr[String]]()
			for(sentence <- text)
			{
				for(word <- sentence)
				{
					val grammedSentence = word.sliding(n).toVector
					for(gram <- grammedSentence)
					{
						val pregram = {
							var setup = Vector[String]()
							for(i <- 0 until n-2)
							{
								setup = setup ++ Vector(gram(i).toString)
							}

							setup
						}
						if(mainmap.contains(pregram) == false)
						{
							mainmap = mainmap + (pregram -> new NgramProbDistr[String](Map[String,Int](),lambda,size.size))
						}	
					
						mainmap(pregram).update((gram.last).toString, 1)

					}
				}
			}
			mainmap
		}		
		//println(size.size)

		val cpd = new NgramCondProbDistr[Vector[String],String](mainDistr, new NgramProbDistr[String](defDistr,lambda,size.size))

		val ngm = new NgramModel(n, cpd)
		ngm
	}

	def swapLetters(i: Int, j: Int, cipherKey: Vector[String]): Vector[String] = 
	{
		var tempcipher = cipherKey.toArray
		val firstletter = tempcipher(i)
		val secondletter = tempcipher(j)
		val temp = firstletter

		tempcipher(i) = secondletter.toString
		tempcipher(j) = firstletter.toString

		return tempcipher.toVector
	}

	def scoreCipherKey(cipherText: Vector[Vector[String]], cipherKey: Vector[String], ngramModel: NgramModelToImplement): Double = {
		val deciphered = decipher(cipherKey,cipherText)
		return PerplexityNgramModelEvaluator(ngramModel,deciphered)
	}
}