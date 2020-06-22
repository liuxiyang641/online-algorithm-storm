package perceptron;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

/**
 * Created by liuxiyang
 */
public class PerceptronMain {
    public static void main(String[] args) {
        RandomCooSpout randomCooSpout = new RandomCooSpout();
        OnlineLearnBolt onlineLearnBolt = new OnlineLearnBolt();
        OnlineEvalBolt onlineEvalBolt = new OnlineEvalBolt();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("random-spout", randomCooSpout);
        builder.setBolt("online-train", onlineLearnBolt).shuffleGrouping("random-spout");
        builder.setBolt("online-eval", onlineEvalBolt).shuffleGrouping("online-train");

        Config config = new Config();
        config.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        // 本地提交
        cluster.submitTopology("perceptron", config, builder.createTopology());
        Utils.sleep(10000);
        cluster.killTopology("perceptron");
        cluster.shutdown();
    }
}
