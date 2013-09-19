package nlp.a1

trait FeatureFileAsDistributionsToImplement {
  def fromFile(filename: String): (Set[String], ProbabilityDistributionToImplement[String], Map[String, ConditionalProbabilityDistributionToImplement[String, String]])
}
