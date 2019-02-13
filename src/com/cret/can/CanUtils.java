package com.cret.can;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jssc.SerialPortList;

/**
 * 
 * Class with static methods. Utils for CAN interaction.
 * 
 * Used to know which interface is configurred, and the configuration characteristics of the interface.
 * 
 * Methods to set and get the configuration of the interfaces and filters.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class CanUtils {
	
	/**
	 * Pair with ID - List<Byte number> for interface 1.
	 */
	private static Map<String, List<Integer>> identifiedIdsInterface1 = new HashMap<>();
	
	/**
	 * Pair with ID - Length of the frame for interface1.
	 */
	private static Map<String, Integer> identifiedIdsInterface1Len = new HashMap<>();
	
	/**
	 * Pair with ID - List<Byte number> for interface 2.
	 */
	private static Map<String, List<Integer>> identifiedIdsInterface2 = new HashMap<>();
	
	/**
	 * Pair with ID - Length of the frame for interface 2.
	 */
	private static Map<String, Integer> identifiedIdsInterface2Len = new HashMap<>();
	
	/**
	 * Selected speed for interface 1.
	 */
	private static String speedInterface1;
	
	/**
	 * Selected speed for interface 2.
	 */
	private static String speedInterface2;
	
	/**
	 * Selected port for interface 1.
	 */
	private static String portInterface1;
	
	/**
	 * Selected port for interface 2.
	 */
	private static String portInterface2;
	
	/**
	 * Selected mode for interface 1.
	 */
	private static String modeInterface1;
	
	/**
	 * Selected mode for interface 2.
	 */
	private static String modeInterface2;
	
	/**
	 * Stores if interface 1 is enabled and configured.
	 */
	private static boolean isInterface1Enabled = false;
	
	/**
	 * Stores if interface 2 is enabled and configured.
	 */
	private static boolean isInterface2Enabled = false;

	/**
	 * Ignore if bytes to plot on interface 1 are 00.
	 */
	private static boolean ignoreZeroBytesInterface1 = true;
	
	/**
	 * Ignore if bytes to plot on interface 2 are 00.
	 */
	private static boolean ignoreZeroBytesInterface2 = true;
	
	/**
	 * Split bytes into nibbles on interface 1.
	 * For example, A0 -> A - 0
	 */
	private static boolean splitBytesInterface1 = false;
	
	/**
	 * Split bytes into nibbles on interface 2.
	 * For example, A0 -> A - 0
	 */
	private static boolean splitBytesInterface2 = false;
	
	
	/**
	 * Clears the identified IDs and bytes of the interface 1.
	 */
	public static void clearIdentifiedInterface1() {
		identifiedIdsInterface1 = new HashMap<>();
		identifiedIdsInterface1Len = new HashMap<>();
	}
	
	/**
	 * Clears the identified IDs and bytes of the interface 2.
	 */
	public static void clearIdentifiedInterface2() {
		identifiedIdsInterface2 = new HashMap<>();
		identifiedIdsInterface2Len = new HashMap<>();
	}
	
	/**
	 * Sets the boolean option to ignore bytes with value 00 for interface 1.
	 * 
	 * @param option Boolean option to ignore bytes with value 00.
	 */
	public static void setIgnoreZeroBytesInterface1(boolean option) {
		ignoreZeroBytesInterface1 = option;
	}
	
	/**
	 * Sets the boolean option to ignore bytes with value 00 for interface 2.
	 * @param option True to ignore bytes with value 00.
	 */
	public static void setIgnoreZeroBytesInterface2(boolean option) {
		ignoreZeroBytesInterface2 = option;
	}
	
	/**
	 * Returns if interface 1 is ignoring bytes with value 00.
	 * 
	 * @return True if interface 1 ignores 00 values.
	 */
	public static boolean getIgnoreZeroBytesInterface1() {
		return ignoreZeroBytesInterface1;
	}
	
	/**
	 * Returns if interface 2 is ignoring bytes with value 00.
	 * 
	 * @return True if interface 2 ignores 00 values.
	 */
	public static boolean getIgnoreZeroBytesInterface2() {
		return ignoreZeroBytesInterface2;
	}
	
	/**
	 * Sets if interface 1 has to ignore bytes with value 00.
	 * 
	 * @param option True if splits bytes.
	 */
	public static void setSplitBytesInterface1(boolean option) {
		splitBytesInterface1 = option;
	}
	
	/**
	 * Sets if interface 2 has to ignore bytes with value 00.
	 * 
	 * @param option True if splits bytes.
	 */
	public static void setSplitBytesInterface2(boolean option) {
		splitBytesInterface2 = option;
	}
	
	/**
	 * Returns if interface 1 splits bytes in two values.
	 * 
	 * @return True if splits bytes.
	 */
	public static boolean getSplitBytesInterface1() {
		return splitBytesInterface1;
	}
	
	/**
	 * Returns if interface 2 splits bytes in two values.
	 * 
	 * @return True if splits bytes.
	 */
	public static boolean getSplitBytesInterface2() {
		return splitBytesInterface2;
	}
	
	
	/**
	 * Returns true if the pair ID - byte number is identified by the interface 1.
	 * 
	 * @param id Identifier of the identified frame.
	 * @param byteNumber Byte position in the frame.
	 * @return True if the pair is identified.
	 */
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
	
	
	/**
	 * Returns true if ID is identified by the interface 1.
	 * 
	 * @param id ID of the frame.
	 * @return True if it's identified.
	 */
	public static boolean isIdentifiedInterface1Len(String id) {
		if (identifiedIdsInterface1Len.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}
	

	/**
	 * Returns if the pair ID - byte number is identified by interface 2.
	 * 
	 * @param id ID of the frame.
	 * @param byteNumber Byte position in the frame.
	 * @return True if it's identified.
	 */
	public static boolean isIdentifiedInterface2(String id, int byteNumber) {
		if (identifiedIdsInterface2.containsKey(id)) {
			if (identifiedIdsInterface2.get(id).contains(byteNumber)){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Returns if the ID is identified by interface 2.
	 * 
	 * @param id ID of the frame.
	 * @return True if it's identified.
	 */
	public static boolean isIdentifiedInterface2Len(String id) {
		if (identifiedIdsInterface2Len.containsKey(id)) {
			return true;
		} else {
			return false;
		}
 	}
	
	/**
	 * Sets the pair ID - byte number in the interface 1.
	 * 
	 * @param id ID of the frame.
	 * @param byteNumber Byte position in the frame.
	 */
	public static void setIdentifiedIdInterface1(String id, int byteNumber) {
		identifiedIdsInterface1.get(id).add(byteNumber);
	}
	
	/**
	 * Sets the pair ID - byte number in interface 2.
	 * 
	 * @param id ID of the frame.
	 * @param byteNumber Byte position in the frame.
	 */
	public static void setIdentifiedIdInterface2(String id, int byteNumber) {
		identifiedIdsInterface2.get(id).add(byteNumber);
	}
	
	/**
	 * Sets the pair ID - length of a frame in the interface 1.
	 * 
	 * @param id ID of the frame.
	 * @param length Lenght of the data field.
	 */
	public static void setIdentifiedIdInterface1Len(String id, int length) {
		identifiedIdsInterface1Len.put(id, length);
		for (int i=0; i < length; i++) {
			identifiedIdsInterface1.put(id, new ArrayList<>());
		}
	}
	
	/**
	 * Sets the pair ID - length of a frame in the interface 2.
	 * 
	 * @param id ID of the frame.
	 * @param length Length of the data field.
	 */
	public static void setIdentifiedIdInterface2Len(String id, int length) {
		identifiedIdsInterface2Len.put(id, length);
	}
	
	/**
	 * Returns a list of the IDs identified by interface 1.
	 * 
	 * @return List with the identified IDs.
	 */
	public static List<String> getIdsIdentifiedInterface1Len(){
		List<String> ids = new ArrayList<String>();
		ids.addAll(identifiedIdsInterface1Len.keySet());
		return ids;
	}
	
	/**
	 * Returns a list of the IDs identified by interface 2.
	 * 
	 * @return List with the identified IDs.
	 */
	public static List<String> getIdsIdentifiedInterface2Len(){
		List<String> ids = new ArrayList<String>();
		ids.addAll(identifiedIdsInterface2Len.keySet());
		return ids;
	}
	
	/**
	 * Returns the length of the frame of a specified ID identified by interface 2.
	 * 
	 * @param id ID of the frame.
	 * @return Length of the identified ID.
	 */
	public static int getLengthOfIdInterface1Len(String id) {
		return identifiedIdsInterface1Len.get(id);
	}
	
	/**
	 * Returns the length of the frame of a specified ID identified by interface 2.
	 * 
	 * @param id ID of the frame.
	 * @return Length of the identified ID.
	 */
	public static int getLengthOfIdInterface2Len(String id) {
		return identifiedIdsInterface2Len.get(id);
	}
	
	/**
	 * Sets the speed to be used by interface 1.
	 * 
	 * @param speed Speed to connect.
	 */
	public static void setConfigurationSpeedInterface1(String speed) {
		speedInterface1 = speed;
	}
	
	/**
	 * Sets the speed to be used by interface 2.
	 * 
	 * @param speed Speed to connect.
	 */
	public static void setConfigurationSpeedInterface2(String speed) {
		speedInterface2 = speed;
	}
	
	/**
	 * Sets the port to be used by interface 1.
	 * 
	 * @param port Port to connect.
	 */
	public static void setConfigurationPortInterface1(String port) {
		portInterface1 = port;
	}
	
	/**
	 * Sets the port to be used by interface 2.
	 * 
	 * @param port Port to connect.
	 */
	public static void setConfigurationPortInterface2(String port) {
		portInterface2 = port;
	}
	
	/**
	 * Sets if interface 1 is configured and enabled.
	 * 
	 * @param state State of the interface.
	 */
	public static void setInterface1State(boolean state) {
		isInterface1Enabled = state;
	}
	
	/**
	 * Sets if interface 2 is configured and enabled.
	 * 
	 * @param state State of the interface.
	 */
	public static void setInterface2State(boolean state) {
		isInterface2Enabled = state;
	}
	
	/**
	 * Returns the speed configured to interface 1.
	 * 
	 * @return Speed of the interface 1.
	 */
	public static String getConfigurationSpeedInterface1() {
		return speedInterface1;
	}
	
	/**
	 * Returns the speed configured for interface 2.
	 * 
	 * @return Speed of the interface 2.
	 */
	public static String getConfigurationSpeedInterface2() {
		return speedInterface2;
	}
	
	/**
	 * Returns the port configured for interface 1.
	 * 
	 * @return Port of the interface 1.
	 */
	public static String getConfigurationPortInterface1() {
		return portInterface1;
	}
	
	/**
	 * Returns the port configured for interface 2.
	 * 
	 * @return Port of the interface 2.
	 */
	public static String getConfigurationPortInterface2() {
		return portInterface2;
	}
	
	/**
	 * Returns if interface 1 is enabled and configured.
	 * 
	 * @return True if interface is enabled.
	 */
	public static boolean getInterface1State() {
		return isInterface1Enabled;
	}
	
	/**
	 * Returns if interface 2 is enabled and configured.
	 * 
	 * @return True if interface is enabled.
	 */
	public static boolean getInterface2State() {
		return isInterface2Enabled;
	}
	
	/**
	 * Returns the mode configured to interface 1.
	 * 
	 * @return Mode configured on interface 1.
	 */
	public static String getConfigurationModeInterface1() {
		return modeInterface1;
	}
	
	/**
	 * Returns the mode configured to interface 2.
	 * 
	 * @return Mode configured on interface 2.
	 */
	public static String getConfigurationModeInterface2() {
		return modeInterface2;
	}
	
	/**
	 * Sets the mode that interface 1 will use to connect to the port.
	 * 
	 * @param mode Mode to configure interface 1.
	 */
	public static void setConfigurationModeInterface1(String mode) {
		modeInterface1 = mode;
	}
	
	/**
	 * Sets the mode that interface 2 will use to connect to the port.
	 * 
	 * @param mode Mode to configure interface 2.
	 */
	public static void setConfigurationModeInterface2(String mode) {
		modeInterface2 = mode;
	}
	
	
	/**
	 * Returns decimal value of hexadecimal string input.
	 * 
	 * @param hex Hex value.
	 * @return Decimal value.
	 */
	public static int hexToDec(String hex) {
		return Integer.parseInt(hex, 16);
	}
	
	/**
	 * Returns hexadecimal String of decimal input.
	 * 
	 * @param dec Decimal value.
	 * @return Hexadecimal value.
	 */
	public static String decToHex(int dec) {
		return Integer.toHexString(dec);
	}
	
	/**
	 * Returns decimal value of hexadecimal byte input.
	 * 
	 * @param hex Hexadecimal value.
	 * @return Decimal value.
	 */
	public static int hexToDec(Byte hex) {
		return ((int) hex & 0xFF);
	}
	
	/**
	 * Turns hexadecimal value to ascii value.
	 * 
	 * @param hex Hexadecimal value.
	 * @return String value.
	 */
	public static String convertHexToAscii(String hex){

		  StringBuilder sb = new StringBuilder();

		  for( int i=0; i<hex.length()-1; i+=2 ){
			  
		      String output = hex.substring(i, (i + 2));
		      
		      int decimal = Integer.parseInt(output, 16);

		      sb.append((char)decimal);
		  }
		  
		  return sb.toString();
	  }
	
	
	/**
	 * Returns the list of COM ports available.
	 * 
	 * @return Array with COM ports.
	 */
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
