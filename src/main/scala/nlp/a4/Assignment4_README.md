Name: Anja Guillory
UTEID: amg4228

Written Answers:
1. a) The same tag being inputted suggests that the model was not trained on this word, and thus is an unknown entity. To care for this, the model decides on a default tag to assign to these unknown words, which will in this case cause many errors because the default tag won't always match the true tag of the unknown word.
2. a)
	Lamda values 		Accuracy		Most common mistake
	1.0         		90.709			gold - JJ, model - NN
	1.5					89.770			gold - JJ, model - NN
	2.0    				89.0301			gold - JJ, model - NN
	3.0 				87.6444			gold - JJ, model - NN
	0.1 				91.9786			gold - NNP, model - POS
	0.001 				92.0176 		gold - NNP, model - POS

	It appears that the lower the lambda, the better the accuracy after slight smoothing. The accuracy with low smoothing is much higher than the accuracy with smoothing at all

	b) Lambda : 0.00156 Accuracy: 92.0287


1. This project implements the Hidden Markov Model idea and the Viterbi algorithm, which is used to decide the best tag sequences for an untagged sequence of words in a sentence. The Hidden Markov Model is a generative model used to calculate the probabilities of sentences with given sequences of tags using the two assumptions that the words are based solely on their given tags, and every tag depends on the the previous (n) tag(s). 
2. Files included:
	UnsmoothedHmmTrainer.scala
	AddLambdaSmoothedHmmTrainer.scala
	Hmm.scala
	HmmCondProbDistr.scala
	HmmProbDistr.scala
	HiddenMarkovModel.scala
3. Commands same as instructed