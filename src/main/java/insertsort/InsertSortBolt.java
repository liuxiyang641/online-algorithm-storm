package insertsort;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

/**
 * Created by liuxiyang
 */
public class InsertSortBolt extends BaseRichBolt {
    private Link link;

    /**
     * prepare()方法类似于ISpout 的open()方法。
     * 这个方法在blot初始化时调用，可以用来准备bolt用到的资源,比如数据库连接。
     * 本例子不需要太多额外的初始化,
     */
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.link = new Link();
    }

    /**
     * InsertSortBolt核心功能是在类IBolt定义execute()方法，这个方法是IBolt接口中定义。
     * 每次Bolt从流接收一个订阅的tuple，都会调用这个方法。
     * 本例中,将收到的数字直接插入结果链表
     */
    public void execute(Tuple input) {
        int number = Integer.parseInt(input.getStringByField("number"));
        link.addNode(number);
    }

    /**
     * cleanup是IBolt接口中定义
     * Storm在终止一个bolt之前会调用这个方法
     * 本例我们利用cleanup()方法在topology关闭时输出最终的计数结果
     * 通常情况下，cleanup()方法用来释放bolt占用的资源，如打开的文件句柄或数据库连接
     * 但是当Storm拓扑在一个集群上运行，IBolt.cleanup()方法不能保证执行（这里是开发模式，生产环境不要这样做）。
     */
    public void cleanup() {
        System.out.println("---------- FINAL COUNTS -----------");
        this.link.printList();
        System.out.println("----------------------------");
        long endTime = System.currentTimeMillis();
        System.out.println("finish time：" + (endTime) + "ms");
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }


}
