package org.wildfly.bpms.frontend.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.exception.BpmsFrontendException;

public class FrontendUtil {

	private static Logger logger = LoggerFactory.getLogger(FrontendUtil.class);

	public static List<String> parseStringToList(String values, String separator) {
		logger.debug("Entering parseStringToList");
		List<String> resultList = new ArrayList<>(1);

		String[] valueStrings = values.split(separator);
		Arrays.asList(valueStrings);

		logger.debug("Leaving parseStringToList");
		return resultList;
	}

	public static Date parseStringToDate(String dateString) throws BpmsFrontendException {
		logger.debug("Executing parseStringToDate");
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date finalDate;
		try {
			finalDate = df.parse(dateString);
		} catch (ParseException e) {
			String errorMessage = String.format("Error parsing expiration date %s, error was: %n%s%n", dateString,
					e.getMessage());
			throw new BpmsFrontendException(errorMessage);
		}

		return finalDate;
	}

	public static Map<String, Object> parseStringToMapStringObject(String values, String strSeparator,
			String objSeparator) throws BpmsFrontendException {
		logger.debug("Entering parseStringToMapStringObject");
		String[] valuesStrings = values.split(strSeparator);
		Map<String, Object> valuesMap = new HashMap<>(1);

		for (int i = 0; i < valuesStrings.length; i++) {
			String[] keyValue = valuesStrings[i].split(objSeparator);
			Object value;

			try {
				value = Class.forName(keyValue[1]).newInstance();

				valuesMap.put(keyValue[0], value);
			} catch (ClassNotFoundException e) {
				String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", keyValue[1],
						e.getMessage());
				throw new BpmsFrontendException(errorMessage);
			} catch (InstantiationException e) {
				String errorMessage = String.format("Error instantiating class %s. Error is:%n%s%n", keyValue[1],
						e.getMessage());
				throw new BpmsFrontendException(errorMessage);
			} catch (IllegalAccessException e) {
				String errorMessage = String.format("Error accessing class %s. Error is:%n%s%n", keyValue[1],
						e.getMessage());
				throw new BpmsFrontendException(errorMessage);
			}
		}

		logger.debug("Leaving parseStringToMapStringObject");

		return valuesMap;
	}

	public static Object createObjFromClassName(String className) throws BpmsFrontendException {
		Object resultObj;
		try {
			resultObj = Class.forName(className).newInstance();
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", className,
					e.getMessage());
			throw new BpmsFrontendException(errorMessage);
		} catch (InstantiationException e) {
			String errorMessage = String.format("Error instantiating class %s. Error is:%n%s%n", className,
					e.getMessage());
			throw new BpmsFrontendException(errorMessage);
		} catch (IllegalAccessException e) {
			String errorMessage = String.format("Error accessing class %s. Error is:%n%s%n", className, e.getMessage());
			throw new BpmsFrontendException(errorMessage);
		}

		return resultObj;
	}

}
