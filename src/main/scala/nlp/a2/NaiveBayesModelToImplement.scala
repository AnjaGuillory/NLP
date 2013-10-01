package nlp.a2

trait NaiveBayesModelToImplement[Label,Feature,Value] {
	def predict(features : Vector[(Feature,Value)]): Label
} 
