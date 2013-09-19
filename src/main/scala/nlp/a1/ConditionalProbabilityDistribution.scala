package nlp.a1

import nlp.a1.ProbabilityDistribution

class ConditionalProbabilityDistribution[A,B] (c: Map[A, ProbabilityDistribution[B]]) extends ConditionalProbabilityDistributionToImplement[A,B] {

	def apply(x: B, given: A): Double = {
		((c(given))(x))
	}

	def sample(given: A): B = {

		val pbUnderGiven = c(given)
		
		pbUnderGiven.sample
	}
}




