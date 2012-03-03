package org.simplesql.om.event;

import java.util.Arrays;

import org.simplesql.om.ClientInfoTemplate.ClientInfo;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.RequestTemplate.QueryParam;
import org.simplesql.om.RequestTemplate.Request;

/**
 * 
 * A contains for the Request and all related objects when a Request is created.
 * 
 */
public class RequestEvent implements Event<Request>{

	Request request;
	ClientInfo clientInfo;

	Projection projection;

	/**
	 * A query param array of array is such that parallel insertion can happen
	 * without need for syncrhonization.
	 */
	final QueryParam[][] addedValuesParams;
	final String[][] excludes;

	boolean valid = true;

	@Override
	public Request getData(){
		return request;
		
	}
	public String[][] getExcludes() {
		return excludes;
	}

	public void setExcludes(int i, String[] excludes) {
		this.excludes[i] = excludes;
	}

	public RequestEvent(int addedValuesParamsSize) {
		addedValuesParams = new QueryParam[addedValuesParamsSize][];
		excludes = new String[addedValuesParamsSize][];
	}

	public QueryParam[][] getAddedValuesParams() {
		return addedValuesParams;
	}

	/**
	 * 
	 * @param i
	 *            each thread is assigned an index in which it will add its
	 *            values
	 * @param addedValuesParams
	 */
	public void setAddedValuesParams(int i, QueryParam[] addedValuesParams) {
		this.addedValuesParams[i] = addedValuesParams;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request, ClientInfo info,
			Projection projection) {
		if (request == null || info == null || projection == null) {
			throw new NullPointerException(
					"Request, ClientInfo or Projection is null request: "
							+ request + " clientInfo: " + clientInfo
							+ " projection: " + projection);
		}

		this.request = request;
		this.clientInfo = info;
		this.projection = projection;
		// cleanup added value params and excludes;
		Arrays.fill(addedValuesParams, null);
		Arrays.fill(excludes, null);
	}

	public Projection getProjection() {
		return projection;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public void clear() {
		request = null;
		clientInfo = null;
		projection = null;

	}

}
