package nlp.a3


import nlpclass._

class NgramCondProbDistr[A,B] (c: Map[A, NgramProbDistr[B]], default: NgramProbDistr[B]) extends ConditionalProbabilityDistributionToImplement[A,B] {

	def apply(x: B, given: A): Double = {
	
		if (c.contains(given) == false){			
			default(x)
		}
		else	
			c(given)(x)

	}

	def sample(given: A): B = {

		val pbUnderGiven = c(given)
		pbUnderGiven.sample
	}
}

