package nlp.a4
import nlpclass.HiddenMarkovModelToImplement
import nlpclass.ConditionalProbabilityDistributionToImplement

class HiddenMarkovModel[Word,Tag](tagset: Set[Tag],transitions: ConditionalProbabilityDistributionToImplement[Tag,Tag], emissions: ConditionalProbabilityDistributionToImplement[Tag,Word]) extends HiddenMarkovModelToImplement[Word,Tag] {
	def sentenceProb(sentence: Vector[(Word,Tag)]): Double = {
		//println(transitions(sentence(0)._2,"<s>".asInstanceOf[Tag]))
		var probability = math.log(transitions((sentence(0)._2),"<s>".asInstanceOf[Tag])*emissions((sentence(0)._1),sentence(0)._2))
		var prevtag = sentence(0)._2
		//var logadd = 0.0
		for(i <- 1 to sentence.length-1)
		{
			val nextprob = math.log(transitions(sentence(i)._2,prevtag)*emissions(sentence(i)._1,sentence(i)._2))
			//logadd = logadd + addlogs(probability, nextprob)
			//probability = nextprob
			probability = probability + nextprob
			prevtag = sentence(i)._2
		}
		val last = sentence(sentence.length-1)
		//logadd = logadd + addlogs(probability,math.log(transitions("<e>".asInstanceOf[Tag],last._2)))
		probability = probability + math.log(transitions("<e>".asInstanceOf[Tag],last._2))		
		//logadd
		probability
	}

	def tagSentence(sentence: Vector[Word]): Vector[Tag] = {
		//Make a map for each word, each tag's viterbi score
		//var wordmap = Map[Word, Vector[(Tag,Double)]]()
		var besttags = Vector[Tag]()
		var prevtag = "<s>".asInstanceOf[Tag]
		var setoftags = tagset.toVector
		//println(setoftags)
		var scorevector = Vector[(Tag,Double)]()

		//for every possible tag
		for(tag <- setoftags)
		{
			//calculate the viterbi score for each tag given this word
			scorevector = scorevector ++ Vector((tag, transitions(tag, prevtag)*emissions(sentence(0),tag)))
			//println(scorevector)
		}

		//add it to the wordmap
		//wordmap = wordmap ++ Map(sentence(0) -> scorevector)
		scorevector = scorevector.sortWith((e1: (Tag, Double), e2: (Tag, Double)) => (e1._2 > e2._2))
		//println(scorevector)
		//make the max tag the previous tag
		prevtag = scorevector(0)._1
		var prevmaxscore = scorevector(0)._2
		
		//add this max tag to the best tags vector
		besttags = besttags ++ Vector(prevtag)

		var currentwordscores = Vector[(Tag,Double)]()
		/* Repeat for the rest of the words */
		for(i <- 1 to sentence.length-1){
			currentwordscores = Vector[(Tag,Double)]()
			for(tag <- setoftags)
			{
				currentwordscores = currentwordscores ++ Vector((tag, viterbi(prevtag,prevmaxscore,tag,sentence(i))))
			}

			//wordmap = wordmap ++ Map(sentence(i) -> currentwordscores)
			currentwordscores = currentwordscores.sortWith((e1: (Tag, Double), e2: (Tag, Double)) => e1._2 > e2._2)
			prevtag = currentwordscores(0)._1
			prevmaxscore = currentwordscores(0)._2
			besttags = besttags ++ Vector(prevtag)
		}




		/* Get the viterbi score of <e> to go backwards */
		/*
		val emaxscore = {
			var maxtag = "<s>".asInstanceOf[Tag]
			var max = 0.0
			val lastwordscores = currentwordscores
			for((tag,score) <- lastwordscores)
			{
				val tempscore = transitions("<e>".asInstanceOf[Tag],tag)*score
				if(tempscore >= max)
				{
					max = tempscore
					maxtag = tag
				}
			}
			besttags = besttags ++ Vector(maxtag)
			max
		}*/

		//backward(wordmap,emaxscore)

		besttags
	}

	def viterbi(previous: Tag, prevscore: Double,currenttag: Tag, word: Word): Double = 
	{
		transitions(currenttag,previous)*emissions(word,currenttag)*prevscore
	}

	def addlogs(x: Double, y: Double): Double = 
	{
		if(y == Double.NegativeInfinity)
		{
			y
		}
		else if(x == Double.NegativeInfinity)
		{
			x
		}
		else if(y <= x)
		{
			x+math.log1p(math.exp(y-x))
		}
		else
		{
			y+math.log1p(math.exp(x-y))
		}
	}
}
