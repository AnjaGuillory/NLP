package nlp.a3
import nlpclass._

class NgramProbDistr[B] (c: Map[B,Int], lambda: Double = 0.0, size: Int = 0) extends ProbabilityDistributionToImplement[B] {

	var map = scala.collection.mutable.Map() ++ c

	def total = map.values.sum.toDouble

	def printout = {
		map foreach { case (string, count) => println("      " + string + " " + count)}
	}

	def apply(x: B): Double = {
		var count = 0.0
		
		if(map.contains(x))
		{
			count = map(x)
		}
		//println(size)
		(count + lambda)/(total + lambda*size)
		//(count+lambda)/(total + lambda*size)

	}


	def sample: B = {
		//println(map)
		val r = scala.util.Random.nextDouble()
		var sum = 0.0
		for((k,v) <- map) {
			//println(v)
			sum += v/total
			//println(sum)
			if(sum > r) 
				//println(k)
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




