package bolts;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by tom on 13.01.17.
 */
public class AirportCassandraBolt extends BaseRichBolt{
    OutputCollector _collector;
    Cluster cluster = null;
    Session session = null;


    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        _collector = outputCollector;
            cluster = Cluster.builder()                                                    // (1)
                    .addContactPoint("127.0.0.1")
                    .build();
            session = cluster.connect();                                           // (2)


    }

    @Override
    public void execute(Tuple tuple) {
        String airport = tuple.getStringByField("AirportName");

        session.execute("UPDATE tutorialspoint.airports SET number = number + 1 WHERE airport_name='" + airport + "';");


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("Delay", "From", "To"));
    }
}
