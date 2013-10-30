package nlp.a4
import scala.io.Source


object Test{
	def main(args: Array[String]) = {
			val file = Source.fromFile(args(0)).getLines()
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

			val trainer = new AddLambdaSmoothedHmmTrainer[String, String](0.0)
			val model = trainer.train(taggedSentences)

			val s1 = Vector(("the", "D"), ("dog", "N"), ("runs", "V"))
			val p1 = model.sentenceProb(s1)
			println(f"$p1%.4f  ${math.exp(p1)}%.4f") // -3.6339  0.0264

			val s2 = Vector(("the", "D"), ("cat", "N"), ("runs", "V"))
			val p2 = model.sentenceProb(s2)
			println(f"$p2%.4f  ${math.exp(p2)}%.4f") // -4.9496  0.0071

			val s3 = Vector(("the", "D"), ("man", "N"), ("held", "V"), ("the", "D"), ("saw", "N"))
			val p3 = model.sentenceProb(s3)
			println(f"$p3%.4f  ${math.exp(p3)}%.4f") // -13.0951  0.0000

			println(model.tagSentence("the dog runs".split("\\s+").toVector))
			// Vector(D, N, V)
			println(model.tagSentence("the cat runs".split("\\s+").toVector))
			// Vector(D, N, V)
			println(model.tagSentence("the man held the saw".split("\\s+").toVector))
			// Vector(D, N, V, D, N)

	}
}