package org.simplesql.om;

import org.simplesql.om.ClientInfoTemplate.Column;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.util.Bytes;
import org.streams.commons.app.ApplicationService;

/**
 * 
 * Basic interface modelled on HBase column and column family based storage.
 * 
 */
public abstract class TableService implements ApplicationService{

	public static final String PROP_TABLE_POOL_MAX = "table.pool.max";
	
	public static final String PROP_TABLE_NAME = "client.infoTable";
	public static final String PROP_PROJECTIONS_NAME = "client.projectionsTable";

	public static final String DEFAULT_TABLE_NAME = "clientInfo";
	public static final String DEFAULT_PROJECTIONS_TABLE_NAME = "clientprojections";

	public static final String PROP_CLIENT_COMPRESSION = "client.compression";

	public static final byte[] COL_FAM_INFO = Bytes.toBytes("info");

	public static final byte[] COL_FAM_ID_INFO = Bytes.toBytes("ids");
	public static final byte[] COL_PID = Bytes.toBytes("pId");

	public static final byte[] COL_INFO = Bytes.toBytes("info");

	public static final byte[] FAMILY_HOURLY = Bytes.toBytes("HOURLY");
	public static final byte[] FAMILY_DAILY = Bytes.toBytes("DAILY");

	
	public static final String PROP_PROJECTION_DATA_TABLE = "projectionsDataTable";
	public static final String PROJECTION_TABLE_DEFAULT_NAME = "projectionsdata";
	
	public static final byte[] FAMILY_UNIQUE_HOURLY = Bytes
			.toBytes("UNIQUE_HOURLY");

	public static final byte[] FAMILY_UNIQUE_DAILY = Bytes
			.toBytes("UNIQUE_DAILY");

	public static final String PROP_DATA_COMPRESSION = "data.compression";

	 
	/**
	 * clientProjections table: key = clientId, name STRING length = 255
	 */
	private static final Projection projectionsKeySchema = Projection
			.newBuilder()
			.setName("clientprojections")
			.addColumn(
					Column.newBuilder().setName("clientId").setOrder(0)
							.setType("LONG").build())
			.addColumn(
					Column.newBuilder().setName("name").setOrder(1)
							.setType("STRING").setWidth(255).build()).build();

	/**
	 * clientInfo table: key = clientId:Long
	 */
	private static final Projection clientInfoKeySchema = Projection
			.newBuilder()
			.setName("clientprojections")
			.addColumn(
					Column.newBuilder().setName("clientId").setOrder(0)
							.setType("LONG").build()).build();


	public Projection getProjectionsKeySchema(){
		return projectionsKeySchema;
	}
	
	public Projection getClientInfoKeySchema(){
		return clientInfoKeySchema;
	}
	
}
