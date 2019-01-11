package com.cret.can;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jssc.SerialPortList;


public class CanUtils {
	
	private static Map<String, Integer> identifiedIdsInterface1 = new HashMap<>();
	
	private static Map<String, Integer> identifiedIdsInterface2 = new HashMap<>();
	
	private static String configSpeed1;
	
	private static String configSpeed2;
	
	private static String configPort1;
	
	private static String configPort2;
	
	private static String configMode1;
	
	private static String configMode2;
	
	private static boolean is1Enabled = false;
	
	private static boolean is2Enabled = false;
	
	
	public static boolean isIdentifiedInterface1(String id) {
		if (identifiedIdsInterface1.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isIdentifiedInterface2(String id) {
		if (identifiedIdsInterface1.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setIdentifiedIdInterface1(String id, int length) {
		identifiedIdsInterface1.put(id, length);
	}
	
	public static void setIdentifiedIdInterface2(String id, int length) {
		identifiedIdsInterface2.put(id, length);
	}
	
	public static List<String> getListIdsInterface1(){
		List<String> ids = new ArrayList<String>();
		ids.addAll(identifiedIdsInterface1.keySet());
		return ids;
	}
	
	public static List<String> getListIdsInterface2(){
		List<String> ids = new ArrayList<String>();
		ids.addAll(identifiedIdsInterface2.keySet());
		return ids;
	}
	
	public static int getLengthIdInterface1(String id) {
		return identifiedIdsInterface1.get(id);
	}
	
	public static int getLengthIdInterface2(String id) {
		return identifiedIdsInterface2.get(id);
	}
	
	public static void setConfigSpeed1(String speed) {
		configSpeed1 = speed;
	}
	
	public static void setConfigSpeed2(String speed) {
		configSpeed2 = speed;
	}
	
	public static void setConfigPort1(String port) {
		configPort1 = port;
	}
	
	public static void setConfigPort2(String port) {
		configPort2 = port;
	}
	
	public static void set1(boolean state) {
		is1Enabled = state;
	}
	
	public static void set2(boolean state) {
		is2Enabled = state;
	}
	
	public static String getConfigSpeed1() {
		return configSpeed1;
	}
	
	public static String getConfigSpeed2() {
		return configSpeed2;
	}
	
	public static String getConfigPort1() {
		return configPort1;
	}
	
	public static String getConfigPort2() {
		return configPort2;
	}
	
	public static boolean get1State() {
		return is1Enabled;
	}
	
	public static boolean get2State() {
		return is2Enabled;
	}
	
	public static String getConfigMode1() {
		return configMode1;
	}
	
	public static String getConfigMode2() {
		return configMode2;
	}
	
	public static void setConfigMode1(String mode) {
		configMode1 = mode;
	}
	
	public static void setConfgiMode2(String mode) {
		configMode2 = mode;
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
