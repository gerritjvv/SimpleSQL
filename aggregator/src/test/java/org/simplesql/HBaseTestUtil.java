package org.simplesql;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.LocalHBaseCluster;
import org.apache.hadoop.hbase.zookeeper.MiniZooKeeperCluster;

/**
 * 
 * Utility for running hbase
 *
 */
public class HBaseTestUtil {

	int port = 60000;
	int zookeeperClientPort;

	String master = "local:" + port;

	LocalHBaseCluster hbaseCluster;
	Configuration conf;

	MiniZooKeeperCluster miniZoo;

	public int getZookeeperClientPort() {
		return zookeeperClientPort;
	}

	public int getPort() {
		return port;
	}

	public String getMaster() {
		return master;
	}

	public Configuration getConf() {
		return conf;
	}

	public void start() throws IOException, InterruptedException {

		conf = HBaseConfiguration.create();
		miniZoo = new MiniZooKeeperCluster(conf);
		File zoopath = new File("./target/minizoo");
		zoopath.mkdirs();

		File hbasedir = new File("./target/hbase/data");
		hbasedir.mkdirs();
		File hbasetmpdir = new File(".target/hbase/tmp");
		hbasetmpdir.mkdirs();

		zookeeperClientPort = miniZoo.startup(zoopath);
		conf.setInt("hbase.zookeeper.property.clientPort", zookeeperClientPort);
		conf.set("hbase.rootdir", hbasedir.toURL().toString());
		conf.set("hbase.cluster.distributed", "false");
		conf.set("hbase.tmp.dir", hbasetmpdir.getAbsolutePath());

		hbaseCluster = new LocalHBaseCluster(conf);
		hbaseCluster.startup();
	}

	public void shutdown() throws IOException {
		if (hbaseCluster != null) {
			hbaseCluster.shutdown();
			miniZoo.shutdown();
		}
	}

}
