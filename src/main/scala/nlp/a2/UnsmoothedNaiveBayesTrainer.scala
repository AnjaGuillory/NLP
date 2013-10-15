package nlp.a2

import nlp.a1.ProbabilityDistribution
import nlp.a1.ConditionalProbabilityDistribution
import nlpclass._

class UnsmoothedNaiveBayesTrainer[Label,Feature,Value]/*(featExt: FeatureExtender[Feature,Value])*/ extends NaiveBayesTrainerToImplement[Label,Feature,Value] {

	def train(instances: Vector[(Label, Vector[(Feature, Value)])]): NaiveBayesModelToImplement[Label, Feature, Value] = {
		val labels = {
			var someset = Vector[Label]() 
			for(i <- 0 to instances.length-1)	
			{
				someset = someset ++ Vector(instances(i)._1)
			}
			
			someset.toSet
		}
		println(labels)

		//tabunn double ni kaware? (groupedByLabel.size: Double)?
		val pLabel = new ProbabilityDistribution[Label](instances.groupBy(_._1).map{ case (label, groupedByLabel) => label -> groupedByLabel.size})

		val instanceVector = {  var arrInst = new Array[(Label, Feature, Value)](0)
					for(i <- 0 to instances.length-1)
					{
						val tuple = instances(i)
						val fvPairs = tuple._2
						for(j <- 0 to fvPairs.length-1)
						{
							val pair = fvPairs(j)
							arrInst = arrInst ++ Array((tuple._1, pair._1, pair._2))
						}
					}
					Vector() ++ arrInst
				     }		
		//kore mo kaware no? (groupedByValue.size: Double)?
		val pValue = instanceVector.groupBy(_._2).map{ 
			case (feature, groupedByFeature) => feature -> new ConditionalProbabilityDistribution[Label,Value](groupedByFeature.groupBy(_._1).map{ 
				case (label, groupedByLabel) => label -> new ProbabilityDistribution[Value](groupedByLabel.groupBy(_._3).map{ 
					case (value, groupedByValue) => value -> groupedByValue.size})})}

		val nbm = new NaiveBayesModel[Label,Feature,Value](labels, pLabel, pValue)
	
		nbm
	
	}
}
