package perceptron;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by liuxiyang
 */
public class OnlineEvalBolt extends BaseRichBolt {
    private int evalNo = 0;
    private final int NUM_INSTANCES = 100;
    private final double[] x = new double[NUM_INSTANCES];
    private final double[] y = new double[NUM_INSTANCES];
    private final double[] z = new double[NUM_INSTANCES];
    private final int[] labels = new int[NUM_INSTANCES];

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        for (int j = 0; j < NUM_INSTANCES; j++) {
            labels[j] = (int) (Math.random() * 2);
            if (labels[j] == 0) {
                x[j] = randomNumber(-5, 4);
                y[j] = randomNumber(-5, 1);
                z[j] = randomNumber(-5, 5);
            } else {
                x[j] = randomNumber(5, 6);
                y[j] = randomNumber(2, 5);
                z[j] = randomNumber(3, 5);
            }
        }
    }

    public void execute(Tuple input) {
        double[] weights = (double[]) input.getValueByField("weights");
        double correctNum = 0.0;
        System.out.println("w1: " + weights[0] + " w2: " + weights[1] + " w3: " + weights[2] + " b: " + weights[3]);
        for (int j = 0; j < NUM_INSTANCES; j++) {
            int output = calculateOutput(0, weights, x[j], y[j], z[j]);
            if (output == labels[j])
                correctNum += 1;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Eval Accuracy " + evalNo + ": " + df.format(correctNum / NUM_INSTANCES));
        evalNo += 1;
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

    private double randomNumber(int min, int max) {
        DecimalFormat df = new DecimalFormat("#.####");
        double d = min + Math.random() * (max - min);
        String s = df.format(d);
        return Double.parseDouble(s);
    }

    private int calculateOutput(int theta, double[] weights, double x, double y, double z) {
        double sum = x * weights[0] + y * weights[1] + z * weights[2] + weights[3];
        return (sum >= theta) ? 1 : 0;
    }
}
