package nlp.a2
import scala.io.Source
import nlp.a1.FeatureFileAsDistributions

object Test {
	def main(args: Array[String]) = {
	val (label, pLabel, pValue) = FeatureFileAsDistributions.fromFile("data.txt")
	val nbm = new NaiveBayesModel[String,String,String](label,pLabel,pValue)
	val lines = Source.fromFile("train.txt").getLines()
	var instancing = new Array[(String, Vector[(String,String)])](0)

	for(line <- lines)
	{
		val text = line.split(",")
		val label = text(text.length-1)
		var pairing = new Array[(String,String)](0)
		for(i <- 0 to text.size-2)
		{
			val pair = text(i).split("=")
			val feature = pair(0)
			val value = pair(1)
			pairing = pairing ++ Array((feature,value))
		}
		val pairs = Vector() ++ pairing
		instancing = instancing ++ Array((label,pairs))
		
	}
		val instances = Vector() ++ instancing
		val nbt = new LogAddLambdaNaiveBayesTrainer[String,String,String](4.0)

		val newnbm = nbt.train(instances)


	val testlines = Source.fromFile("test.txt").getLines()
	var testInstancing = new Array[(String, Vector[(String,String)])](0)
	for(line <- testlines)
	{
		val text = line.split(",")
		val label = text(text.length-1)
		var pairing = new Array[(String,String)](0)
		for(i <- 0 to text.size-2)
		{
			val pair = text(i).split("=")
			val feature = pair(0)
			val value = pair(1)
			pairing = pairing ++ Array((feature,value))
		}
		val pairs = Vector() ++ pairing
		testInstancing = testInstancing ++ Array((label,pairs))
		
	}
		val testInstances = Vector() ++ testInstancing
		
		NaiveBayesScorer.score(newnbm, testInstances, "Yes")
				
		
		
	}

}
