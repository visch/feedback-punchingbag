/*
 * ======== Standard MSP430 includes ========
 */
#include <msp430.h>
#include "Grace.h"

/*
 * ======== Grace related declaration ========
 */
extern void Grace_init(void);
void accelConfig(int csBit);
void readAccelData(int csBit);
void computeAccels(void);

int xl = 0, xh = 0, yl = 0, yh = 0, zl = 0, zh = 0;
int tempx = 0, tempy = 0, tempz = 0;
double xAccel = 0, yAccel = 0, zAccel = 0;
int flag = 0;
int acAvgFlag = 0;

#define SCALE  0.000366222 //was 0.007324

/*
 *  ======== main ========
 */
int main( void )
{
	/* Stop watchdog timer from timing out during initial start-up. */
	WDTCTL = WDTPW | WDTHOLD;

	Grace_init();                      // Activate Grace-generated configuration

	accelConfig(BIT3);				   // Configure the Accelerometer

	while(1) {
		readAccelData(BIT3);  			   // Get Accel data from accelerometers
		computeAccels();
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

	__delay_cycles(2000);			   // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
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
	__delay_cycles(1);			       // Give chipselect time some time to be high
	P2OUT &= (~csBit); 			       // Select Device
	__delay_cycles(1);

	flag = 1;
	UCB0TXBUF = 0xA9;
	while (!(IFG2 & UCB0TXIFG));   	   // USCI_B0 TX buffer ready?
	UCB0TXBUF = 0xA9;
	while (!(IFG2 & UCB0TXIFG));   	   // USCI_B0 TX buffer ready?

	__delay_cycles(1);			   	   // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
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

	__delay_cycles(1);			       // Give some delay between configure writes (Tdis -- LIS331 Timing Diagram)
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

void computeAccels(void) {
	tempx = (xl | (xh<<8));			   // Form whole word of x acceleration value
	tempy = (yl | (yh<<8));			   // Form whole word of y acceleration value
	tempz = (zl | (zh<<8));			   // Form whole word of z acceleration value

	xAccel = tempx * SCALE;			   // Multiply x accleration value by scalar
	yAccel = tempy * SCALE;			   // Multiply y accleration value by scalar
	zAccel = tempz * SCALE;			   // Multiply z accleration value by scalar
}
