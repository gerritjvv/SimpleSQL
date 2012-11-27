package org.simplesql.parser;

import java.util.List;
import java.util.Set;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.DataSource;
import org.simplesql.data.RangeGroups;
import org.simplesql.data.TransformFunction;

public interface SQLExecutor {

	/**
	 * 
	 * @param source
	 *            DataSource to read cells from
	 * 
	 * @param keyParser
	 *            keyParser
	 * 
	 * @param store
	 *            AggregateStore The AggregateStore implementation to use to
	 *            store aggregate data.
	 * @param progressListener
	 *            Progress to update progress to.
	 */
	@SuppressWarnings("rawtypes")
	void pump(DataSource source, AggregateStore store, Progress progressListener);

	int getLimit();

	int[] getGroupOrderIndexes();

	int[] getSelectOrderIndexes();
	/**
	 * Get the order direction that was specified by the select order by
	 * statement
	 * 
	 * @return
	 */
	AggregateStore.ORDER getOrder();
	
	/**
	 * Returns all of the columns used from the table
	 * 
	 * @return Set of String
	 */
	Set<String> getColumnsUsed();

	WhereFilter getWhereFilter();

	RangeGroups getRangeGroups();

	Set<String> getSelectOrderByColumns();
	Set<String> getGroupOrderByColumns();

	/**
	 * 
	 * @return List of transforms
	 */
	List<TransformFunction> getTransforms();

	/**
	 * 
	 * Each Progress update will be sent an object of this type. Contains the
	 * values about the execution progress.
	 * 
	 */
	public static class ProgressContext {

		int recordsCompleted;

		public int getRecordsCompleted() {
			return recordsCompleted;
		}

		public void setRecordsCompleted(int recordsCompleted) {
			this.recordsCompleted = recordsCompleted;
		}

	}

	/**
	 * Interface to listen to progress of the SQLExecutor as it processes data.
	 * 
	 * 
	 */
	public static interface Progress {

		void update(ProgressContext ctx);

	}
	

}
