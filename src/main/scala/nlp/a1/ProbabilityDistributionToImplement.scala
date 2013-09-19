package nlp.a1

trait ProbabilityDistributionToImplement[B] {
  def apply(x: B): Double
  def sample(): B
}


