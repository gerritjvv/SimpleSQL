package org.simplesql.om.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.simplesql.om.ClientInfoTemplate.Column;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.RequestTemplate.QueryParam;
import org.simplesql.om.RequestTemplate.Request;
import org.simplesql.om.key.KeyWriterReader;

/**
 * 
 * HelperClass to get and do client projections
 * 
 */
public class ClientProjections {

	public static final Projection getProjection(List<Projection> projections,
			String tableName) {
		int len = projections.size();

		for (int i = 0; i < len; i++) {
			Projection projection = projections.get(i);
			if (projection.getName().equalsIgnoreCase(tableName))
				return projection;
		}

		return null;
	}

	public static final KeyWriterReader buildProjectionKey(
			Projection projection, Request request,
			QueryParam[][] addedValuesParams) {

		KeyWriterReader krw = new KeyWriterReader(projection);

		Map<String, String> paramMap = buildParamMap(request.getParamsList());

		List<Column> columns = projection.getColumnList();

		Set<String> colValuesNotfound = new HashSet<String>();

		for (int i = 0; i < columns.size(); i++) {
			final Column column = columns.get(i);
			final String columnName = column.getName();

			final String colValue = paramMap.get(columnName);

			if (colValue == null) {
				if (columnName.equals("host")) {
					krw.write(columnName, request.getHost());

				} else if (columnName.equals("ip")) {
					krw.write(columnName, request.getIp());

				} else if (columnName.equals("referer")) {
					krw.write(columnName, request.getReferer());
				} else if (columnName.equals("ua")) {
					krw.write(columnName, request.getUa());
				} else if (columnName.equals("clientId")) {
					;// ignore
				} else if (columnName.equals("projection")) {
					;// ignore
				} else {

					colValuesNotfound.add(columnName);
				}

			} else {
				// write string data to the correct column format
				krw.write(columnName, colValue);
			}

		}

		// if any colValuesNotfound then we look into the added values
		if (colValuesNotfound.size() > 0) {
			Map<String, String> addedMap = buildAddedValueMap(addedValuesParams);

			for (final String columnName : colValuesNotfound) {

				final String value = addedMap.get(columnName);
				if (value != null) {
					krw.write(columnName, value);
				}

			}

		}

		return krw;
	}

	// public static final String[] createProjectionKeys(ClientInfo clientInfo,
	// Request request, QueryParam[][] addedValuesParams,
	// String[][] excludes) {
	//
	// int projectionCount = clientInfo.getProjectionCount();
	// Set<String> keys = new HashSet<String>(projectionCount);
	//
	// List<Projection> projections = clientInfo.getProjectionList();
	//
	// // build excludes
	// // Set<String> excludesSet = buildExcludesSet(excludes);
	// Map<String, String> addedMap = buildAddedValueMap(addedValuesParams);
	// Map<String, String> paramMap = buildParamMap(request.getParamsList());
	//
	// StringBuilder buff = new StringBuilder(100);
	// StringBuilder valBuff = new StringBuilder(100);
	//
	// // for each projection
	// for (Projection projection : projections) {
	// int i = 0;
	// boolean broken = false;
	//
	// for (String pair : projection.getPairList()) {
	// String val = paramMap.get(pair);
	//
	// if (val == null && (val = addedMap.get(pair)) == null) {
	// // if the values are not found in the request or added map
	// // skip this projection
	// broken = true;
	// break;
	// }
	//
	// if (i++ != 0) {
	// buff.append("_");
	// valBuff.append("_");
	// }
	//
	// buff.append(pair);
	// valBuff.append(val);
	//
	// }
	//
	// // if complete then add the projection
	// if (!broken) {
	// buff.append("_").append(valBuff.toString());
	// keys.add(buff.toString());
	// }
	//
	// buff.delete(0, buff.length());
	// valBuff.delete(0, valBuff.length());
	//
	// }
	//
	// return keys.toArray(new String[] {});
	// }
	//
	/**
	 * Create map from query params
	 * 
	 * @param paramsList
	 * @return
	 */
	private static Map<String, String> buildParamMap(List<QueryParam> paramsList) {
		final Map<String, String> paramMap;

		if (paramsList != null && paramsList.size() > 0) {
			final int paramsSize = paramsList.size();

			paramMap = new HashMap<String, String>(paramsSize);
			for (QueryParam param : paramsList) {
				paramMap.put(param.getKey(), param.getValue());
			}
		} else {
			paramMap = new HashMap<String, String>(0);
		}

		return paramMap;
	}

