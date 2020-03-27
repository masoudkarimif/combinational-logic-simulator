package clsimulator.main;

public class INVALID extends Wire {
	public INVALID(int fanInCount, int idx) {
		name = "INVALID";
		//type = 2;
		this.fanInCount = fanInCount;
		this.idx = idx;
	}
	
	@Override
	public void applyDeductiveFault() {}
	
	public int logic() {
		return 2; //z
	}
}
