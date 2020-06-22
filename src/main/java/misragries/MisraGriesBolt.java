package misragries;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map.Entry;
import java.util.Iterator;

/**
 * Created by liuxiyang
 */
public class MisraGriesBolt extends BaseRichBolt {
    // 计数器集合大小
    private int counterSetSize;
    // 计数器集合
    private Hashtable<String, AtomicLong> countersMap;
    // 当前计数器个数
    private AtomicLong numberOfElements = new AtomicLong(0);

    /**
     * prepare()方法类似于ISpout 的open()方法。
     * 这个方法在blot初始化时调用，可以用来准备bolt用到的资源,比如数据库连接。
     * 本例子不需要太多额外的初始化, 获取初始的计数器集合大小
     */
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        counterSetSize = Integer.parseInt(stormConf.get("counter-set-size").toString());
        countersMap = new Hashtable<String, AtomicLong>();
    }

    /**
     * 执行Bolt
     */
    public void execute(Tuple input) {
        String ele = input.getStringByField("element");
        this.countValue(ele);
    }

    /**
     * 结束时输出
     */
    public void cleanup() {
        System.out.println("---------- FINAL OUTPUTS -----------");
        this.printCounterState();
        System.out.println("Most frequency element is " + this.getMaxFrequency());
        System.out.println("----------------------------");
        long endTime = System.nanoTime();
        System.out.println("Finish time： " + endTime / 1000 + "us");
    }

    /**
     * 进行Mrsra-Gries计数
     *
     * @param element 接收到的新元素
     */
    private void countValue(String element) {
        // 获取计数器
        AtomicLong counter = countersMap.get(element);
        //  如果有对应的计数器
        if (counter != null) {
            // 计数器+1
            counter.incrementAndGet();
        } else { // 没有对应的计数器
            // 如果计数器的数量没有达到上限
            if (numberOfElements.get() < counterSetSize) {
                addCounter(element);
            } else { // 所有计数器值-1
                Iterator<Entry<String, AtomicLong>> entrySetIterator = countersMap.entrySet().iterator();

                while (entrySetIterator.hasNext()) {
                    Entry<String, AtomicLong> entry = entrySetIterator.next();
                    // 如果某个计数器-1后值<0，移去该计数器
                    if (entry.getValue().decrementAndGet() <= 0) {
                        entrySetIterator.remove();
                        numberOfElements.decrementAndGet();
                    }
                }

                if (numberOfElements.get() < counterSetSize) {
                    addCounter(element);
                }
            }

        }
    }

    /**
     * 增加新计数器
     *
     * @param element 新元素
     */
    private synchronized void addCounter(String element) {
        if (numberOfElements.get() < counterSetSize) {
            numberOfElements.incrementAndGet();
            countersMap.put(element, new AtomicLong(1));
        }
    }

    /**
     * 获取最频繁元素
     *
     * @return 最频繁元素 String
     */
    private String getMaxFrequency() {
        long maxCounter = 0;
        String maxFrequency = null;

        for (String key : countersMap.keySet()) {
            AtomicLong decremntCounter = countersMap.get(key);
            if (decremntCounter != null && decremntCounter.get() > maxCounter) {
                maxCounter = decremntCounter.get();
                maxFrequency = key;
            }
        }

        return maxFrequency;
    }

    /**
     * 打印当前计数器集合状态
     */
    private void printCounterState() {
        // 遍历所有计数器
        System.out.println("ALL frequency elements:");
        for (Entry<String, AtomicLong> entry : countersMap.entrySet()) {
            System.out.println("element: " + entry.getKey() + "\tcounts:" + entry.getValue());
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }
}
