package com.cret.can;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jssc.SerialPortList;


/**
 * @author Adrian Marcos
 *
 */
public class CanUtils {
	
	private static Map<String, List<Integer>> identifiedIdsInterface1 = new HashMap<>();
	
	private static Map<String, Integer> identifiedIdsInterface1Len = new HashMap<>();
	
	private static Map<String, List<Integer>> identifiedIdsInterface2 = new HashMap<>();
	
	private static Map<String, Integer> identifiedIdsInterface2Len = new HashMap<>();
	
	private static String speedInterface1;
	
	private static String speedInterface2;
	
	private static String portInterface1;
	
	private static String portInterface2;
	
	private static String modeInterface1;
	
	private static String modeInterface2;
	
	private static boolean isInterface1Enabled = false;
	
	private static boolean isInterface2Enabled = false;
	
	private static boolean ignoreZeroBytes = true;
	
	private static boolean splitBytes = false;
	
	
	public static void clearInterface1() {
		identifiedIdsInterface1 = new HashMap<>();
		identifiedIdsInterface1Len = new HashMap<>();
	}
	
	public static void clearInterface2() {
		identifiedIdsInterface2 = new HashMap<>();
		identifiedIdsInterface2Len = new HashMap<>();
	}
	
	public static void setIgnoreZeroBytes(boolean option) {
		ignoreZeroBytes = option;
	}
	
	public static boolean getIgnoreZeroBytes() {
		return ignoreZeroBytes;
	}
	
	public static void setSplitBytes(boolean option) {
		splitBytes = option;
	}
	
	public static boolean getSplitBytes() {
		return splitBytes;
	}
	
	
	public static boolean isIdentifiedInterface1(String id, int byteNumber) {
		if (identifiedIdsInterface1.containsKey(id)) {
			if (identifiedIdsInterface1.get(id).contains(byteNumber)){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static boolean isIdentifiedInterface1Len(String id) {
		if (identifiedIdsInterface1Len.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isIdentifiedInterface2(String id) {
		if (identifiedIdsInterface1Len.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setIdentifiedIdInterface1Len(String id, int length) {
		identifiedIdsInterface1Len.put(id, length);
		for (int i=0; i < length; i++) {
			identifiedIdsInterface1.put(id, new ArrayList<>());
		}
	}
	
	public static void setIdentifiedIdInterface1(String id, int byteNumber) {

		identifiedIdsInterface1.get(id).add(byteNumber);
	}
	
	public static void setIdentifiedIdInterface2Len(String id, int length) {
		identifiedIdsInterface2Len.put(id, length);
	}
	
	public static List<String> getListIdsInterface1Len(){
		List<String> ids = new ArrayList<String>();
		ids.addAll(identifiedIdsInterface1Len.keySet());
		return ids;
	}
	
	public static List<String> getListIdsInterface2Len(){
		List<String> ids = new ArrayList<String>();
		ids.addAll(identifiedIdsInterface2Len.keySet());
		return ids;
	}
	
	public static int getLengthIdInterface1Len(String id) {
		return identifiedIdsInterface1Len.get(id);
	}
	
	public static int getLengthIdInterface2Len(String id) {
		return identifiedIdsInterface2Len.get(id);
	}
	
	public static void setConfigSpeed1(String speed) {
		speedInterface1 = speed;
	}
	
	public static void setConfigSpeed2(String speed) {
		speedInterface2 = speed;
	}
	
	public static void setConfigPort1(String port) {
		portInterface1 = port;
	}
	
	public static void setConfigPort2(String port) {
		portInterface2 = port;
	}
	
	public static void set1(boolean state) {
		isInterface1Enabled = state;
	}
	
	public static void set2(boolean state) {
		isInterface2Enabled = state;
	}
	
	public static String getConfigSpeed1() {
		return speedInterface1;
	}
	
	public static String getConfigSpeed2() {
		return speedInterface2;
	}
	
	public static String getConfigPort1() {
		return portInterface1;
	}
	
	public static String getConfigPort2() {
		return portInterface2;
	}
	
	public static boolean get1State() {
		return isInterface1Enabled;
	}
	
	public static boolean get2State() {
		return isInterface2Enabled;
	}
	
	public static String getConfigMode1() {
		return modeInterface1;
	}
	
	public static String getConfigMode2() {
		return modeInterface2;
	}
	
	public static void setConfigMode1(String mode) {
		modeInterface1 = mode;
	}
	
	public static void setConfigMode2(String mode) {
		modeInterface2 = mode;
	}
	
	public static int hexToDec(String hex) {
		return Integer.parseInt(hex, 16);
	}
	
	public static String decToHex(int dec) {
		return Integer.toHexString(dec);
	}
	
	public static int hexToDec(Byte hex) {
		int value = 0;
		value = (value << 8) | hex;
		return value;
	}
	
	//Returns the COM ports
	public static String[] getSerialPorts() {
		
		String[] ports = SerialPortList.getPortNames();
		
		if (ports.length == 0) {
			String[] empty = {"No ports found"};
			return empty;
		} else {
			return ports;
		}
		
	}
}
