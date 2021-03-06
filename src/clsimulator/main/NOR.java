package clsimulator.main;
import clsimulator.util.Util;


public class NOR extends Wire {
	public NOR(int fanInCount, int idx) {
		name = "NOR";
		//type = 1;
		this.fanInCount = fanInCount;
		this.idx = idx;
	}
	
	@Override
	public void applyDeductiveFault() {
		if(!applyedDeductiveFault) {
			int value = logicValue;
			short commonCounter = 0;
			String currentFault="";
			StringBuilder addList = new StringBuilder("");
			StringBuilder removeList = new StringBuilder("");
			if(value == 0) {
				//one or more inputs are 1
				//all inputs must be 0
				if(possible_faults[1])
					currentFault = "w" + idx + "/1";
				for(int i=0; i < fanIns.size(); i++) {
					Wire fanIn = fanIns.get(i);
					if(fanIn.getValue() == 0) {
						//no change!
						removeList.append(" " + fanIn.fault_list);
					}
					else {
						//must be changed to 1
						//add fault list
						addList.append(" " + fanIn.fault_list);
					}
				}
				if(commonCounter > 1)
					addList = Util.removeOnce(addList);
			}
			
			else {
				//value is 1
				//all inputs are 0
				//one or more must changed to 1
				if(possible_faults[0])
					currentFault = "w" + idx + "/0";
				for(int i=0; i < fanIns.size(); i++) {
					Wire fanIn = fanIns.get(i);
					addList.append(" " + fanIn.fault_list);
				}
			}
			fault_list = cleanFaultList(addList.toString().trim(), removeList.toString().trim()) 
					+ " " 
					+ 
					currentFault;
			//return fault_list;
			applyedDeductiveFault = true;
		}
		
	}
	
	public int logic() {
		boolean zf=false, of=false, uf=false, nf = false;
		for(int i=0; i < fanInCount; i++) {
			if(fanIns.get(i).logicValue == 1) {
				of = true;
				break;
			}
			
			else if(fanIns.get(i).logicValue == 3) {
				uf = true;
			}
			
			else if(fanIns.get(i).logicValue == 0) {
				zf = true;
			}
			
			else if(fanIns.get(i).logicValue == -1) {
				nf = true;
				break;
			}
		}
		
		if(nf) return -1;
		else if(of) return 0;
		else if(uf) return 3;
		else if(zf) return 1;
		else return 2;
	}
}
