package org.simplesql.om.helpcry;

import org.simplesql.om.RequestTemplate.Request;


public interface HelpCryService {

	/**
	 * The plugin wrapper cries help if it had any errors during plugin.send with a batch of data.
	 * @param pluginName
	 * @param request
	 * @param start
	 * @param max
	 */
	public void cryHelp(String pluginName, Request[] request, int start, int max);
	
}
