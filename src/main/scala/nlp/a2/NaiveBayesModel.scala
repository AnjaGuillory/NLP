package nlp.a2
import nlpclass._
import com.typesafe.scalalogging.log4j.Logging

class NaiveBayesModel[Label,Feature,Value](
	labels: Set[Label],
	pLabel: ProbabilityDistributionToImplement[Label],
	pValue: Map[Feature, ConditionalProbabilityDistributionToImplement[Label, Value]])
	/*featExt: FeatureExtender[Feature,Value],*/
	extends NaiveBayesModelToImplement[Label,Feature,Value] with Logging {

	def predict(features: Vector[(Feature, Value)]): Label = {
		var argmax = new Array[(Label,Double)](0)
		//prob of each label
		for(label <- labels) {
			
			var chain = pLabel(label)//p(x)
			for(pair <- features)	
			{
				//println((pValue(pair._1))(pair._2,label))
				
				val feature = pair._1
				val value = pair._2	
				chain = chain * ((pValue(feature))(value,label)) //p(featval|x)
			}
			//logger.info(label + " " + chain)
			argmax = argmax ++ Array((label, chain))	
			
 		}
		//val vectormax = Vector() ++ argmax
		scala.util.Sorting.stableSort(argmax,(e1: (Label, Double), e2: (Label, Double)) => e1._2 > e2._2) 
		for(i <- 0 to argmax.length-2)
				{
					val tuple1 = argmax(i)
					val tuple2 = argmax(i+1)
					logger.info(tuple1._1 + " " + tuple1._2 + " " + tuple2._1 + " " + tuple2._2)

				}
		//println(argmax(0)._1)
		return argmax(0)._1		
	}

}

/*logger.debug()*/
