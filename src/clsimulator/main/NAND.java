package clsimulator.main;
import clsimulator.util.Util;


public class NAND extends Wire {
	
	public NAND(int fanInCount, int idx) {
		name = "NAND";
		this.fanInCount = fanInCount;
		this.idx = idx;
	}
	
	@Override
	public void applyDeductiveFault() {
		if(!applyedDeductiveFault) {
			int value = logicValue;
			short commonCounter = 0;
			String currentFault = "";
			StringBuilder addList = new StringBuilder("");
			StringBuilder removeList = new StringBuilder("");
			if(value == 1) {
				//one or more inputs are 0
				//all inputs must be 1
				if(possible_faults[0])
					currentFault = "w" + idx + "/0";
				for(int i=0; i < fanIns.size(); i++) {
					Wire fanIn = fanIns.get(i);
					if(fanIn.getValue() == 1) {
						//no change!
						removeList.append(" " + fanIn.fault_list);
					}
					else {
						//must be changed to 1
						//add fault list
						commonCounter++;
						addList.append(" " + fanIn.fault_list);
					}
				}

				if(commonCounter > 1)
					addList = Util.removeOnce(addList);
			}
			
			else {
				//value is 0
				//all inputs are 1
				//one or more must changed to 0
				if(possible_faults[1])
					currentFault = "w" + idx + "/1";
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
		boolean zf=false, of=false, uf=false, nf=false;
		for(int i=0; i < fanInCount; i++) {
			if(fanIns.get(i).logicValue == 0) {
				zf = true;
				break;
			}
			
			else if(fanIns.get(i).logicValue == 3) {
				uf = true;
			}
			
			else if(fanIns.get(i).logicValue == 1) {
				of = true;
			}
			
			else if(fanIns.get(i).logicValue == -1) {
				nf=true;
				break;
			}
		}
		
		if(nf) return -1;
		else if(zf) return 1;
		else if(uf) return 3;
		else if(of) return 0;
		else return 2;
	}
}
