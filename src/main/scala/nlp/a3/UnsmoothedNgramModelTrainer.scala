package nlp.a3

import nlpclass.NgramModelTrainerToImplement
import nlpclass.NgramModelToImplement

class UnsmoothedNgramModelTrainer(n: Int) extends NgramModelTrainerToImplement {
	
	def lowercase(v: Vector[String]): Vector[String] = {
		var lowercases = Vector[String]()
		for(string <- v)
		{
			lowercases = lowercases ++ Vector(string.toLowerCase)
		}
		lowercases
	}

	def train(tokenizedSentences: Vector[Vector[String]]): NgramModelToImplement = {

		//vector is vector of sentences!
		//create CPD where each n-1 vector points to a word at n that points to a int
		// cpd(map[Vector[String], PD(Map[String, Int])])
		val starttags = {
			var tags = Vector[String]()
			for(i <- 0 to n-2)
			{
				tags = tags ++ Vector("<s>")
			}
			tags
		}

//must take the sentences to add <s> as a conditional in the CPD
		val taggedSentences = {
			var tagged = Vector[Vector[String]]() 
			for(tokenizedSentence <- tokenizedSentences)
			{
				tagged = tagged ++ Vector(starttags ++ tokenizedSentence ++ Vector("</s>"))
			}	
			tagged
		}
		
		//make file set and get size for lambda

		val mainDistr = {
			var mainmap = Map[Vector[String], NgramProbDistr[String]]()
			for(taggedSentence <- taggedSentences)
			{
				val grammedSentence = taggedSentence.sliding(n).toVector
				for(gram <- grammedSentence)
				{
					val pregram = lowercase(gram.take(n-1))
					if(mainmap.contains(pregram) == false)
					{
						mainmap = mainmap + (pregram -> new NgramProbDistr[String](Map[String,Int]()))
					}
					
					mainmap(pregram).update((gram.last).toLowerCase, 1)

				}
			}
			mainmap
		}
val defDistr = tokenizedSentences.flatten.groupBy(x => x).map{case (word, group) => word -> group.size}
		val cpd = new NgramCondProbDistr[Vector[String],String](mainDistr, new NgramProbDistr[String](defDistr))

		val ngm = new NgramModel(n, cpd)
		ngm

	}
}
