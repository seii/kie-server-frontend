package org.wildfly.bpms.frontend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	/**
	 * Convert a specifically formatted <pre>String
	 * </pre>
	 * into a
	 * 
	 * <pre>{@code List<String>}</pre>
	 * 
	 * @param values
	 * 
	 *            <pre>
	 *            String
	 * 			</pre>
	 * 
	 *            in the format "value1[separator]value2[separator]..."
	 * @param separator
	 * 
	 *            <pre>
	 *            String
	 * 			</pre>
	 * 
	 *            used to separate values
	 * @return
	 * 
	 *         <pre>{@code List<String> } </pre>
	 * 
	 *         created from
	 * 
	 *         <pre>
	 *         String
	 * 		</pre>
	 */
	public static List<String> parseStringToList(String values, String separator) {
		logger.debug("Entering parseStringToList");
		List<String> resultList = new ArrayList<>(1);

		String[] valueStrings = values.split(separator);
		resultList = Arrays.asList(valueStrings);

		logger.debug("Leaving parseStringToList");
		return resultList;
	}

	/**
	 * Convert a
	 * 
	 * <pre>
	 * String
	 * </pre>
	 * 
	 * to a
	 * 
	 * <pre>
	 * Date
	 * </pre>
	 * 
	 * object
	 * 
	 * @param dateString
	 * 
	 *            <pre>
	 *            String
	 * 			</pre>
	 * 
	 *            in the format "MM-dd-yyyy"
	 * @return
	 * 
	 *         <pre>
	 *         Date
	 * 		</pre>
	 * 
	 *         object created from
	 * 
	 *         <pre>
	 *         String
	 * 		</pre>
	 */
	public static Date parseStringToDate(String dateString) throws BpmsFrontendException {
		logger.debug("Executing parseStringToDate");
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date finalDate;
		try {
			finalDate = df.parse(dateString);
		} catch (ParseException e) {
			String errorMessage = String.format("Error parsing expiration date %s, error was: %n%s%n", dateString,
					e.getStackTrace());
			throw new BpmsFrontendException(errorMessage);
		}

		return finalDate;
	}

	/**
	 * Convert a specifically formatted
	 * <pre>
	 * String
	 * </pre>
	 * into a
	 * 
	 * <pre>{@code
	 * Map<String, Object>}
	 * </pre>
	 * 
	 * @param values
	 * 
	 *            <pre>
	 *            String
	 * 			</pre>
	 * 
	 *            in the format
	 *            "key1[objSeparator]value1[strSeparator]key2[objSeparator]value2[strSeparator]..."
	 * @param strSeparator
	 * 
	 *            <pre>
	 *            String
	 * 			</pre>
	 * 
	 *            used to separate objects
	 * @param objSeparator
	 * 
	 *            <pre>
	 *            String
	 * 			</pre>
	 * 
	 *            used to separate key/value pairs
	 * @return
	 * 
	 *         <pre>{@code
	 * Map<String, Object>}
	 *         </pre>
	 * 
	 *         constructed from the provided
	 * 
	 *         <pre>
	 *         String
	 * 		</pre>
	 * 
	 */
	public static Map<String, Object> parseStringToMapStringObject(String values, String strSeparator,
			String objSeparator) throws BpmsFrontendException {
		logger.debug("Entering parseStringToMapStringObject");
		String[] valuesStrings = values.split(strSeparator);
		Map<String, Object> valuesMap = new HashMap<>(1);

		for (int i = 0; i < valuesStrings.length; i++) {
			String[] keyValue = valuesStrings[i].split(objSeparator);
			Object value;

			value = FrontendUtil.createObjFromClassName(keyValue[1]);

			valuesMap.put(keyValue[0], value);
		}

		logger.debug("Leaving parseStringToMapStringObject");

		return valuesMap;
	}

	/**
	 * Accept a class name and convert it to a concrete instance of that class. If
	 * the full package format is not included (i.e. "java.util.List"), will try to
	 * add
	 * 
	 * <pre>
	 * java.lang
	 * </pre>
	 * 
	 * and see if it's a basic type.
	 * 
	 * @param className
	 *            Name of Java class (including package) in
	 * 
	 *            <pre>
	 *            String
	 * 			</pre>
	 * 
	 *            format. Is case-sensitive, and must be exact.
	 * @return
	 * 
	 *         <pre>
	 *         Object
	 * 		</pre>
	 * 
	 *         containing the specified class
	 * 
	 */
	public static Object createObjFromClassName(String className) throws BpmsFrontendException {
		Object resultObj = null;
		try {

			if (className.contains(".")) {
				resultObj = Class.forName(className).newInstance();
			} else {
				String alteredName = "java.lang." + className;
				switch (alteredName) {
				case "java.lang.String":
					resultObj = "";
					break;
				case "java.lang.Boolean":
					resultObj = false;
					break;
				case "java.lang.Character":
					// Why "y"? "Y" not?
					resultObj = 'y';
					break;
				case "java.lang.Double":
					resultObj = 0.0d;
					break;
				case "java.lang.Float":
					resultObj = 0.0f;
					break;
				case "java.lang.Integer":
					resultObj = 0;
					break;
				case "java.lang.Long":
					resultObj = 0L;
					break;
				case "java.lang.Short":
					resultObj = 0;
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n%s%n", className,
					e.getMessage(), e.fillInStackTrace());
			throw new BpmsFrontendException(errorMessage);
		} catch (InstantiationException e) {
			String errorMessage = String.format("Error instantiating class %s. Error is:%n%s%n", className,
					e.getStackTrace());
			throw new BpmsFrontendException(errorMessage);
		} catch (IllegalAccessException e) {
			String errorMessage = String.format("Error accessing class %s. Error is:%n%s%n", className,
					e.getStackTrace());
			throw new BpmsFrontendException(errorMessage);
		}

		return resultObj;
	}

	/**
	 * Imitate a console if no system console is actually being used
	 * 
	 * @param format
	 *            Formatting string, in
	 * 
	 *            <pre>
	 *            String.format()
	 * 			</pre>
	 * 
	 *            style
	 * @param args
	 *            Additional arguments to be used by formatting string
	 * @return Lines read from user input, in
	 * 
	 *         <pre>
	 *         String
	 * 		</pre>
	 * 
	 *         format
	 * 
	 */
	/*
	 * Workaround for Eclipse bug #122429 Code found here:
	 * https://stackoverflow.com/questions/4203646/system-console-returns-null/
	 * 11199326#11199326
	 */
	public static String readLine(String format, Object... args) throws BpmsFrontendException {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		System.out.print(String.format(format, args));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			return reader.readLine();
		} catch (IOException e) {
			String errorMessage = String.format(
					"Encountered an IOException while trying to read from the console. " + "The error was: %s%n",
					e.getMessage());
			throw new BpmsFrontendException(errorMessage);
		}
	}

	/**
	 * Imitate password entry for a system console if no system console is present.
	 * <strong>WARNING:</strong> Typing passwords into a pseudo-console using this
	 * method may not hide the password!
	 * 
	 * @param format
	 *            Formatting string, in
	 * 
	 *            <pre>
	 *            String.format()
	 * 			</pre>
	 * 
	 *            style
	 * @param args
	 *            Additional arguments to be used by formatting string
	 * @return Password read from user input, in
	 * 
	 *         <pre>
	 *         String
	 * 		</pre>
	 * 
	 *         format
	 * 
	 */
	public static char[] readPassword(String format, Object... args) throws BpmsFrontendException {
		if (System.console() != null)
			return System.console().readPassword(format, args);
		return readLine(format, args).toCharArray();
	}

}
