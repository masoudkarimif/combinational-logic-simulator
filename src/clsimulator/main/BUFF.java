package clsimulator.main;

public class BUFF extends Wire {
	public BUFF(int fanInCount, int idx) {
		name = "BUFF";
		this.fanInCount = fanInCount;
		this.idx = idx;
	}
	
	@Override
	public void applyDeductiveFault() {
		if(!applyedDeductiveFault) {
			String currentFault = "";
			StringBuilder addList = new StringBuilder("");
			
			if(possible_faults[0] && logicValue == 1)
				currentFault = "w" + idx + "/0";
			else if(possible_faults[1] && logicValue == 0)
				currentFault = "w" + idx + "/1";
			
			addList.append(fanIns.get(0).getFault_list());
			
			fault_list = cleanFaultList(addList.toString().trim(), "") 
					+ " " 
					+ currentFault;

			applyedDeductiveFault = true;
		}
		
	}
	
	public int logic() {
		return fanIns.get(0).logicValue;
		 
	}
}
