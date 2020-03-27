package clsimulator.util;

public class Util {
	public static StringBuilder removeOnce(StringBuilder stringBuilder) {
		String str = stringBuilder.toString().trim();
		String[] strArr = str.split("\\s+");
		String removeStr = "";
		for(String s: strArr) {
			if(str.indexOf(s) != -1 && removeStr.indexOf(s) == -1) {
				str = str.replaceFirst(s, "");
				removeStr += s;
			}
		}
		return new StringBuilder(str.trim().replaceAll("\\s+", " "));
	}
	
	public static StringBuilder removeDuplicate(StringBuilder stringBuilder) {
		String str = stringBuilder.toString().trim();
		String[] strArr = str.split("\\s+");
		String removeStr = "";
		for(String s: strArr) {
			if(str.indexOf(s) != -1) {
				if(removeStr.indexOf(s) != -1)
					str = str.replaceFirst(s, "");
				else
					removeStr += s;
			}
		}
		return new StringBuilder(str.trim().replaceAll("\\s+", " "));
	}
}
