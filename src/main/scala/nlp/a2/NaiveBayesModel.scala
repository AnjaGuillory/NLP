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
			if(label != ""){
			val plabel = pLabel(label)
			var chain = 1.0
			for(pair <- features)	
			{
				//println((pValue(pair._1))(pair._2,label))
				
				val feature = pair._1
				val value = pair._2	
				val item = (pValue(feature))(value,label) 
				chain = chain*item
			}
			logger.info(label + " " + chain*plabel)
			argmax = argmax ++ Array((label, (chain*plabel)))	
			}
			
 		}
		//val vectormax = Vector() ++ argmax
		scala.util.Sorting.stableSort(argmax,(e1: (Label, Double), e2: (Label, Double)) => e1._2 > e2._2) 
		
		return argmax(0)._1		
	}

}
