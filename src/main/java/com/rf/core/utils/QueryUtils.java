package com.rf.core.utils;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rf.core.website.constants.dbQueries.DBQueries_RFO;

public class QueryUtils {
	private static final Logger logger = LogManager
			.getLogger(QueryUtils.class.getName());


	private static List<Map<String, Object>> randomConsultantList =  null;
	private static List<Map<String, Object>> randomPCList =  null;
	private static List<Map<String, Object>> randomRCList =  null;
	
	public static String getRandomActiveConsultantFromDB(String RFO_DB, String query,String countryId){
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(query,countryId),RFO_DB);
		String username= (String) getValueFromQueryResult(randomConsultantList, "UserName");
		logger.info("Consultant username fetched from DB is "+username);
		return username;
	}
	
	public static String getRandomActiveConsultantWithPWSFromDB(String RFO_DB, String query,String env,String site,String country,String countryId){
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(query,site,country,countryId),RFO_DB);
		String username= (String) getValueFromQueryResult(randomConsultantList, "UserName");
		logger.info("Consultant username fetched from DB is "+username);
		return username;
	}
	
	public static String getRandomActivePCFromDB(String RFO_DB, String query,String countryId){
		randomPCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(query,countryId),RFO_DB);
		String username= (String) getValueFromQueryResult(randomPCList, "UserName");
		logger.info("PC username fetched from DB is "+username);
		return username;
	}
	
	public static String getRandomActiveRCFromDB(String RFO_DB, String query,String countryId){
		randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(query,countryId),RFO_DB);
		String username= (String) getValueFromQueryResult(randomRCList, "UserName");
		logger.info("RC username fetched from DB is "+username);
		return username;
	}
	
	public static Object getValueFromQueryResult(List<Map<String, Object>> userDataList,String column){
		Object value = null;
		for (Map<String, Object> map : userDataList) {
			value = map.get(column);                                            
		}
		logger.info("Data returned by query: "+ value);
		return value;
	}
}
