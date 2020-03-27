package clsimulator.main;

public class NOT extends Wire {
	public NOT(int fanInCount, int idx) {
		name = "NOT";
		//type = 1;
		this.fanInCount = fanInCount;
		this.idx = idx;
	}
	
	@Override
	public void applyDeductiveFault() {
		if(!applyedDeductiveFault) {
			int value = logicValue;
			String currentFault = "";
			StringBuilder addList = new StringBuilder("");
			StringBuilder removeList = new StringBuilder("");
			if(value == 1) {
				//one or more inputs are 0
				//all inputs must be 1
				if(possible_faults[0])
					currentFault = "w" + idx + "/0";
				
			}
			
			else {
				//value is 0
				if(possible_faults[1])
					currentFault = "w" + idx + "/1";
				
			}
			Wire fanIn = fanIns.get(0);
			addList.append(" " + fanIn.fault_list);
			
			fault_list = cleanFaultList(addList.toString().trim(), removeList.toString().trim()) 
					+ " " 
					+ 
					currentFault;
			//return fault_list;
			applyedDeductiveFault = true;
		}	
	}
	
	public int logic() {
		int value = fanIns.get(0).logicValue;
		return (value == 0) ? 1:
				(value == 1) ? 0:
				(value == 3) ? 3:
				(value == 4) ? 4 : -1;
	}
}
