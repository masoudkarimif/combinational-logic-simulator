package clsimulator.main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import clsimulator.time_stack.ActivityList;
import clsimulator.time_stack.WireList;
import clsimulator.view.ResultWindow;


public class ApplyTestVectors {
	
	private static ActivityList activityList;
	private static int timeCut = 50;

	public static void apply_modify(Circuit myCircuit) {
	
		
		
		try ( BufferedReader fr = new BufferedReader(
				new FileReader("resources/test-inputs.txt")) )
		{
			String c;
			
			while( (c=fr.readLine() ) != null ) {
				activityList = new ActivityList();
				String[] input_vector = c.trim().split("\\-");
				int counter=0;
				for(int i=0; i < myCircuit.getOnlyWireArrayLength(); i++) {
					//no null exists
					Wire thisWire = myCircuit.getOnlyWireAtIdx(i);
					if(thisWire.level == 0 && thisWire.logicValue == -1) {
						thisWire.logicValue = Integer.parseInt(input_vector[counter]);
						
						for(int k=0; k < thisWire.getFanOuts().size(); k++) {
							Wire fanout = thisWire.getFanOuts().get(k);
							int logic = fanout.logic();
							short time;
							if(logic != -1) {
								switch (logic) {
								case 0:
									time = fanout.getFall_time();
									break;

								default:
									time = fanout.getRise_time();
									break;
								}
								
								fanout.setNxt_value((short) logic);
								
								WireList wireList = activityList.retrieveFromTimeStack(time);
								if(wireList == null) {
									//this time stack is empty yet!
									wireList = new WireList();
								}
								wireList.insert(fanout);
								activityList.insertIntoTimeStack(time, wireList);
							}
						}
						
						thisWire.applyDeductiveFault();
						counter++;
						if(counter > myCircuit.getInputCount()) break;
					}
				}
				
				
				
				for(int i=0; i < timeCut; i++) {
					//time goes fast :)
					//i indicates time
					WireList wireList = activityList.retrieveFromTimeStack(i);
					if(wireList != null) {
						for(int j=0; j < wireList.getSize(); j++) {
							Wire thisWire = wireList.getWireAtIdx(j);
							
							thisWire.setValue(wireList.getWireAtIdx(j).getNxt_value());
							
							
							for(int k=0; k < thisWire.getFanOuts().size(); k++) {
								Wire fanout = thisWire.getFanOuts().get(k);
								int logic = fanout.logic();
								short time;
								if(logic != -1) {
									switch (logic) {
									case 0:
										time = fanout.getFall_time();
										break;

									default:
										time = fanout.getRise_time();
										break;
									}
									
									fanout.setNxt_value((short) logic);
									
									WireList newWireList = activityList.retrieveFromTimeStack(i + time);
									if(newWireList == null) {
										//this time stack is empty!
										newWireList = new WireList();
									}
									newWireList.insert(fanout);
									activityList.insertIntoTimeStack(i + time, newWireList);
								}
							}
						}
					}
					goNxtTime(myCircuit, i);
				}
				for(int i=0; i < myCircuit.getOnlyWireArrayLength(); i++) {
					Wire thisWire = myCircuit.getOnlyWireAtIdx(i);
					thisWire.applyDeductiveFault();
					System.out.println(
							"Line "
							+ thisWire.idx 
							/*+ " - fanouts"
							+ thisWire.getFanOuts().size()*/
							+" - level: "
							+ thisWire.level 
							+ " - name: "
							+ thisWire.name
							+" - value: " 
							+ thisWire.logicValue
							+ " - fault list: {"
							+ thisWire.getFault_list().trim()
							+ "}"
							/*+ " -rise time: "
							+ thisWire.getRise_time()
							+ " -fall time: "
							+ thisWire.getFall_time()*/
							);
				}
				myCircuit.resetForRepeatPass();
				System.out.println("---------------------------------------");
			  new ResultWindow(myCircuit.getOnlyWires(), timeCut);	
			} // end of pass 1 -> assigning primary inputs
			
		}
		
		catch(IOException e) {
			System.out.println("I/O Error: " + e);
		}
	}




	private static void goNxtTime(Circuit circuit, int time) {
		for(int i=0; i < circuit.getOnlyWireArrayLength(); i++) {
			Wire wire = circuit.getOnlyWireAtIdx(i);
			wire.addHistoryPoint(time, (short) wire.getValue());
		}
	}
}
