package nlp.a3

import nlpclass.NgramModelToImplement



class InterpolatedNgramModel(ngram: Int, models: Vector[(NgramModelToImplement,Double)]) extends NgramModelToImplement {
	def n: Int = ngram

	def sentenceProb(sentence: Vector[String]): Double = {
		var probabilities = Vector[Double]()
		for((model,weight) <- models)
		{

			probabilities = probabilities ++ Vector(model.sentenceProb(sentence))
		}

		var logadd = 0.0
		for(i <- 0 until probabilities.length-2)
		{
			val x = probabilities(i)
			val y = probabilities(i+1)

			if(y == Double.NegativeInfinity)
			{
				logadd = logadd + y
			}
			else if(x == Double.NegativeInfinity)
			{
				logadd = logadd + x
			}
			else if(y <= x)
			{
				logadd = logadd + (x + math.log1p(math.exp(y-x)))
			}
			else
			{
				logadd = logadd + (y + math.log1p(math.exp(x-y)))
			}
		}

		logadd
	}

	def generate(): Vector[String] = {
		val sorted = models.sortWith((e1:(NgramModelToImplement,Double), e2:(NgramModelToImplement,Double)) => (e1._1.n > e2._1.n))
		sorted(0)._1.generate()
	}
}