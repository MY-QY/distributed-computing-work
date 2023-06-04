package xdu.jzh;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class GrandMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String ttt = new String(value.getBytes(), 0, value.getLength(), StandardCharsets.UTF_8);    //转换中文编码
        String[] lines = ttt.split("/n");

        for (String line : lines) {
            String[] params = line.split(",");
            String child = params[0].trim();
            String parent = params[1].trim();
            context.write(new Text(parent), new Text(child));
            context.write(new Text(child), new Text("@" + parent));
        }
    }
}
