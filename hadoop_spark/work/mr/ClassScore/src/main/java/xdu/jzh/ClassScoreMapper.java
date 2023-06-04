package xdu.jzh;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClassScoreMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text,
            Text, IntWritable>.Context context) throws IOException, InterruptedException {

        String ttt = new String(value.getBytes(), 0, value.getLength(), StandardCharsets.UTF_8);    //转换中文编码
        String[] lines = ttt.split("/n");
        for (String line : lines) {
            String[] params = line.split(",");
            String subjectAltClass = params[2] + "@" + params[0];
            int score = Integer.parseInt(params[4]);
            context.write(new Text(subjectAltClass), new IntWritable(score));
        }
    }
}