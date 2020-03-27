package clsimulator.main;
import java.io.*;

public class Simulator {

	private Circuit myCircuit;
	private String fileName;
	
	public Simulator(String fileName) {
		myCircuit = new Circuit("Test");
		this.fileName = fileName;
	}
	
	public void logWires() {
		try ( BufferedReader fr = new BufferedReader(
				new FileReader(fileName)) )
		{
			String c;
			while( (c=fr.readLine() ) != null ) {
				String[] line_arr = c.trim().split("\\s+");
				if(c.charAt(0) == '*') continue; // skip comments
				if(line_arr.length == 0) continue; //blank line
				
				if(line_arr.length >= 5) {
					//inpt || from || gate
					String type = line_arr[2];
					int idx = Integer.parseInt(line_arr[0]);
					

					if(type.equals("inpt")) {
						int fanOut = Integer.parseInt(line_arr[3]);
						INPUT newInput = new INPUT(0 , idx);
						myCircuit.addWireAtIdx(
								newInput
								, idx);
						myCircuit.addInput(idx);
						
						if(c.contains(">sa0")) newInput.addPossibleFaultAtZero();
						if(c.contains(">sa1")) newInput.addPossibleFaultAtOne();
						
						newInput.setLevel();
						
						if (fanOut > 1) {
							for(int i=0; i< fanOut; i++) {
								c=fr.readLine();
								line_arr = c.trim().split("\\s+");
								idx = Integer.parseInt(line_arr[0]);
								FROM newFrom = new FROM(1, idx);
								myCircuit.addWireAtIdx(
										newFrom
										, idx);
								newFrom.addFanIn(newInput);
								newFrom.setLevel();
							}
						}
					}//end of if input
					
					else {
						//it's a gate wire!
						Wire newGateWire = determineGateType(line_arr);
						myCircuit.addWireAtIdx(newGateWire, newGateWire.idx);
						if(c.contains(">sa0")) newGateWire.addPossibleFaultAtZero();
						if(c.contains(">sa1")) newGateWire.addPossibleFaultAtOne();
						
						int fanOut = Integer.parseInt(line_arr[3]);
						//nxt lines are inputs
						c=fr.readLine();
						line_arr = c.trim().split("\\s+");
						for(int i=0; i < newGateWire.fanInCount; i++) {
							newGateWire.addFanIn(myCircuit.getWireAtIdx(Integer.parseInt(line_arr[i])));
						}
						if(line_arr.length == 4) {
							//if has rise_time & fall_time
							newGateWire.setRise_time((short) Integer.parseInt(line_arr[2]));
							newGateWire.setFall_time((short) Integer.parseInt(line_arr[3]));
						}
						else if(line_arr.length == 3) {
							//if has rise_time & fall_time for not and buff
							newGateWire.setRise_time((short) Integer.parseInt(line_arr[1]));
							newGateWire.setFall_time((short) Integer.parseInt(line_arr[2]));
						}
						newGateWire.setLevel();
						
						//set fanouts
						if (fanOut > 1) {
							
							for(int i=0; i< fanOut; i++) {
								c=fr.readLine();
								line_arr = c.trim().split("\\s+");
								idx = Integer.parseInt(line_arr[0]);
								FROM newFrom = new FROM(1, idx);
								myCircuit.addWireAtIdx(
										newFrom
										, idx);
								if(c.contains(">sa0")) newFrom.addPossibleFaultAtZero();
								if(c.contains(">sa1")) newFrom.addPossibleFaultAtOne();
								newFrom.addFanIn(newGateWire);
								newFrom.setLevel();
							}
						}
					}
					
					
				}
			}
			
		} catch(IOException e) {
			System.out.println("I/O Error: " + e);
		}
		
		
		myCircuit.createEfficientArray();
		ApplyTestVectors.apply_modify(myCircuit);
		
		
	}
	
	public Wire determineGateType( String[] line_arr) {
		String type = line_arr[2];
		int idx = Integer.parseInt(line_arr[0]);
		int fanIn = Integer.parseInt(line_arr[4]);
		
		if(type.equals("nand")) return new NAND(fanIn, idx);
		else if(type.equals("and")) return new AND(fanIn, idx);
		else if(type.equals("or")) return new OR(fanIn, idx);
		else if(type.equals("nor")) return new NOR(fanIn, idx);
		else if(type.equals("xor")) return new XOR(fanIn, idx);
		else if(type.equals("xnor")) return new XNOR(fanIn, idx);
		else if(type.equals("not")) return new NOT(fanIn, idx);
		else if(type.equals("buff")) return new BUFF(fanIn, idx);
		else return new INVALID(fanIn, idx);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Simulator mySimulator = new Simulator("resources/circuit.isc");
		mySimulator.logWires();
		

	}

}
