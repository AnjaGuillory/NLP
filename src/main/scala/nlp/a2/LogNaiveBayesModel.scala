package nlp.a2
import nlpclass._
import com.typesafe.scalalogging.log4j.Logging
import scala.math._

class LogNaiveBayesModel[Label, Feature, Value](
	labels: Set[Label],
	pLabel: ProbabilityDistributionToImplement[Label],
	pValue: Map[Feature, ConditionalProbabilityDistributionToImplement[Label, Value]]) 
	/*featExt: FeatureExtender[Feature,Value],*/
	extends NaiveBayesModelToImplement[Label, Feature, Value] with Logging{
	
	def predict(features: Vector[(Feature, Value)]): Label = {
		var argmax = new Array[(Label,Double)](0)
			//prob of each label
			for(label <- labels) {
				if(label != "") {
				val plabel = pLabel(label)
				var chain = 0.0
				for(pair <- features)	
				{
					val feature = pair._1
					val value = pair._2
					chain = chain + math.log((pValue(feature))(value,label))	
				}
				argmax = argmax ++ Array((label, math.exp(chain+math.log(plabel))))			}
 			}
			argmax.sortWith((e1: (Label, Double), e2: (Label, Double)) => (e2._2 > e1._2))	
			return argmax(0)._1		
	}

}
