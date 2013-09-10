package nlp.a0

import scala.io.Source
import scala.util.matching.Regex
import java.util.Arrays.sort

object CountFeatures {
	def main(args: Array[String]) = {
		val file: String = args(0) match {
			case file => file
			case "" => sys.error("You didn't put a file")
			case _ => sys.error("You put too many files")
			}

			var valcount = Map[String,Int]()
			var labval = Map[String, Map[String,Int]]()
			var remDup = new Array[(String, String, String)](0)
			var featureValues = Map[String, Map[String, Map[String, Int]]]()
			val lines = Source.fromFile(file).getLines()
			for(line <- lines)
			{
				val dataline = line.split(",")
				val label = dataline(dataline.length-1)
				for(i <- 0 to dataline.length-2)
				{
					val str = dataline(i).split("=")
					val feature = str(0)
					val value = str(1)
					remDup = remDup ++ Array((feature,label,value))
					val counter = remDup count (_==(feature,label,value))
					valcount += (value -> counter)
					labval += (label -> valcount)
					featureValues += feature -> labval
				}
			}
		
				val keyset = featureValues.keySet
				//println(keyset)
				for(x <- keyset)
				{
					val key = x
					println(key)
					val secondSet = featureValues(key).keySet
					//println(secondSet)
					for(y <- secondSet)
					{
						val secKey = y
						val thirdSet = featureValues(key)(secKey).keySet
						//println(thirdSet)
						println("	" + secKey)
						for(z <- thirdSet)
						{
							val thirdKey = z
							//println(featureValues(key)(secKey)(thirdKey))
							val num = featureValues(key)(secKey)(thirdKey)
							println("		"+ thirdKey + "	" + num)
						}
					}
					
					
				}	
	}
}