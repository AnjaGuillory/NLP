package nlp.a3

import nlpclass.NgramModelToImplement
import scala.math._

class NgramModel(
	val n: Int,
	ngramProbs: NgramCondProbDistr[Vector[String], String])
	extends NgramModelToImplement {
	
	def ngram: Int = n
	
	def sentenceProb(sentenceTokens: Vector[String]): Double = {
		val sentence = appendS(sentenceTokens)
		val tokens = Vector() ++ sentence.sliding(n)
		val probability = {
			var totalProb = 0.0
			for(i <- 0 to tokens.length-1) {
				val gram = tokens(i)
				val word = gram.last
				val condWords = gram.dropRight(1)
				totalProb = totalProb + math.log(ngramProbs(word,condWords))
			}
			totalProb
		}
		probability
		
	}

	def generate(): Vector[String] = {
		var generatedString = Vector[String]()
		if(n>1)
		{
			generatedString = Vector.fill(n-1)("<s>")
			//println(randomString)
			generatedString = generatedString ++ Vector(ngramProbs.sample(generatedString))
			println(generatedString)
			var newGen = ngramProbs.sample(generatedString.takeRight(n-1))
			while(!(newGen == "</s>"))
			{
				generatedString = generatedString ++ Vector(newGen)
				newGen = ngramProbs.sample(generatedString.takeRight(n-1))
			}
					generatedString.filter(_ != "<s>" )


		}
		else
		{
			var newGen = ngramProbs.sample(Vector())
			for(i <- 0 to 9 if newGen != "</s>")
			{
				generatedString = generatedString ++ Vector(newGen)
				newGen = ngramProbs.sample(Vector())
			}
					generatedString

		}
	}

	def appendS(sentence: Vector[String]): Vector[String] = {
		var starts = Vector[String]()
		for(i <- 0 until n-1)
		{
			starts = starts ++ Vector("<s>")
		}
		starts ++ sentence
	}
}

