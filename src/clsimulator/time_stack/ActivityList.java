package clsimulator.time_stack;

public class ActivityList {
	private WireList[] time_stack;
	
	public ActivityList() {
		time_stack = new WireList[500];
	}
	
	public void insertIntoTimeStack(int idx, WireList wireList) {
		time_stack[idx] = wireList;
	}
	
	public WireList retrieveFromTimeStack(int idx) {
		return time_stack[idx];
	}
}
