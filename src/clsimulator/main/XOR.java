package clsimulator.main;
import clsimulator.util.Util;


public class XOR extends Wire {
	public XOR(int fanInCount, int idx) {
		name = "XOR";
		this.fanInCount = fanInCount;
		this.idx = idx;
	}
	
	@Override
	public void applyDeductiveFault() {
		if(!applyedDeductiveFault) {
			String currentFault = "";
			StringBuilder addList = new StringBuilder("");
			StringBuilder removeList = new StringBuilder("");
			
				if(possible_faults[0] && logicValue == 1)
					currentFault = "w" + idx + "/0";
				else if(possible_faults[1] && logicValue == 0)
					currentFault = "w" + idx + "/1";
				
				addList.append(fanIns.get(0).fault_list);
				addList.append(fanIns.get(1).fault_list);
				addList = Util.removeDuplicate(addList);
			
			fault_list = cleanFaultList(addList.toString().trim(), removeList.toString().trim()) 
					+ " " 
					+ 
					currentFault;

			applyedDeductiveFault = true;
		}
		
	}
	
	public int logic() {
		boolean zf=false, uf=false, nf=false;
		int one_counter=0;
		for(int i=0; i < fanInCount; i++) {
			if(fanIns.get(i).logicValue == 1) {
				one_counter++;
			}
			
			else if(fanIns.get(i).logicValue == 3) {
				uf = true;
				break;
			}
			
			else if(fanIns.get(i).logicValue == 0) {
				zf = true;
			}
			
			else if(fanIns.get(i).logicValue == -1) {
				nf=true;
				break;
			}
		}
		
		if(nf) return -1;
		else if(uf) return 3;
		else if(one_counter > 0) {
			return (one_counter % 2 == 1) ? 1:0;
		}
		else return (zf) ? 0 : 2;
	}
}