	/**
	 * Create Added Value Map
	 * 
	 * @param addedValuesParams
	 * @return
	 */
	private static final Map<String, String> buildAddedValueMap(
			QueryParam[][] addedValuesParams) {

		final Map<String, String> map;

		if (addedValuesParams != null && addedValuesParams.length > 0) {
			map = new HashMap<String, String>(addedValuesParams.length);
			for (int i = 0; i < addedValuesParams.length; i++) {
				final QueryParam[] innerParams = addedValuesParams[i];
				if (innerParams != null) {
					for (int x = 0; x < innerParams.length; x++) {
						final QueryParam innerParam = innerParams[x];
						map.put(innerParam.getKey(), innerParam.getValue());
					}
				}
			}
		} else {
			map = new HashMap<String, String>(0);
		}

		return map;
	}
	//
	// private static final Set<String> buildExcludesSet(String[][] excludes) {
	// Set<String> excludesSet = null;
	//
	// if (excludes != null && excludes.length > 0) {
	// excludesSet = new HashSet<String>(excludes.length);
	//
	// for (int i = 0; i < excludes.length; i++) {
	// String[] excludesInner = excludes[i];
	// if (excludesInner != null) {
	// for (int x = 0; x < excludesInner.length; x++)
	// excludesSet.add(excludesInner[x]);
	// }
	// }
	//
	// } else {
	// excludesSet = new HashSet<String>(0);
	// }
	//
	// return excludesSet;
	// }
	//
	// /**
	// * Here we determine the Projection that includes all of the columns used
	// in
	// * the query to the neares fit.
	// *
	// * @param projections
	// * @param columnsUsed
	// * @return Projection
	// */
	// public static final Projection getBestFitProjection(
	// List<Projection> projections, String[] columnsUsed) {
	//
	// int colsSize = columnsUsed.length;
	//
	// Projection projectionFound = null;
	//
	// for (Projection projection : projections) {
	// int paircount = projection.getPairCount();
	//
	// if (projection.getPairCount() == colsSize) {
	// // if projection contains the pairs of same size as columns
	// // used.
	// // and projection contains all columns used
	// // use projection
	// if (containsAllColumns(projection, columnsUsed)) {
	// projectionFound = projection;
	// break;
	// }
	//
	// } else if (paircount > colsSize) {
	// // if the pair count is larger than columns used
	// // two checks, use the shortest projection that contains the
	// // columns used.
	// if (containsAllColumns(projection, columnsUsed)) {
	// if (projectionFound == null) {
	// projectionFound = projection;
	// } else {
	// if (paircount < projectionFound.getPairCount()) {
	// projectionFound = projection;
	// }
	// }
	// }
	// }
	//
	// }
	//
	// return projectionFound;
	//
	// }
	//
	// /**
	// *
	// * @param projection
	// * @param columnsUsed
	// * @return boolean True if the columns are all present in the projection
	// */
	// public static final boolean containsAllColumns(Projection projection,
	// String[] columnsUsed) {
	//
	// final List<String> pairs = projection.getPairList();
	// final int len = columnsUsed.length;
	//
	// // loop till a none match is found
	// boolean found = false;
	// for (int i = 0; i < len; i++) {
	// if (!pairs.contains(columnsUsed[i])) {
	// found = false;
	// break;
	// } else {
	// found = true;
	// }
	// }
	//
	// return found;
	// }

}
