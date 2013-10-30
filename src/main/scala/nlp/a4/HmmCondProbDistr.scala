package nlp.a4

import nlpclass._

class HmmCondProbDistr[A,B] (c: Map[A, HmmProbDistr[B]]) extends ConditionalProbabilityDistributionToImplement[A,B] {

	def apply(x: B, given: A): Double = {
		//println("Given: " + given)
		c(given)(x)
	}

	def sample(given: A): B = {

		val pbUnderGiven = c(given)
		
		pbUnderGiven.sample
	}
}




