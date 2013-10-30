package nlp.a4
import scala.io.Source


object Hmm{
	def main(args: Array[String]) = {
			val file = Source.fromFile(args(1)).getLines()
			var taggedSentences = Vector[Vector[(String,String)]]()
			for(line <- file)
			{
				var tagsandwords = Vector[(String,String)]()
				val pairs = line.split(" ")
				//println(pairs.toVector)
				for(str <- pairs)
				{
					val splitted = str.split("\\|")
					//println(splitted.toVector)
					val tags = splitted(1)
					val words = splitted(0)
					//println(words + "|" + tags)

					tagsandwords = tagsandwords ++ Vector((words,tags))
				}
				taggedSentences = taggedSentences ++ Vector(tagsandwords)
			}

			val hmmt = new UnsmoothedHmmTrainer[String,String]()

			if(args.length == 6)
			{
				if(args(4) == "--lambda")
				{
					val alhmmt = new AddLambdaSmoothedHmmTrainer[String,String](args(5).toDouble)
					val model = alhmmt.train(taggedSentences)


					var originalTagging = Vector[Vector[String]]()
					var originalNoTags = Vector[Vector[String]]()

					val devfile = Source.fromFile(args(3)).getLines()
					var devset = Vector[Vector[(String,String)]]()
					for(line <- devfile)
					{
						val pairs = line.split(" ")
						var words = Vector[String]()
						var tags = Vector[String]()
						//println(pairs.toVector)
						for(str <- pairs)
						{
							val splitted = str.split("\\|")
							//println(splitted.toVector)
							val tag = splitted(1)
							val word = splitted(0)
							//println(words + "|" + tags)
							tags = tags ++ Vector(tag)
							words = words ++ Vector(word)
						}			
					originalTagging = originalTagging ++ Vector(tags)
					originalNoTags = originalNoTags ++ Vector(words)
					}

					var accounter = 0.0
					var modelGuesses = Vector[Vector[String]]()
					var total = 0.0
					for(sentence <- originalNoTags)
					{
						modelGuesses = modelGuesses ++ Vector(model.tagSentence(sentence))
					}

					var mistakepairs = Vector[(String,String)]()

					for(i <- 0 to originalTagging.length-1)
					{
						val originalSeq = originalTagging(i)
						val modelSeq = modelGuesses(i)

						for(j <- 0 to originalSeq.length-1)
						{
							if((modelSeq(j) == originalSeq(j))==false)
							{
								mistakepairs = mistakepairs ++ Vector((originalSeq(j),modelSeq(j)))
							}
							else
							{
								accounter = accounter + 1
							}
							total = total + 1
						}
					}

					var mistakepairmap = mistakepairs.groupBy(x=>x).map{case (tagpairs,groupedByPairs) => tagpairs -> groupedByPairs.size}
					val setmap = mistakepairmap.toSet
					val listmap = setmap.toList
					val sorted = listmap.sortWith((e1: ((String,String),Int), e2: ((String,String),Int)) => (e1._2 > e2._2))
					//println(sorted)
					println("Accuracy: " + (accounter/total)*100 + " " + accounter + "/" + total)
					println("count	gold	medal")
					for(i <- 0 to 9)
					{
						val pair = sorted(i)
						val count = pair._2
						val gold = (pair._1)._1
						val model = (pair._1)._2
						println(count + "	" + gold + "	" + model)
					}
				}
			}

			else
			{			

				val model = hmmt.train(taggedSentences)


				var originalTagging = Vector[Vector[String]]()
				var originalNoTags = Vector[Vector[String]]()

				val devfile = Source.fromFile(args(3)).getLines()
				var devset = Vector[Vector[(String,String)]]()
				for(line <- devfile)
				{
					val pairs = line.split(" ")
					var words = Vector[String]()
					var tags = Vector[String]()
					//println(pairs.toVector)
					for(str <- pairs)
					{
						val splitted = str.split("\\|")
						//println(splitted.toVector)
						val tag = splitted(1)
						val word = splitted(0)
						//println(words + "|" + tags)
						tags = tags ++ Vector(tag)
						words = words ++ Vector(word)
					}			
					originalTagging = originalTagging ++ Vector(tags)
					originalNoTags = originalNoTags ++ Vector(words)
				}	

				var accounter = 0.0
				var modelGuesses = Vector[Vector[String]]()
				var total = 0.0
				for(sentence <- originalNoTags)
				{
					modelGuesses = modelGuesses ++ Vector(model.tagSentence(sentence))
				}	

				var mistakepairs = Vector[(String,String)]()

				for(i <- 0 to originalTagging.length-1)
				{
					val originalSeq = originalTagging(i)
					val modelSeq = modelGuesses(i)

					for(j <- 0 to originalSeq.length-1)
					{
						if((modelSeq(j) == originalSeq(j))==false)
						{
							mistakepairs = mistakepairs ++ Vector((originalSeq(j),modelSeq(j)))
						}
						else
						{
							accounter = accounter + 1
						}
						total = total + 1
					}
				}
		

				var mistakepairmap = mistakepairs.groupBy(x=>x).map{case (tagpairs,groupedByPairs) => tagpairs -> groupedByPairs.size}
				val setmap = mistakepairmap.toSet
				val listmap = setmap.toList
				val sorted = listmap.sortWith((e1: ((String,String),Int), e2: ((String,String),Int)) => (e1._2 > e2._2))
				//println(sorted)
				println("Accuracy: " + (accounter/total)*100 + " " + accounter + "/" + total)
				println("count	gold	medal")
				for(i <- 0 to 9)
				{
					val pair = sorted(i)
					val count = pair._2
					val gold = (pair._1)._1
					val model = (pair._1)._2
					println(count + "	" + gold + "	" + model)
				}
		}
	}
}