package HadoopDemo.mr.compute;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Created by minming.zhu on 2017/1/5.
 */
public class TestMR {
    public static class TestMapper extends Mapper<Object,Text,Text,Text>{

    }
    public static class TestReduce extends Reducer<Text,Text,Text,Text>{

    }
    public static void main(String[] args){

    }
}
