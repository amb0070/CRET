package com.cret.interfaces;
import jssc.SerialPortList;


public class CanUtils {
	
	private static String configSpeed1;
	
	private static String configSpeed2;
	
	private static String configPort1;
	
	private static String configPort2;
	
	private static boolean is1Enabled = false;
	
	private static boolean is2Enabled = false;
	
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
