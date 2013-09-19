package nlp.a1

import nlp.a1.FeatureFileAsDistributions 

object Test {
	def main(args: Array[String]) = {

		val (labels, pLabel, pFeatureValueGivenLabelByFeature) = FeatureFileAsDistributions.fromFile("data.txt")

		println(labels)
		//println(pLabel)
		//println(pFeatureValueGivenLabelByFeature)
		
		//println(labels) // Set(neutral, negative, positive)

		println(f"p(label=negative) = ${pLabel("negative")}%.2f") // 0.57
		println(f"p(label=neutral)  = ${pLabel("neutral")}%.2f")  // 0.14
		println(f"p(label=positive) = ${pLabel("positive")}%.2f") // 0.29

		val featureNeg = pFeatureValueGivenLabelByFeature("neg")
		println(f"p(neg=bad | label=negative) = ${featureNeg("bad", "negative")}%.2f") // 0.29

		val featurePos = pFeatureValueGivenLabelByFeature("pos")
		println(f"p(pos=best | label=negative) = ${featurePos("best", "negative")}%.2f") // 1.00
		println(f"p(pos=best | label=positive) = ${featurePos("best", "positive")}%.2f") // 0.25
		
		val featureWord = pFeatureValueGivenLabelByFeature("word")
		println(f"${featureWord("best", "negative")}%.2f") // p(word=best | label=negative) = 0.07
		println(f"${featureWord("best", "positive")}%.2f") // p(word=best | label=positive) = 0.13*/

	}
}
