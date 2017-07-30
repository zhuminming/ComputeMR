package com.SparkDemo.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by zmm on 2017-07-18.
 */
public class WordCount {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args)throws Exception {
        SparkConf sparkConf = new SparkConf().setAppName("JavaWordCount");
        String srcPath = null;
        String desPath = "/apps/output";
        if (args.length == 1) {
            srcPath = args[0];
        } else if(args.length == 2) {
            srcPath = args[0];
            desPath = args[1];
        }
        else {
            System.out.println("Usage: java -jar jarName <src> [des]");
            System.exit(1);
        }

        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = jsc.textFile(srcPath, 1);

        System.out.println("Begin to split!");
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            public Iterable<String> call(String s) throws Exception {
                return Arrays.asList(SPACE.split(" "));
            }
        });

        System.out.println("Begin to map!");
        JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        });  //转化为pair Rdd

        System.out.println("Begin to reduce!");
        JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer i1, Integer i2) throws Exception {
                return i1 + i2;
            }
        });  //reduceByKey() 合并具有相同键的值

        System.out.println("Begin to save!");
        /*List<Tuple2<String, Integer>> output = counts.collect();
        for(Tuple2<?, ?> tuple: output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }*/
        counts.saveAsTextFile(desPath);
        jsc.stop();

    }
}
