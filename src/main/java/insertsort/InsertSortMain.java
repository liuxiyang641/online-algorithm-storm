package insertsort;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

/**
 * Created by liuxiyang
 */
public class InsertSortMain {
    public static void main(String[] args) //throws Exception
    {
        NumberSpout numberSpout = new NumberSpout();
        InsertSortBolt insertSortBolt = new InsertSortBolt();

        TopologyBuilder builder = new TopologyBuilder();//创建了一个TopologyBuilder实例
        //TopologyBuilder提供流式风格的API来定义topology组件之间的数据流
        builder.setSpout("number-spout", numberSpout);
        builder.setBolt("insert-sort-bolt", insertSortBolt).shuffleGrouping("number-spout");

        //Config类是一个HashMap<String,Object>的子类，用来配置topology运行时的行为
        Config config = new Config();
        config.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        //本地提交
        cluster.submitTopology("insert-sort", config, builder.createTopology());
        Utils.sleep(10000);
        cluster.killTopology("insert-sort");
        cluster.shutdown();
    }
}
