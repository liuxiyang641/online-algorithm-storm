package perceptron;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by liuxiyang
 */
public class RandomCooSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private int NUM_INSTANCES = 100;
    private int CURRENT_NUM = 0;

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void nextTuple() {
        if (CURRENT_NUM >= NUM_INSTANCES)
            return;

        //three variables (features)
        double[] coo = new double[4];
        coo[3] = (int) (Math.random() * 2);
        if (coo[3] == 0) {
            coo[0] = randomNumber(-1, 3);
            coo[1] = randomNumber(-4, 2);
            coo[2] = randomNumber(-3, 5);
        } else {
            coo[0] = randomNumber(5, 10);
            coo[1] = randomNumber(2, 8);
            coo[2] = randomNumber(2, 9);
        }
        CURRENT_NUM += 1;
        this.collector.emit(new Values((Object) coo));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("coo"));
    }

    private double randomNumber(int min, int max) {
        DecimalFormat df = new DecimalFormat("#.####");
        double d = min + Math.random() * (max - min);
        String s = df.format(d);
        return Double.parseDouble(s);
    }
}
