package nlp.a2

trait NaiveBayesScorerToImplement {
	def score[Label, Feature, Value](
  	  naiveBayesModel: NaiveBayesModelToImplement[Label, Feature, Value],
  	  testInstances: Vector[(Label, Vector[(Feature, Value)])],
   	  positiveLabel: Label)
}