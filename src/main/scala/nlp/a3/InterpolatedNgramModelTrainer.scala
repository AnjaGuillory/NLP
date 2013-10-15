package nlp.a3

import nlpclass._

class InterpolatedNgramModelTrainer(lambda: Double,n: Int) extends NgramModelTrainerToImplement
{

	def train(tokenizedSentences: Vector[Vector[String]]): NgramModelToImplement = {
		var totalweights = 0.0

		for(i <- 1 to n)
		{
			totalweights = totalweights + math.pow(2,i-1)
		}

    	var models = Vector[(NgramModelToImplement, Double)]()
    	for(i <- 1 to n)
    	{
    		val lambdatrainer = new AddLambdaNgramModelTrainer(i,lambda)
    		var weight = math.pow(2,i-1)
    		models = models ++ Vector((lambdatrainer.train(tokenizedSentences),weight/totalweights))

    	}	

    	new InterpolatedNgramModel(n,models)
	}
}