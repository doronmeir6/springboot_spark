package com.spark.spring;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class WordCountService {

    @Autowired
    JavaSparkContext sc;

    public String getCount(String path)
    {
        JavaRDD<String> wordLines = sc.textFile(path);
        JavaPairRDD<String, Integer> counts = wordLines.flatMap(line -> Arrays.asList(line.split(" ")).iterator()).mapToPair(word-> new Tuple2<>(word, 1)).reduceByKey((p,q)->p + q);
        return tuplesToString(getMostPopularWord(counts));
    }


    /**
     * swap between map JavaPairRDD<String, Integer> to JavaPairRDD<Integer, String> sort it by Integer and take top 10 popular
     * @param wordsWithCount
     * @return
     */
    public  List<Tuple2<Integer,String>> getMostPopularWord(JavaPairRDD<String, Integer> wordsWithCount)
    {
        // swap k,v to v,k to sort by word frequency
        JavaPairRDD<Integer, String> wc_swap = wordsWithCount.flatMapToPair(x -> Collections.singletonList(x.swap()).iterator());
        // sort keys by ascending=false (descending)+ filter ""
        JavaPairRDD<Integer, String>  hifreq_words = wc_swap.sortByKey(false,1).filter(tup->!tup._2.equals(""));
        // get an array of top 10 frequent words
        return  hifreq_words.take(10);
    }

    /**
     * converts tuples of number +word in string to string for presentation
     * @param hifreqWordsListTuple
     * @return
     */
    public String tuplesToString(List<Tuple2<Integer,String>> hifreqWordsListTuple)
    {
        StringBuilder str=new StringBuilder();
        for (Tuple2<Integer,String> pair:hifreqWordsListTuple)
        {
        str.append(pair._2).append(",").append(+pair._1).append("\n");
        }
        return String.valueOf(str);
    }
}
