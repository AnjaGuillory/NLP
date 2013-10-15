package nlp.a3
import nlpclass.Tokenize
import dhg.util.CollectionUtil._
import dhg.util.FileUtil._
import nlp.a3.UnsmoothedNgramModelTrainer

object Test {

	def fileTokens(filename: String) = {
  File(filename).readLines
    .split("")
    .flatMap(paragraph => Tokenize(paragraph.mkString(" ")))
    .map(_.map(_.toLowerCase))
    .toVector
}

	def main(args: Array[String]) = {
		//val aliceText = fileTokens("src/main/scala/nlp/a3/alice.txt")
		//val trainer = new AddLambdaNgramModelTrainer(3,1.0)

		//val aliceModel = trainer.train(aliceText)
		//println(PerplexityNgramModelEvaluator(aliceModel, aliceText))
		//val trainer = new UnsmoothedNgramModelTrainer(1).train(fileTokens("src/main/scala/nlp/a3/alice.txt"))
		//println(trainer.generate.mkString(" "))
// came , a to ' turtle near spreading you next
//println(train(2).generate.mkString(" "))
// the question , and she said the jury-box , with oh , screamed ` call the list , we do .
//println(train(4).generate.mkString(" "))
// ` please would you tell me , ' said the duchess : you 'd only have to whisper a hint to time , and round goes the clock in a twinkling !
//println(train(5).generate.mkString(" "))
// ` in my youth , ' said his father , ' i took to the law , and argued each case with my wife ; and the muscular strength , which it gave to my jaw , has lasted the rest of my life . '
		
		/*val trainer = new UnsmoothedNgramModelTrainer(2)
		val alice = trainer.train(fileTokens("src/main/scala/nlp/a3/alice.txt"))
println(alice.sentenceProb("the last came a little bird , so there was that .".split(" ").toVector))
// -41.39217559191104
println(alice.sentenceProb("so there was that .".split(" ").toVector))
// -19.451400403614677
println(alice.sentenceProb("the last came a little bird .".split(" ").toVector))
// -Infinity*/



/*		
/*Vector(
	Vector(the, dog, runs, .), 
	Vector(the, dog, walks, .), 
	Vector(the, man, walks, .), 
	Vector(a, man, walks, the, dog, .), 
	Vector(the, cat, walks, .), 
	Vector(the, dog, chases, the, cat, .))
*/

val data =
  Vector(
    "the dog runs .",
    "the dog walks .",
    "the man walks .",
    "a man walks the dog .",
    "the cat walks .",
    "the dog chases the cat .")
    .map(_.split("\\s+").toVector)


val ngt = new UnsmoothedNgramModelTrainer(2)
val ngm = ngt.train(data)

val p1 = ngm.sentenceProb("the dog walks .".split("\\s+").toVector)
println(f"$p1%.4f  ${math.exp(p1)}%.4f")  // -2.4159  0.0893

val p2 = ngm.sentenceProb("the cat walks the dog .".split("\\s+").toVector)
println(f"$p2%.4f  ${math.exp(p2)}%.4f")  // -5.4604  0.0043

val p3 = ngm.sentenceProb("the cat runs .".split("\\s+").toVector)
println(f"$p3%.4f  ${math.exp(p3)}%.4f")  // NaN  Na*/


	}
}
