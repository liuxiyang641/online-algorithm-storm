package insertsort;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * Created by liuxiyang
 */
public class NumberSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private FileReader fileReader;
    private boolean completed = false;

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //定义输出字段描述
        declarer.declare(new Fields("number"));
    }

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        long startTime = System.currentTimeMillis();
        System.out.println("Start reading data：" + (startTime) + "ms");
        this.collector = spoutOutputCollector;
        try {
            this.fileReader = new FileReader("src/main/resources/sortnumbers.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading file src/main/resources/sortnumbers.txt");
        }
    }

    public void nextTuple() {
        if (completed)
            return;
        String str;
        BufferedReader reader = new BufferedReader(fileReader);

        try {
            // 读取所有行
            while ((str = reader.readLine()) != null) {
                /**
                 * 每一行读取的数据向外提交一个数据
                 */
                this.collector.emit(new Values(str));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading tuple", e);
        } finally {
            completed = true;
        }
    }
}
