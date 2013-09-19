package nlp.a1

trait ConditionalProbabilityDistributionToImplement[A, B] {
  def apply(x: B, given: A): Double
  def sample(given: A): B
}
