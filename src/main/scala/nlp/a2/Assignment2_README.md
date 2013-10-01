Name: Anja Guillory
UTEID: amg4228
Section: TTH 200-330

Overview:

So far I have created a NaiveBayes project that calculates probabilities of words (features and values) using the Naive Bayes method. This method assumes that the occurrence of every word is independent of another.

This project consists of NaiveBayes models and trainers that include Smoothing, which is a trick used to extinguish zero probabilities from the naive bayes model in order to produce better probabilities for each word (feature and value) within each label. This is so that every word can be predicted and not assumed impossible. 

There are also Log versions of the models and trainers which basically takes the log of the probabilities of any base (must all be the same). Then the returned value is e to the exponent of the sum of the logs of the probabilities. This is to make it easier to calculate really small numbers. 

The trainers are used to calculate the probabilities of feature,value pairs in the training files in order to create a model to test with a test file. 

In summary, this project was a learning experience to solidify the understanding of how to "naively" calculate word probabilities using non-smoothing, smoothing, and log techniques.

The problems I have in the project are:
The FeatureExtender does not work with the rest of the project.
My logger format is incorrect, howver it does show the label and their overall probabilities
My accuracy, precision, recall, and F1's for the PPA section are a bit off from what is mentioned correct in the assignment instructions.


List of files:
AddLambdaNaiveBayesTrainer.scala
LogAddLambdaNaiveBayesTrainer.scala
LogNaiveBayesModel.scala
NaiveBayes.scala
NaiveBayesModel.scala
NaiveBayesScorer.scala
PpaFeatureExtender.scala
UnsmoothedNaiveBayesTrainer.scala

Commands:
The same commands as instructed can be used.
