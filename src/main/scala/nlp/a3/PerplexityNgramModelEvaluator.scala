package nlp.a3

object PerplexityNgramModelEvaluator extends nlpclass.NgramModelEvaluator {
	def apply(model: nlpclass.NgramModelToImplement, tokenizedSentences: Vector[Vector[String]]): Double = {
		var allprobs = 0.0
		var sizes = 0.0
		for(sentence <- tokenizedSentences)
		{
			allprobs = allprobs + model.sentenceProb(sentence)
			sizes = sizes + sentence.length
		}

		math.exp((-1.0*allprobs)/sizes)
	}
}