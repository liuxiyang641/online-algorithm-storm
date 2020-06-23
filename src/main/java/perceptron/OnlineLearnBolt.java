package perceptron;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by liuxiyang
 */
public class OnlineLearnBolt extends BaseRichBolt {
    private OutputCollector collector;
    private final double[] weights = new double[4];// 3 for input variables and one for bias
    private double localError = 0, globalError = 0;
    private double LEARNING_RATE = 0.1;
    private int theta = 0;
    private int instanceNum;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        weights[0] = 0;// w1
        weights[1] = 0;// w2
        weights[2] = 0;// w3
        weights[3] = 0;// this is the bias
        instanceNum = 0;
        this.collector = collector;
    }

    public void execute(Tuple input) {
        // 在线训练10次，进行评估
        if (instanceNum % 10 == 0)
            collector.emit(new Values((Object) new double[]{
                    weights[0], weights[1], weights[2], weights[3]
            }));
        double[] cooWithLabel = (double[]) input.getValueByField("coo");
        // 计算预测结果
        int predictOutput = calculateOutput(theta, weights, cooWithLabel[0], cooWithLabel[1], cooWithLabel[2]);
        // 预测结果与实际标签之间的差异
        localError = cooWithLabel[3] - predictOutput;
        // 更新weight和bias
        weights[0] += LEARNING_RATE * localError * cooWithLabel[0];
        weights[1] += LEARNING_RATE * localError * cooWithLabel[1];
        weights[2] += LEARNING_RATE * localError * cooWithLabel[2];
        weights[3] += LEARNING_RATE * localError;
        // 计算全局loss
        globalError += (localError * localError);
        instanceNum += 1;
        /* 均方误差 */
        System.out.println("Instance " + instanceNum +
                ": (" + cooWithLabel[0] + ", " + cooWithLabel[1] + ", " + cooWithLabel[2] + ") " +
                "label: " + (int) cooWithLabel[3] + " predict: " + predictOutput +
                " RMSE = " + Math.sqrt(globalError / instanceNum));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("weights"));
    }

    private double initialWeight(int min, int max) {
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
