/*
 * ======== Standard MSP430 includes ========
 */
#include <msp430.h>
#include "Grace.h"

#define delayScalar 4
#define spiChipSelectScalar 500
#define SCALE .000732444227 //24 / ((2^16))/2)-1
#define INTSCALE 0.0003662109375 // 24/(2^16)
#define FIFO_SIZE 300 //was 300
#define delayTime 100

void SPIDummySend(void);
char SPIRead(char Address, char P2CSBIT);
void SPIWrite(char Address, char Data, char P2CSBIT);
extern void Grace_init(void);
void accelConfig(char csBit);
void readAccelData(char csBit);
void computeAccels(void);
void UARTSendArray(unsigned char *TxArray, unsigned char ArrayLength);
void UARTSendInt(unsigned int sendMe);
void initRead(void);
void uart_init(void);
void uart_putc(unsigned char c);
void uart_puts(char *str);
void uart_puti(int data);
void sendData(void);
void bufferOutput(void);
int flag=9;
int accelerometerFlag=0;
int xl = 0, xh = 0, yl = 0, yh = 0, zl = 0, zh = 0;
int xlb,xhb,ylb,yhb,zlb,zhb;
int stateFlag=0;
double magnitude=0;
int acAvgFlag = 0;
int spiFlag=0;
unsigned int intMagnitude=0;
unsigned int derek=0;
unsigned char alex=0,ryan=0,spiData;
int tempx,tempy,tempz;
double xAccel,yAccel,zAccel;


//FOR Buffered UART
//Code example grabbed from
volatile unsigned char tx_char;			//This char is the most current char to go into the UART
volatile unsigned char tx_fifo[FIFO_SIZE];	//The array for the tx fifo
volatile unsigned int tx_fifo_ptA=0;			//Theses pointers keep track where the UART and the Main program are in the Fifos
volatile unsigned int tx_fifo_ptB=0;
volatile unsigned int tx_fifo_full=0;
volatile unsigned int id=0;
unsigned int uartOverflowFlag=0;

char dmoney;

/*
 *  ======== main ========
 */
int main( void )
{	/* Stop watchdog timer from timing out during initial start-up. */
	WDTCTL = WDTPW | WDTHOLD;
	//UART Buffer Config
	tx_fifo_ptA = 0;					//Set the fifo pointers to 0
	tx_fifo_ptB = 0;
	tx_fifo_full = 0;
	Grace_init();                      // Activate Grace-generated configuration
	P2OUT |= BIT2;
	P2OUT &= ~BIT1;
	IE2 &= ~UCA0TXIE; //Disabled the TX Interrupt until needed.

	accelConfig(BIT3);				   // Configure the Accelerometers
	accelConfig(BIT4);
//	accelConfig(BIT5);
	//accelConfig(BIT7);

	IE2 |= UCA0RXIE; //Turn on the UART Ch A Interrupt for state commands from the Computer
	stateFlag=1; // Put us into state 1
	while(1)
	{
		if (uartOverflowFlag==1)
		{
			stateFlag=1;
			P2OUT &= ~BIT1;
		}
		//Do we need to check the accelerometers
		if (accelerometerFlag==1 && stateFlag==2)
		{



			//			time++;
			//Grab the accelerometer values
			//			initRead();
			//			readAccelData(BIT3);  			   // Get Accel data from accelerometers
			//  			//sendData();
			////			computeAccels();
			//
			//			initRead();
			//			readAccelData(BIT4);  			   // Get Accel data from accelerometers
			//
			////			computeAccels();
			//			//sendData();
			//
			//			initRead();
			//			readAccelData(BIT3);  			   // Get Accel data from accelerometers
			//			computeAccels();
			//			sendData(0x03);
			//dmoney=SPIRead(0xAE, BIT3);

//			dmoney=SPIRead(0xA0,BIT3);
////			dmoney=SPIRead(0xA1,BIT3);
////			dmoney=SPIRead(0xA2,BIT3);
////			dmoney=SPIRead(0xA3,BIT3);
//			dmoney=SPIRead(0xA0,BIT5);
//			dmoney=SPIRead(0xA1,BIT5);
//			dmoney=SPIRead(0xA2,BIT5);
//			dmoney=SPIRead(0xA3,BIT5);
//			dmoney=SPIRead(0xA0,BIT7);
//			dmoney=SPIRead(0xA1,BIT7);
//			dmoney=SPIRead(0xA2,BIT7);
//			dmoney=SPIRead(0xA3,BIT7);


//
			initRead();
			readAccelData(BIT3);
			//computeAccels();
			bufferOutput();

			initRead();
			readAccelData(BIT4);
			sendData();
			//computeAccels();

//			initRead();
//			readAccelData(BIT5);
//			sendData(0x03);


			//__delay_cycles(10000);
			//UARTSendInt(intMagnitude);
			//
//			initRead();
//			readAccelData(BIT7);
//			sendData(0x04);
			//computeAccels();

//			initRead();
//			readAccelData(BIT5);
//			computeAccels();
//
//			initRead();
//			readAccelData(BIT7);
//			computeAccels();
			//			initRead();
			//			readAccelData(BIT7);  			   // Get Accel data from accelerometers
			id++; //Increase our ID
			accelerometerFlag=0;
		}
	}

	return (0);
}

void bufferOutput()
{
	xlb=xl;
	xhb=xh;
	ylb=yl;
	yhb=yh;
	zlb=zl;
	zhb=zh;
}
//Acceleration configuration
void accelConfig(char csBit) {
	//dmoney=SPIRead(0xA0, BIT3);
	SPIWrite(0x20,0x37,csBit);// CNTRL_REG_1: set to normal mode; set output data rate @ 1000Hz; enable x,y,z WAS 37
//	dmoney=SPIRead(0xA0, BIT3);
	SPIWrite(0x21,0x00,csBit); // CNTRL_REG_2: set defaults value; reboots memory; turns off all filters
	SPIWrite(0x22,0x00,csBit); // CNTRL_REG_3:
	SPIWrite(0x23,0x30,csBit);//24g mode
}

