package nlp.a3

import nlpclass.Tokenize
import dhg.util.CollectionUtil._
import dhg.util.FileUtil._

object Ngrams {
	def fileTokens(filename: String) = {
  File(filename).readLines
    .split("")
    .flatMap(paragraph => Tokenize(paragraph.mkString(" ")))
    .map(_.map(_.toLowerCase))
    .toVector
}
	def main(args: Array[String]) = {
		var n = 0
		var trainer = new UnsmoothedNgramModelTrainer(1)
		if(args.length == 6)
		{
			if(args(0) == "--n")
			{
				n = args(1).toInt
			}
			if(args(2) == "--train")
			{
				val trainfile = fileTokens(args(3))
				trainer = new UnsmoothedNgramModelTrainer(n)
				if(args(4) == "--test")
				{
					val model = trainer.train(trainfile)
					val testfile = fileTokens(args(5))
					println(PerplexityNgramModelEvaluator(model, testfile))
				}
			}
		}
		
		if(args.length == 8)
		{
			if(args(0) == "--n")
			{
				n = args(1).toInt
			}
			if(args(2) == "--train")
			{
				val trainfile = fileTokens(args(3))
				trainer = new UnsmoothedNgramModelTrainer(n)
				if(args(4) == "--test")
				{
					val model = trainer.train(trainfile)
					val testfile = fileTokens(args(5))
					println(PerplexityNgramModelEvaluator(model, testfile))
				}
				if(args(4) == "--lambda")
				{
					val lambda = args(5).toDouble
					val trainer = new AddLambdaNgramModelTrainer(n,lambda)
					val model = trainer.train(trainfile)

					if(args(6) == "--test")
					{
						val testfile = fileTokens(args(7))
						println(PerplexityNgramModelEvaluator(model, testfile))
					}

				}

				
			}
		}

		if(args.length == 10)
		{
			if(args(0) == "--n")
			{
				n = args(1).toInt
			}
			if(args(2) == "--train")
			{
				val trainfile = fileTokens(args(3))
				if(args(4) == "--lambda")
				{
					val lambda = args(5).toDouble
					if(args(6) == "--interp" && args(7) == "true")
					{
						val trainer = new InterpolatedNgramModelTrainer(lambda, n)
						val model = trainer.train(trainfile)

						if(args(8) == "--test")
						{
							val testfile = fileTokens(args(9))
							println(PerplexityNgramModelEvaluator(model, testfile))
						}
					}

				}

				
			}
		}
	}
}