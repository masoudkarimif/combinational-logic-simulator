package clsimulator.main;

public class INPUT extends Wire{

	public INPUT(int fanInCount, int idx) {
		name = "INPUT";
		this.fanInCount = fanInCount;
		level = 0;
		this.idx = idx;
	}
	
	@Override
	public void applyDeductiveFault() {
		if(!applyedDeductiveFault) {
			if(logicValue == 0 && possible_faults[1]) {
				addToFaultList("/1");
			}
			else if (logicValue == 1 && possible_faults[0]) {
				addToFaultList("/0");
			}
			applyedDeductiveFault = true;
		}
		
		
		
	}
	
	public int logic() {
		return logicValue;
	}
}
