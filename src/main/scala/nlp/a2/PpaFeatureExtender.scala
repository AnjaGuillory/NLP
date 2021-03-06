package nlp.a2

import nlpclass._

/*
class NumberFeatureExtender//[Feature,Value] extends FeatureExtender[Feature,Value]
{
	val regex = "[0-9]*".r
}

/*class LemmaFeatureExtender//[Feature,Value] extends FeatureExtender[Feature,Value]
{
	val regex = [
}*/

class CapitalizedFeatureExtender//[Feature,Value] extends FeatureExtender[Feature,Value]
{
	val regex = "[A-Za-z]*".r
}

class HyphenedFeatureExtender//[Feature,Value] extends FeatureExtender[Feature,Value]
{
	val regex = "[A-Za-z]*-[A-Za-z]*".r
}


class PpaFeatureExtender[Feature,Value] extends FeatureExtender[Feature,Value]
{
	val featureExtender = new CompositeFeatureExtender[String,String](Vector(new NumberFeatureExtender(), new HyphenedFeatureExtender(), new CapitalizedFeatureExtender()))	

	def extendFeatures(features: Vector[(Feature, Value)]): Vector[(Feature, Value)] =
	{
		features
	}
}
*/


