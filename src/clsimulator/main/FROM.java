package clsimulator.main;

public class FROM extends Wire{

	public FROM(int fanInCount, int idx) {
		name = "FROM";
		//type = 2;
		this.fanInCount = fanInCount;
		this.idx = idx;
	}
	
	@Override
	public void applyDeductiveFault() {
		if(!applyedDeductiveFault) {
		fault_list = fanIns.get(0).getFault_list() 
				+ " w" + idx + ((logicValue == 0) ? "/1" : "/0");
		applyedDeductiveFault = true;
		}
		
	}
	
	public int logic() {
		int value = fanIns.get(0).logicValue;
		return value;
	}
}
