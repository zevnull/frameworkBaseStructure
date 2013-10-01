package com.ddn.autotest.test.CallHomeClient.Emails;

import com.ddn.autotest.mongodb.DBConnector;

public class CallhomeDB extends DBConnector
{
	
	public CallhomeDB(String baseName) throws Exception
	{
		super(baseName);
	}

	public CallhomeDB(String baseName, String collectionName) throws Exception
	{
		super(baseName, collectionName);
	}

	
	public CallhomeDB(String baseName, String collectionName, String IP, String port) throws Exception
	{
		super(baseName, collectionName, IP, port);
	}


	/**
	 * Get Task Id from Event body from response
	 * 
	 * @param response
	 * @return requried task id
	 */
	public String getTaskIdFromEvent(String response)
	{
	    int startIndex = response.indexOf("\"$ref\" : \"task\" , \"$id\"");
	    int endIndex = response.indexOf("\"}", startIndex);
	    return response.substring(startIndex + 27, endIndex);
	}
}
