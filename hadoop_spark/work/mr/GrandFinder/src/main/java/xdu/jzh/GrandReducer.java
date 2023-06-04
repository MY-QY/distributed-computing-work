package xdu.jzh;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class GrandReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        ArrayList<Text> grandparent = new ArrayList<>();
        ArrayList<Text> grandchild = new ArrayList<>();
        for (Text t : values) {

            String s = t.toString();
            if (s.startsWith("@")) {
                grandparent.add(new Text(s.substring(1)));
            } else {
                grandchild.add(new Text(s));
            }
        }

        for (Text text : grandchild) {
            for (Text value : grandparent) {
                context.write(text, value);
            }
        }
    }
}