void readAccelData(char csBit) {
	P2OUT &= (~csBit); //Select device

	UCB0TXBUF = 0xE8; //Multi read starting at xl
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	while(UCB0STAT & UCBUSY); //    Wait for USCI B to available again

	flag=0;
	SPIDummySend();
	//		xl=spiData; //Grab the current data and toss it into XL

	flag=1;
	SPIDummySend();
	//		xh=spiData;

	flag=2;
	SPIDummySend();
	//		yl=spiData;

	flag=3;
	SPIDummySend();
	//		yh=spiData;

	flag=4;
	SPIDummySend();
	//		zl=spiData;

	flag=5;
	SPIDummySend();
	//		zh=spiData;
	flag=9; //disabled

	while(UCB0STAT & UCBUSY); //    Wait for USCI B to available again
	P2OUT |= csBit; //Deselect device
	__delay_cycles(delayTime);

}

void SPIWrite(char Address, char Data, char P2CSBIT)
{
	P2OUT &= (~P2CSBIT); //Select device
	UCB0TXBUF = Address;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	UCB0TXBUF = Data;
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	while(UCB0STAT & UCBUSY); //    Wait for USCI B to available again

	P2OUT |= P2CSBIT; //Deselect device
	__delay_cycles(delayTime);//Give the Slave out line time to get to steadystate
}

char SPIRead(char Address, char P2CSBIT)
{
	//TODO Maybe add a check that our first bit is a one for a read.
	SPIWrite(Address,0x00,P2CSBIT); //Send meaningless data for the write
	return spiData;
}

void SPIDummySend()
{
	UCB0TXBUF = 0x00;	//Throw 8 more clock pulses for next data
	while (!(IFG2 & UCB0TXIFG));       // USCI_B0 TX buffer ready?
	while(UCB0STAT & UCBUSY); //    Wait for USCI B to available again

}

void computeAccels(void) {
	tempx = (xl | (xh<<8));			   // Form whole word of x acceleration value
	tempy = (yl | (yh<<8));			   // Form whole word of y acceleration value
	tempz = (zl | (zh<<8));			   // Form whole word of z acceleration value


		xAccel = tempx*SCALE;			   // Multiply x accleration value by scalar
		yAccel = tempy*SCALE;			   // Multiply y accleration value by scalar
		zAccel = tempz*SCALE;			   // Multiply z accleration value by scalar


	magnitude=xAccel*xAccel+yAccel*yAccel+zAccel*zAccel;
	//intMagnitude = (xAccel*xAccel+yAccel*yAccel+zAccel*zAccel)//INTSCALE;
	return;
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
void initRead()
{
	xl = 0;
	xh = 0;
	yl = 0;
	yh = 0;
	zl = 0;
	zh = 0;
	return;
}

void UARTSendInt(unsigned int sendMe){
	//Upper
	while(!(IFG2 & UCA0TXIFG)); // Wait for TX buffer to be ready for new data
	UCA0TXBUF = (char) (sendMe >> 8); //Write the character at the location specified py the pointer

	//Lower
	while(!(IFG2 & UCA0TXIFG)); // Wait for TX buffer to be ready for new data
	UCA0TXBUF = (char) (sendMe); //Write the character at the location specified py the pointer

	return;
}

//BUFFERED UART
/*uart_putc
 * Sends a char to the UART. Will wait if the UART is busy
 * INPUT: Char to send
 * RETURN: None
 */
void uart_putc(unsigned char c)
{
	tx_char = c;						//Put the char into the tx_char
	tx_fifo[tx_fifo_ptA] = tx_char;		//Put the tx_char into the fifo
	tx_fifo_ptA++;						//Increase the fifo pointer
	if(tx_fifo_ptA == FIFO_SIZE)		//Check to see if the pointer is max size. If so roll it over
	{
		tx_fifo_ptA = 0;
	}
	if(tx_fifo_ptB == tx_fifo_ptA)		//fifo full
	{
		tx_fifo_full = 1;
		uartOverflowFlag=1;
	}
	else
	{
		tx_fifo_full = 0;
	}
	IE2 |= UCA0TXIE; 					//Enable USCI_A0 TX interrupt
	return;
}

/*uart_puts
 * Sends a string to the UART. Will wait if the UART is busy
 * INPUT: Pointer to String to send
 * RETURN: None
 */
void uart_puts(char *str)				//Sends a String to the UART.
{
	while(*str) uart_putc(*str++);		//Advance though string till end
	return;
}

void uart_puti(int data)
{
	uart_putc(data>>8);
	uart_putc(data);
}

void uart_putl(long data)
{
	uart_puti(data>>16);
	uart_puti(data);
}

//Our message

//Sending every 4ms we get 11 chars per accelerometer.
void sendData()
{
	uart_putc(0x0A); //Start transmission (Line Feed)
	uart_puti(id);
	// uart_putc(accelNumber);
	//uart_puti(intMagnitude);
	//Accel1
	uart_putc(xhb);
	uart_putc(xlb);
	uart_putc(yhb);
	uart_putc(ylb);
	uart_putc(zhb);
	uart_putc(zlb);

	uart_putc(xh);
	uart_putc(xl);
	uart_putc(yh);
	uart_putc(yl);
	uart_putc(zh);
	uart_putc(zl);
	uart_putc(0x0D); //End of Transmission (Carriage Return)
	return;
}











