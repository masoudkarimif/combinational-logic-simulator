package clsimulator.main;
import java.util.ArrayList;
public class Circuit {
	private String name;
	private int wireCount;
	private Wire[] wires;
	private Wire[] onlyWires;
	private int inputCount;
	private ArrayList<Integer> inputs;
	private int outputCount;
	private ArrayList<Integer> outputs;
	private Wire latestWire;
	
	public Wire getLatestWire() {
		return latestWire;
	}

	public Wire[] getOnlyWires() {
		return onlyWires;
	}
	
	public void createEfficientArray(){
		onlyWires = new Wire[wireCount];
		for(int i=0; i < wireCount; i++) {
			for(int j=0; j < wires.length; j++) {
				Wire thisWire = wires[j];
				if(thisWire!=null) {
					onlyWires[i] = thisWire;
					wires[j] = null;
					break;
				}
			}
		}
		wires = null;
	}
	
	public int getOnlyWireArrayLength() {
		return onlyWires.length;
	}
	
	public void resetForRepeatPass() {
		for(int i=0; i < getOnlyWireArrayLength(); i++) {
			Wire wire = getOnlyWireAtIdx(i);
			wire.logicValue = -1;
			wire.fault_list = "";
			wire.applyedDeductiveFault = false;
		}
	}
	
	public Wire getOnlyWireAtIdx(int idx) {
		return onlyWires[idx];
	}
	
	public Circuit(String name) {
		this.name = name;
		wires = new Wire[1000];
		inputs = new ArrayList<Integer>();
		outputs = new ArrayList<Integer>();
	}
	
	public int getWireCount() {
		return wireCount;
	}
	
	public int getWireArrayLength() {
		return wires.length;
	}
	
	public void addInput(int idx) {
		inputs.add(idx);
		inputCount++;
	}
	
	public void addOutput(int idx) {
		outputs.add(idx);
		outputCount++;
	}
	
	public int getInputCount() {
		return inputCount;
	}
	
	public void setInputCount(int x) {
		inputCount = x;
	}
	
	public int getOutputCount() {
		return outputCount;
	}
	
	public void setOutputCount(int x) {
		outputCount = x;
	}
	
	public void addWireAtIdx(Wire newGate, int idx) {
		wires[idx] = newGate;
		latestWire = newGate;
		wireCount++;
	}
	
	public Wire getWireAtIdx(int idx) {
		return wires[idx];
	}
}
