package org.simplesql.om.data.stores.berkeley;

import java.io.File;
import java.util.Comparator;

import org.simplesql.data.CellTuple;
import org.simplesql.data.SimpleCellKey;

import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;

/**
 * 
 * Responsible for managing global db instances and creating DBs.<br/>
 * Only create one instance of this class.<br/>
 * All databases returned are temporary.<br/>
 */
public class DBManager {

	private static final String NAME_CATALOG_DB = "dbcatalog";

	final File baseDir;
	final EnvironmentConfig config;
	final Environment env;
	final DatabaseConfig catalogConfig;
	final DatabaseConfig dbConfig;
	final Database catalogDB;
	final ClassCatalog classCatalog;

	SecondaryConfig secDbConfig;

	final SerialBinding<SimpleCellKey> keyBinding;
	final SerialBinding<CellTuple> valueBinding;

	/**
	 * 
	 * @param baseDir
	 *            File directories are created if not exist.
	 */
	public DBManager(File baseDir) {
		this.baseDir = baseDir;

		if (!baseDir.exists())
			baseDir.mkdirs();

		// create global environment
		config = new EnvironmentConfig();
		config.setTransactional(false);
		config.setAllowCreate(true);
		env = new Environment(baseDir, config);

		// create serialization catalog db
		catalogConfig = new DatabaseConfig();
		catalogConfig.setTransactional(false);
		catalogConfig.setAllowCreate(true);

		// create global db config
		dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(false);
		dbConfig.setTemporary(true);
		dbConfig.setAllowCreate(true);

		catalogDB = env.openDatabase(null, NAME_CATALOG_DB, catalogConfig);
		classCatalog = new StoredClassCatalog(catalogDB);

		keyBinding = new SerialBinding<SimpleCellKey>(classCatalog,
				SimpleCellKey.class);
		valueBinding = new SerialBinding<CellTuple>(classCatalog,
				CellTuple.class);

	}

	/**
	 * Creates a Database instance by calling <code>env.openDatabase(..);</code><br/>
	 * <b>Transactions</b><br/>
	 * Note that the database opened is not transactional
	 * 
	 * @param name
	 *            database name
	 * @return Database this database instance should be closed after use.
	 */
	public Database openDatabase(String name) {
		return env.openDatabase(null, name, dbConfig);
	}

	/**
	 * Open a secondary database for the Database db.
	 * 
	 * @param db
	 * @param keyCreator
	 * @return
	 */
	public SecondaryDatabase openSecondaryDatabase(Database db,
			SecondaryKeyCreator keyCreator) {
		SecondaryConfig secDbConfig = new SecondaryConfig();
		secDbConfig.setAllowCreate(true);
		// Duplicates are required for secondary databases.
		secDbConfig.setSortedDuplicates(true);

		secDbConfig.setKeyCreator(keyCreator);

		return env.openSecondaryDatabase(null, db.getDatabaseName() + "_sec_"
				+ System.nanoTime(), db, secDbConfig);

	}

	/**
	 * Same as openDatabase but allows a BTree comparator to be specified.
	 * 
	 * @param name
	 * @param comp
	 * @return
	 */
	public Database openDatabase(String name, Comparator<byte[]> comp) {
		// create global db config
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(false);
		dbConfig.setTemporary(true);
		dbConfig.setAllowCreate(true);
		dbConfig.setBtreeComparator(comp);

		return env.openDatabase(null, name, dbConfig);
	}

	public StoredSortedMap<SimpleCellKey, CellTuple> createMap(Database db) {
		return new StoredSortedMap<SimpleCellKey, CellTuple>(db, keyBinding,
				valueBinding, true);
	}

	public void destroyDb(String name) {
		env.removeDatabase(null, name);
	}

	public void close() {
		try {
			catalogDB.close();
			classCatalog.close();
		} finally {
			// should always close the environment
			env.close();
		}
	}

}
