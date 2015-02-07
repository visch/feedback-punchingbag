package comGui; 
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class SerialPortReaderWrapper implements Runnable  {
	RawDataStorage rawDataObject;
	SerialPortReader eventHandler = new SerialPortReader(this);
	PrintWriter dumpWriter;
	SerialPort serialPort;
	testRead parent;
	
	//FOR TESTING
	
	
	protected static int numberOfCharsToWaitFor=160; //Make sure this is a multiple of your messaging length (As of writing this its 10)
	protected static int messageSize=16;
	
	@SuppressWarnings("static-access")
	public void run() {
		try {
			serialPort.openPort(); //Open serial port
			serialPort.setParams(115200, 8, 1, serialPort.PARITY_ODD);//Set parameters.
			int mask = SerialPort.MASK_RXCHAR;//Prepare mask
			serialPort.setEventsMask(mask);//Set mask
			//setEventHand();
			serialPort.addEventListener(eventHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
	
	public SerialPortReaderWrapper(String portName, testRead parentClass, RawDataStorage messagesObj)
	{
		this.rawDataObject = messagesObj;
		parent = parentClass;
		serialPort = new SerialPort(portName);


	}
	
	//Takes the message from the serial event and deals with it

	public void buildMessage(byte[] stuff) throws Exception {
		@SuppressWarnings("unused")
		String sByte, eByte;
		String accelNum[] = new String[2], messNum[] = new String[2];
		String xAccelStr[] = new String[2], yAccelStr[] = new String[2], zAccelStr[] = new String[2];
		int xAccel[] = new int[2], yAccel[] = new int[2], zAccel[] = new int[2];	
		List<String> hexVals = new LinkedList<String>();


		while((rawDataObject.leftOverMessages != null) && (rawDataObject.leftOverMessages.size() != 0))
		{
			int index;
			index=0;
			hexVals.add(rawDataObject.leftOverMessages.get(index)); //Grab the last thing in the list and put it in the front 
			rawDataObject.leftOverMessages.remove(index);
		}

		//Add stuff from the message that the serial event gives us
		for (int i = 0; i<stuff.length; i++) {
			hexVals.add(String.format("%02X ", stuff[i]).trim());
		}
//
//<<<<<<< HEAD
//		for (int j = 0; j<hexVals.length; j++) {
//			if((j+4) <hexVals.length){				//make sure full message is there?  why not increment by 4 in other loop?
//				if((hexVals[j].equals("0A")) && (hexVals[j+4].equals("0D")))
//				{
//					sByte = hexVals[j];
//					accelNum = hexVals[j+1];
//					dataStr = hexVals[j+2] + hexVals[j+3];		//Concatenate upper and lower bits
//					//TODO: TEST TO SEE IF BIG VAL GIVES NEGATIVE ANS:(8 bit data in 16bit var. upper bits always 0 so never negative tested with FFF0)
//					dataVal = Integer.parseInt(dataStr, 16);
//					eByte = hexVals[j+4];
//					amsgs.addMessage(dataVal, accelNum);
//					j = j + 4;
//				}//end if  start
//			}//end if  length
//		}// end for loop
//	}// end build message
//=======
		for (int j = 0; j<hexVals.size(); j++) {
			if((j+15) < hexVals.size()){
				if((hexVals.get(j).equals("0A")) && (hexVals.get(j+15).equals("0D")))
				{
					sByte = hexVals.get(j);
					messNum[0] = hexVals.get(j+1) + hexVals.get(j+2);
					xAccelStr[0] = hexVals.get(j+3) + hexVals.get(j+4);
					yAccelStr[0] = hexVals.get(j+5) + hexVals.get(j+6);
					zAccelStr[0] = hexVals.get(j+7) + hexVals.get(j+8);
					xAccelStr[1] = hexVals.get(j+9) + hexVals.get(j+10);
					yAccelStr[1] = hexVals.get(j+11) + hexVals.get(j+12);
					zAccelStr[1] = hexVals.get(j+13) + hexVals.get(j+14);
					eByte = hexVals.get(j+15);
					
					/*sByte = hexVals.get(j+22);
					messNum[2] = hexVals.get(j+23) + hexVals.get(j+24);
					accelNum[2] = hexVals.get(j+25);
					xAccelStr[2] = hexVals.get(j+26) + hexVals.get(j+27);
					yAccelStr[2] = hexVals.get(j+28) + hexVals.get(j+29);
					zAccelStr[2] = hexVals.get(j+30) + hexVals.get(j+31);
					eByte = hexVals.get(j+32);*/

					xAccel[0] = Integer.valueOf(xAccelStr[0],16).shortValue();
					yAccel[0] = Integer.valueOf(yAccelStr[0],16).shortValue();
					zAccel[0] = Integer.valueOf(zAccelStr[0],16).shortValue();
					
					xAccel[1] = Integer.valueOf(xAccelStr[1],16).shortValue();
					yAccel[1] = Integer.valueOf(yAccelStr[1],16).shortValue();
					zAccel[1] = Integer.valueOf(zAccelStr[1],16).shortValue();
					
					/*xAccel[2] = Integer.valueOf(xAccelStr[2],16).shortValue();
					yAccel[2] = Integer.valueOf(yAccelStr[2],16).shortValue();
					zAccel[2] = Integer.valueOf(zAccelStr[2],16).shortValue();*/
					
					rawDataObject.addMessage(accelNum, messNum, xAccel, yAccel, zAccel);
					j = j + 15;
				}
			}

			else{
				//We are in here because J is to big for the current message and we need to save the left over characters
				rawDataObject.leftOverMessages.add(hexVals.get(j));
			}
		}
	}
	



	//Getter
	public RawDataStorage getAmsgs(){
		return rawDataObject;
	}

	/* Static Class for handling the events that are received from the Serial Port */
	static class SerialPortReader implements SerialPortEventListener {
		SerialPortReaderWrapper parentClass;

		public SerialPortReader(SerialPortReaderWrapper theParent) {
			this.parentClass = theParent;
		}
		public void serialEvent(SerialPortEvent event) {
			if(parentClass.parent.getComPortFlag() == 0)
			{
				int eventValue = event.getEventValue();
				if(event.isRXCHAR()){//If data is available
					if(eventValue >= numberOfCharsToWaitFor){//Check bytes count in the input buffer
						//Read data, if waitForChars bytes available 
						try {
							byte buffer[] = parentClass.serialPort.readBytes(numberOfCharsToWaitFor);
							parentClass.buildMessage(buffer);
							//parentClass.parent.setComPortFlag(1);
							//parentClass.removeEventHand();
						}
						catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ("Something went wrong" + ex), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			else
			{
				
			}
		}
	}
}


