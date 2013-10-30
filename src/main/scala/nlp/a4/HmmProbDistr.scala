package nlp.a4
import nlpclass._

class HmmProbDistr[B] (c: Map[B,Int], lambda: Double = 0.0, size: Int = 0) extends ProbabilityDistributionToImplement[B] {

	var map = scala.collection.mutable.Map() ++ c
	def total = map.values.sum.toDouble

	def apply(x: B): Double = {
		var count = 0.0

		if(map.contains(x))
		{
			count = map(x)
		}
		//println("Value:" + x)
		//println("Count: " + count)
		//println("Total: " + total)
		//println(size)
		(count + lambda)/(total + (lambda*size))

		
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

	def update(key: B, value: Int): Unit = {
		if(map.contains(key) == false)
		{
			map = map + (key -> value)
			//println(map(key))
		}
		else
		{
			map(key) = map(key)+value
			//println(map(key))
		}
		
	}
}




