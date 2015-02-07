package backend;

import java.util.concurrent.TimeUnit;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortTester {
	public static void terminate(String comPort) throws SerialPortException, InterruptedException {
		byte stopByte = 0x45;
		SerialPort serialPort = new SerialPort(comPort);
		 serialPort.openPort();//Open serial port
		   serialPort.setParams(115200, 8, 1, SerialPort.PARITY_ODD);//Set parameters.
		for (int i = 0; i < 20; i++) {
			
			serialPort.writeByte(stopByte);
			TimeUnit.MILLISECONDS.sleep(30);
		}
		serialPort.closePort();
	}
    @SuppressWarnings("static-access")
	public static boolean testSerialPort(String comPort) throws InterruptedException
    {
    	
    	
    	boolean retValue=false;
        SerialPort serialPort = new SerialPort(comPort);
        try {
        	
        	terminate(comPort);
            serialPort.openPort();//Open serial port
            serialPort.setParams(115200, 8, 1, serialPort.PARITY_ODD);//Set parameters.
            serialPort.writeBytes("p".getBytes());//Write data to port
        	TimeUnit.MILLISECONDS.sleep(300);
            String test = serialPort.readString();
            serialPort.closePort();//Close serial port
            
            if (test != null && test.equals("Received command: p\n\rUnknown Command: p\n\r"))
            {
            	retValue=true;
            }
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
        return retValue;
    }
}