package nlp.a1

class ProbabilityDistribution[B] (c: Map[B,Int]) extends ProbabilityDistributionToImplement[B] {

	def apply(x: B): Double = {
		val count = c(x)
		var total = 0.0
		//c foreach { case (k,v) => total = total + v}
		count/total
	}

	def sample: B = {
		val r = scala.util.Random.nextDouble()
		var sum = 0.0
		var total = 0.0
		c foreach { case (k,v) => total = total + v}
		for((k,v) <- c) {
			sum += v/total
			if(sum > r) 
				return k
		}
		sys.error("Could not return")
	}
}




