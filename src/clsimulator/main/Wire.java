package clsimulator.main;
import java.util.ArrayList;

public abstract class Wire {
	
	protected String name;
	protected int type; // type of gate driving this wire
	protected int fanInCount; // number of fanin of this wire's source gate
	protected ArrayList<Wire> fanIns = new ArrayList<Wire>(); // indices of wires that connect to this wire's source
	protected ArrayList<Wire> fanOuts = new ArrayList<Wire>();
	protected int level; // level of this wire
	protected int faultCount; // number of fault associated with this wire
	protected int logicValue = -1; // logic value of this wire's source gate
	protected int idx;
	protected String fault_list="";
	protected boolean[] possible_faults = {false, false};
	protected boolean applyedDeductiveFault = false;
	protected short[] value_history = new short[50];
	
	protected short nxt_value;
	protected short rise_time = 0;
	protected short fall_time = 0;
	
	public void addHistoryPoint(int idx, short value) {
		
		value_history[idx] = value;
	}
	
	public short getHistoryValue(int idx) {
		return value_history[idx];
	}
	
	public int getHistoryValueCount() {
		return value_history.length;
	}
	
	public short getNxt_value() {
		return nxt_value;
	}

	public void setNxt_value(short nxt_value) {
		this.nxt_value = nxt_value;
	}

	public short getRise_time() {
		return rise_time;
	}

	public void setRise_time(short rise_time) {
		this.rise_time = rise_time;
	}

	public short getFall_time() {
		return fall_time;
	}

	public void setFall_time(short fall_time) {
		this.fall_time = fall_time;
	}

	public abstract int logic();
	
	public abstract void applyDeductiveFault();
	
	
	public void addPossibleFaultAtZero() {
		possible_faults[0] = true;
	}
	public void addPossibleFaultAtOne() {
		possible_faults[1] = true;
	}
	
	public String getFault_list() {
		return fault_list;
	}

	public void addToFaultList(String s) {
		fault_list += " w" + idx + s;
	}
	
	public String cleanFaultList(String addSet, String removeSet) {
		String str = addSet;
		if(removeSet.length() > 0){
			String[] removeArr = removeSet.trim().split("\\s+");
			for( String s: removeArr) {
				str = str.replaceAll(s + "+", "");
			}
		}
		
		if(str.length() > 0) {
			String[] strArr = str.trim().split("\\s+");
		
			for( String s: strArr) {
				str = str.replaceAll(s + "+", "");
				str += s + " ";
			}
		}
		
		return str.trim().replaceAll("\\s+", " ");
	}
	
	
	public int getIdx() {
		return idx;
	}
	
	public void addFanOut(Wire w) {
		fanOuts.add(w);
	}
	
	public void addFanIn(Wire newWire) {
		fanIns.add(newWire);
		newWire.addFanOut(this);
	}
	
	public ArrayList<Wire> getFanOuts() {
		return fanOuts;
	}

	public void setLevel() {
		int maxLevel = -1;
		for(int i=0; i < fanInCount; i++) {
			if(fanIns.get(i).level > maxLevel) {
				maxLevel = fanIns.get(i).level;
			}
		}
		level = ++maxLevel;
	}
	
	public int getValue() {
		return logicValue;
	}
	
	public void setValue(int value) {
		logicValue = value;
	}
	
}
