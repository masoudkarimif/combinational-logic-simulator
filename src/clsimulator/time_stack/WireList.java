package clsimulator.time_stack;
import java.util.ArrayList;

import clsimulator.main.*;


public class WireList {
	private ArrayList<Wire> activity_list;
	
	public WireList() {
		activity_list = new ArrayList<Wire>();
	}
	
	public void insert(Wire wire) {
		if(!activity_list.contains(wire)){
			activity_list.add(wire);
		}		
	}
	
	public Wire getWireAtIdx(int idx) {
		return activity_list.get(idx);
	}
	
	public int getSize() {
		return activity_list.size();
	}
}
