package nlp.a2


import scala.io.Source
import nlp.a2.NaiveBayesScorer

object NaiveBayes {
	def main(args: Array[String]) = {
		if(args.length == 2)
		{
			val file = makeVector(args(1))
			val nbt = new UnsmoothedNaiveBayesTrainer[String,String,String]()
			val nbm = nbt.train(file)
			
		}
		else if(args.length == 6)
		{
			val file = makeVector(args(1))
			val nbt = new UnsmoothedNaiveBayesTrainer[String,String,String]()
			val nbm = nbt.train(file)
			val testfile = makeVector(args(3))
			NaiveBayesScorer.score(nbm,testfile,args(5))
		}
		else if(args.length == 8)
		{
			val file = makeVector(args(1))
			if(args(6) == "--lambda")
			{
				val nbt = new AddLambdaNaiveBayesTrainer[String,String,String](args(7).toDouble)	
				val nbm = nbt.train(file)
				val testfile = makeVector(args(3))
				NaiveBayesScorer.score(nbm,testfile,args(5))	
			}		
			else if(args(6) == "--extend" && args(7) == "true")
			{
				val file = makeVector(args(1))
				val nbt = new UnsmoothedNaiveBayesTrainer[String,String,String]()
				val nbm = nbt.train(file)
				val testfile = makeVector(args(3))
				NaiveBayesScorer.score(nbm,testfile,args(5))
			}
		}
		else if(args.length == 10)
		{
			val file = makeVector(args(1))
			if(args(6) == "--lambda" && args(8) == "--log" && args(9) == "true")
			{
				val nbt = new LogAddLambdaNaiveBayesTrainer[String,String,String](args(7).toDouble)
				val lnbm = nbt.train(file)
				val testfile = makeVector(args(3))
				NaiveBayesScorer.score(lnbm,testfile,args(5))
			}
			else if(args(6) == "--lambda" && args(8) == "--extend" && args(9) == "true")			{
				val nbt = new AddLambdaNaiveBayesTrainer[String,String,String](args(7).toDouble)	
				val nbm = nbt.train(file)
				val testfile = makeVector(args(3))
				NaiveBayesScorer.score(nbm,testfile,args(5))
			}
		}
		else if(args.length == 12)
		{
			val file = makeVector(args(1))
			if(args(6) == "--lambda" && args(8) == "--log" && args(9) == "true" && args(10) == "--extend" && args(11) == "true")
			{
				val nbt = new LogAddLambdaNaiveBayesTrainer[String,String,String](args(7).toDouble)
				val lnbm = nbt.train(file)
				val testfile = makeVector(args(3))
				NaiveBayesScorer.score(lnbm,testfile,args(5))	
			}
			
		}	
		
	}

	def makeVector(file: String): Vector[(String, Vector[(String,String)])] = {
			val lines = Source.fromFile(file).getLines() 		
		
			var labelPairs = new Array[(String, Vector[(String,String)])](0)

			for(line <- lines)
			{
				val info = line.split(",")
				val label = info(info.length-1)
				if(label != ""){
				//println(label)
				var pairs = new Array[(String, String)](0)
				for(i <- 0 to info.length-2)
				{
					val fv = info(i)
					val pair = fv.split("=")
					//println(" " + (pair(0),pair(1)))
					pairs = pairs ++ Array((pair(0),pair(1)))
				}

				val vectPairs = Vector() ++ pairs
				labelPairs = labelPairs ++ Array((label, vectPairs))}
				
			}
			
			Vector() ++ labelPairs 
	}
}
