/*
 * ======== Standard MSP430 includes ========
 */
#include <msp430.h>
#include "Grace.h"
#include <math.h>

/*
 * ======== Grace related declaration ========
 */
extern void Grace_init(void);
void accelConfig(int csBit);
void readAccelData(int csBit);
void computeAccels(int accelNum);
void UARTSendArray(unsigned char *TxArray, unsigned char ArrayLength);

int accelerometerFlag=0;
int xl = 0, xh = 0, yl = 0, yh = 0, zl = 0, zh = 0;
int tempx[4] = {0, 0, 0, 0};
int tempy[4] = {0, 0, 0, 0};
int tempz[4] = {0, 0, 0, 0};
int flag = 0;
double sqrtVal;
int sqrtValInt;
int acAvgFlag = 0;

#define SCALE  0.000366222 //was 0.007324

/*
 *  ======== main ========
 */
int main( void )
{
	/* Stop watchdog timer from timing out during initial start-up. */
	WDTCTL = WDTPW | WDTHOLD;

	sqrtVal = sqrt(612);
	sqrtValInt = sqrt(612);

	Grace_init();                      // Activate Grace-generated configuration

	accelConfig(BIT3);				   // Configure the Accelerometer
	accelConfig(BIT4);				   // Configure the Accelerometer
	accelConfig(BIT5);				   // Configure the Accelerometer
	accelConfig(BIT7);				   // Configure the Accelerometer


	while(1)
	{
		// Do we need to check the accelerometers
		if (accelerometerFlag==1)
		{
			// Grab the accelerometer values
			readAccelData(BIT3);  			// Get Accel data from accelerometer 0
			computeAccels(0);
			// Grab the accelerometer values
			readAccelData(BIT4);  			// Get Accel data from accelerometer 1
			computeAccels(1);
			// Grab the accelerometer values
			readAccelData(BIT5);  			// Get Accel data from accelerometer 2
			computeAccels(2);
			// Grab the accelerometer values
			readAccelData(BIT7);  			// Get Accel data from accelerometer 3
			computeAccels(3);

			UARTSendArray(tempx, 4);        // Send some bytes
			accelerometerFlag=0;	        // Turn off the flag
		}
	}
	return (0);
}

void accelConfig(int csBit) {
	P2OUT &= (~csBit); 			       // Select Device
	__delay_cycles(2000);		       // Give chipselect time some time to be low

	UCB0TXBUF = 0x20;			       // CNTRL_REG_1: set to normal mode; set output data rate @ 1000Hz; enable x,y,z
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0x37;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?

	__delay_cycles(20);			   // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device

	__delay_cycles(20);			       // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			       // Select Device

	UCB0TXBUF = 0x21;			       // CNTRL_REG_2: set defaults value; reboots memory; turns off all filters
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0x00;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?

	__delay_cycles(2000);			   // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device

	__delay_cycles(20);			       // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			       // Select Device

	UCB0TXBUF = 0x23;			       // CTRL_REG_4: set continuous updates of data; set data as little Endian; 24G mode; self test enable; 4-wire SPI
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0x10;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?

	__delay_cycles(2000);			   // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device
}

void readAccelData(int csBit) {
	__delay_cycles(1);			   	   // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			   	   // Select Device
	__delay_cycles(1);

	flag = 0;
	UCB0TXBUF = 0xA8;
	while (!(IFG2 & UCB0TXIFG));   	   // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0xA8;
	while (!(IFG2 & UCB0TXIFG));   	   // USCI_B0 TX buffer ready?

	__delay_cycles(1);			   	   // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device
	__delay_cycles(100);			       // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			       // Select Device
	__delay_cycles(1);

	flag = 1;
	UCB0TXBUF = 0xA9;
	while (!(IFG2 & UCB0TXIFG));   	   // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0xA9;
	while (!(IFG2 & UCB0TXIFG));   	   // USCI_B0 TX buffer ready?

	__delay_cycles(100);			   	   // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device

	__delay_cycles(1);			   	   // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			       // Select Device
	__delay_cycles(1);


	flag = 2;
	UCB0TXBUF = 0xAA;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0xAA;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?

	__delay_cycles(1);			       // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device
	__delay_cycles(1);			       // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			       // Select Device
	__delay_cycles(1);

	flag = 3;
	UCB0TXBUF = 0xAB;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0xAB;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?

	__delay_cycles(1);			       // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device
	__delay_cycles(1);			       // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			       // Select Device
	__delay_cycles(1);

	flag = 4;
	UCB0TXBUF = 0xAC;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0xAC;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?

	__delay_cycles(100);			       // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device
	__delay_cycles(1);			       // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			       // Select Device
	__delay_cycles(1);

	flag = 5;
	UCB0TXBUF = 0xAD;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0xAD;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?

	__delay_cycles(2);			       // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
	P2OUT |= (csBit);                  // Unselect Device

}

void computeAccels(int accelNum) {
	tempx[accelNum] = (xl | (xh<<8));			   // Form whole word of x acceleration value
	tempy[accelNum] = (yl | (yh<<8));			   // Form whole word of y acceleration value
	tempz[accelNum] = (zl | (zh<<8));			   // Form whole word of z acceleration value
}

void UARTSendArray(unsigned char *TxArray, unsigned char ArrayLength){
	// Send number of bytes Specified in ArrayLength in the array at using the hardware UART 0
	// Example usage: UARTSendArray("Hello", 5);
	// int data[2]={1023, 235};
	// UARTSendArray(data, 4); // Note because the UART transmits bytes it is necessary to send two bytes for each integer hence the data length is twice the array length

	while(ArrayLength--){ // Loop until StringLength == 0 and post decrement
		while(!(IFG2 & UCA0TXIFG)); // Wait for TX buffer to be ready for new data
		UCA0TXBUF = *TxArray; //Write the character at the location specified py the pointer
		TxArray++; //Increment the TxString pointer to point to the next character
	}
	return;
}
