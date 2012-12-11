package org.simplesql.kafka;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.netflix.curator.test.TestingServer;

public class ZookeeperTestSuite {

	static final File baseDir = new File("target/test/zk");
	static final File kafkaBaseDir = new File("target/test/kafka");

	static int zkClientPort;
	static TestingServer zkServer;
	static KafkaServer server;
	static Properties props;

	@BeforeClass
	public static void setupZK() throws Throwable {

		deleteCreate(baseDir);
		deleteCreate(kafkaBaseDir);

		int tickTime = 2000;
		zkServer = new TestingServer();
		
		zkClientPort = zkServer.getPort();
		props = getConfig(zkClientPort);
		
		server = new KafkaServer(new KafkaConfig(props));
		server.startup();
		
		System.out.println("Zookeeper and Kafka Started");
		
	}

	
	private static void deleteCreate(File basedir2) throws IOException {
		if (basedir2.exists()) {
			FileUtils.deleteDirectory(basedir2);
		}

		basedir2.mkdirs();
	}

	private static Properties getConfig(int zkClientPort) {

		Properties props = new Properties();
		props.put("log.dir", kafkaBaseDir.getAbsolutePath());
		props.put("enable.zookeeper", "true");
		props.put("zk.connect", "127.0.0.1:" + zkClientPort);
		props.put("brokerid", "0");
		props.put("groupid", "1");
		props.put("serializer.class", "kafka.serializer.StringEncoder");

		return props;
	}

	@AfterClass
	public static void shutdownZK() throws IOException {
		try {
			if (server != null)
				server.shutdown();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		zkServer.stop();

	}

}
