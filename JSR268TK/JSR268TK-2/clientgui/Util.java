package clientgui;

public class Util {
	public static Long doubleToLong(Double num){
		String s = num.toString();
		return Long.parseLong(s.substring(0, s.length() - 2));
	}
	
	public static Integer doubleToInt(Double num){
		String s = num.toString();
		return Integer.parseInt((s.substring(0, s.length() - 2)));
	}
}
