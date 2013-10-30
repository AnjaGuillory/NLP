package nlp.a4
import nlpclass.HmmTrainerToImplement
import nlpclass.HiddenMarkovModelToImplement
import scalaz._
import Scalaz._


class UnsmoothedHmmTrainer[Word,Tag] extends HmmTrainerToImplement[Word,Tag] {
	//NOTE: Make map of word counts first then make into HmmProbDistr
	
	//var emissions = Map[Tag, HmmProbDistr[Word]]()
	var transitions = Map[Tag, HmmProbDistr[Tag]]()
	//var emissions = List[(Tag, HmmProbDistr[Word])]()
	var emissions = Map[Tag, Map[Word,Int]]()
	def train(taggedSentences: Vector[Vector[(Word,Tag)]]): HiddenMarkovModelToImplement[Word,Tag] = {
				//make a tagset to pass into the model

				//add the transition <s> as a key 
				//add the first sentence's first word in <s>'s probability distribution
				transitions = transitions + ("<s>".asInstanceOf[Tag] -> new HmmProbDistr[Tag](Map[Tag,Int]()))
				
				//for every sentence in the tagged Sentences vector
				for(sentence <- taggedSentences)
				{
					//turn emissions into a list of tuples?
					//turn the map for the newly counted words into a list of tuples?
					//thn perform |+|?
					emissions = emissions |+| sentence.groupBy(_._2).map{ case (tags, groupedByTags) => tags -> (groupedByTags.groupBy(_._1).map{case (word, groupedByWords) => word -> groupedByWords.size})}


					//add first word to <s>'s probability distribution
					transitions("<s>".asInstanceOf[Tag]).update(sentence(0)._2, 1)
					//get the tag after <s> tag
					var prevtag = sentence(0)._2

					for(i <- 1 to sentence.length-1)
					{
						//println(prevtag)
						//if we have already seen the previous tag, then add 1 the count of current tag in the probability distribution of the prevtag
						if(transitions.contains(prevtag))
						{
							transitions(prevtag).update(sentence(i)._2,1)
							//println("Problem not here?")
						}
						//else add the prevtag as a key, and create a new probability distribution with the count of the current tag = 1 
						else
						{
							//println("Problem not here?")
							//println(prevtag)
							transitions = transitions + (prevtag -> new HmmProbDistr[Tag](Map[Tag,Int](sentence(i)._2 -> 1)))	
						}
						//now the current tag is the previous tag
						prevtag = sentence(i)._2
						//println(prevtag)
					}
					if(transitions.contains(prevtag) == false)
					{
						transitions = transitions + (prevtag -> new HmmProbDistr[Tag](Map[Tag,Int]()))	
					}
					//add the last word's probability distribution the count of <e>'s seen after
					//println(prevtag)
					transitions(prevtag).update("<e>".asInstanceOf[Tag],1)
				}
				var tagset = Set[Tag]()
				val newemissions = {
					var tempmap = Map[Tag, HmmProbDistr[Word]]()
					for((k,v) <- emissions)
					{
						tagset = tagset ++ Set(k)
						tempmap = tempmap + (k -> new HmmProbDistr[Word](v))
					}
					tempmap
				}
				//println("Emissions: "+ newemissions("V".asInstanceOf[Tag]).map)
		new HiddenMarkovModel(tagset,new HmmCondProbDistr[Tag,Tag](transitions),new HmmCondProbDistr[Tag,Word](newemissions))
	}
}