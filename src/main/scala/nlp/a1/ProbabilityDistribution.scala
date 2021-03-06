package nlp.a1
import nlpclass._

class ProbabilityDistribution[B] (c: Map[B,Int], lambda: Double = 0.0) extends ProbabilityDistributionToImplement[B] {

	var map = scala.collection.mutable.Map() ++ c
	var total = 0.0
	c foreach { case (k,v) => total = total + v}

	def apply(x: B): Double = {
		var count = map(x)
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




