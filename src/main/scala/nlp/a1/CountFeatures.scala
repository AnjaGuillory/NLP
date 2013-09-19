package nlp.a1

import scala.io.Source
import scala.util.matching.Regex
import java.util.Arrays.sort

object CountFeatures {
	def main(args: Array[String]): Unit = {
		val file: String = args(0) match {
			case file => file
			case "" => sys.error("You didn't put a file")
			case _ => sys.error("You put too many files")
			}
			countfeats(file)
	}
	
	def countfeats(file: String): Vector[(String,String,String)] = {
		
		//var featureValues = Map[String,String,String,Int]
		val lines = Source.fromFile(file).getLines().toVector
		val fvPairs = lines.flatMap{line => 

			val getlabel = line.split(",")
			val label = getlabel(getlabel.length-1)

			val vectPairs = Vector() ++ getlabel 		
			var pairs = vectPairs.dropRight(1).map {x =>
				var str = x.split("=")
				var feature = str(0)
				var value = str(1)
				(label,feature, value)
				}	

				pairs
			}
		
	fvPairs	

	}
}
			

		/*	
			var quads = new Array[(String, String, String, Int)](0)
			var pairSet = fvPairs.toSet
			for(x <- pairSet)
			{
				quads = quads ++ Array((x._2, x._1, x._3, (fvPairs count (_==x))))
			}
			
			quads = quads.sortWith((e1: (String,String,String,Int), e2: (String,String,String,Int)) => e1._1 < e2._1)
			//var fullMap = scala.collection.mutable.Map[String,String,String,Int]()

			var fullMap = scala.collection.mutable.Map[String, Map[String, Map[String,Int]]]()
			val remDup = new Array[String](0)
			for(i <- 0 to quads.length-1)
			{
				println(quads(i))
				val feature = quads(i)._1
				val label = quads(i)._2
				val value = quads(i)._3
				val counter = quads(i)._4
				val valcount = Map(value -> counter)
				val labval = Map(label -> valcount)
				fullMap += feature -> labval
			}*/

			//fullMap foreach { case (k,v) => println(k + "	" + v)}
			
			/*for((k,v) <- fullMap)
			{
				println(k)
				for((k,v) <- v)
				{
					println("	" + k)
					for((k,v) <- v)
					{
						println("	   " + k + "	" + v)
						
					}
				}						
			}*/
			

		





