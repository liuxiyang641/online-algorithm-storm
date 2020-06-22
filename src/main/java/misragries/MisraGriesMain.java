package misragries;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

/**
 * Created by liuxiyang
 */
public class MisraGriesMain {
    public static void main(String[] args) //throws Exception
    {
        ElementSpout elementSpout = new ElementSpout();
        MisraGriesBolt misraGriesBolt = new MisraGriesBolt();
        // 创建一个TopologyBuilder实例
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("element-spout", elementSpout);
        builder.setBolt("mirsa-gries-bolt", misraGriesBolt).shuffleGrouping("element-spout");

        // Config类是一个HashMap<String,Object>的子类，用来配置topology运行时的行为
        Config config = new Config();
        config.setDebug(false);
        // 设置计数器集合大小
        config.put("counter-set-size", 2);

        LocalCluster cluster = new LocalCluster();
        // 本地提交
        cluster.submitTopology("mirsa-gries", config, builder.createTopology());
        Utils.sleep(10000);
        cluster.killTopology("mirsa-gries");
        cluster.shutdown();
    }
}
