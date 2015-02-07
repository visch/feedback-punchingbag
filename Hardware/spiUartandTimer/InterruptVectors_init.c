/*
 *  This file is automatically generated and does not require a license
 *
 *  ==== WARNING: CHANGES TO THIS GENERATED FILE WILL BE OVERWRITTEN ====
 *
 *  To make changes to the generated code, use the space between existing
 *      "USER CODE START (section: <name>)"
 *  and
 *      "USER CODE END (section: <name>)"
 *  comments, where <name> is a single word identifying the section.
 *  Only these sections will be preserved.
 *
 *  Do not move these sections within this file or change the START and
 *  END comments in any way.
 *  ==== ALL OTHER CHANGES WILL BE OVERWRITTEN WHEN IT IS REGENERATED ====
 *
 *  This file was generated from
 *      C:/ti/grace/grace_2_20_02_32/packages/ti/mcu/msp430/csl/interrupt_vectors/InterruptVectors_init.xdt
 */
#include <msp430.h>
#include "uart_fifo_tx.h"

/* USER CODE START (section: InterruptVectors_init_c_prologue) */
extern int xl, xh, yl, yh, zl, zh;
extern int flag;
extern int accelerometerFlag;
extern int tempx, tempy, tempz;
extern int stateflag;
extern double xAccel, yAccel, zAccel;
extern int spiflag;
extern int stateFlag;
static volatile char data;
extern char spiData;
extern void readAccelData(int csBit);
extern void computeAccels(void);
extern void uart_puts(char *str);
extern void uart_putc(unsigned char c);
extern int uartOverflowFlag;
int counter=0;

/* USER CODE END (section: InterruptVectors_init_c_prologue) */

/*
 *  ======== InterruptVectors_graceInit ========
 */
void InterruptVectors_graceInit(void)
{
}


/* Interrupt Function Prototypes */


/*
 *  ======== USCI A0/B0 TX Interrupt Handler Generation ========
 *
 * Here are several important notes on using USCI_A0/B0 TX interrupt Handler:
 * 1. User could use the following code as a template to service the interrupt
 *    handler. Just simply copy and paste it into your user definable code
 *    section.
 *  For UART and SPI configuration:

    if (IFG2 & UCA0TXIFG) {

    }
    else if (IFG2 & UCB0TXIFG) {

    }

 *  For I2C configuration:
    if (IFG2 & UCA0/B0TXIFG) {

    }
    else if (IFG2 & UCA0/B0RXIFG) {

    }


 * 2. User could also exit from low power mode and continue with main
 *    program execution by using the following instruction before exiting
 *    this interrupt handler.
 *
 *    __bic_SR_register_on_exit(LPMx_bits);
 */
/*
 * IFG2, Interrupt Flag Register 2
 *
 * UCB0TXIFG -- Interrupt pending
 * UCB0RXIFG -- Interrupt pending
 * ~UCA0TXIFG -- No interrupt pending
 * ~UCA0RXIFG -- No interrupt pending
 *
 * Note: ~<BIT> indicates that <BIT> has value zero
 */
//IFG2 &= ~(UCB0TXIFG | UCB0RXIFG);

#pragma vector=USCIAB0TX_VECTOR
__interrupt void USCI0TX_ISR_HOOK(void)
{

	//For Buffered UART


	if ((IFG2 & UCA0TXIFG) & (IE2 & UCA0TXIE)) {
		UCA0TXBUF = tx_fifo[tx_fifo_ptB];		//Copy the fifo into the TX buffer
		tx_fifo_ptB++;

		if(tx_fifo_ptB == FIFO_SIZE)			//Roll the fifo pointer over
		{
			tx_fifo_ptB = 0;
		}
		if(tx_fifo_ptB == tx_fifo_ptA)			//Fifo pointers the same no new data to transmit
		{
			IE2 &= ~UCA0TXIE; 					//Turn off the interrupt to save CPU
		}
	}

/*
	else if (IFG2 & UCB0TXIFG) {
	//Done Sending
		P2OUT &= (~BIT3);
	}
	//IE2 |= UCA0TXIE;
	 * */

	return;

	/* USER CODE END (section: USCI0TX_ISR_HOOK) */
}

/*
 *  ======== USCI A0/B0 RX Interrupt Handler Generation ========
 *
 * Here are several important notes on using USCI_A0/B0 RX interrupt Handler:
 * 1. User could use the following code as a template to service the interrupt
 *    handler. Just simply copy and paste it into your user definable code
 *    section.
 *  For UART and SPI configuration:

    if (IFG2 & UCA0RXIFG) {

    }
    else if (IFG2 & UCB0RXIFG) {

    }

 *  For I2C configuration:
    if (UCB0STAT & UCSTTIFG) {

    }
    else if (UCB0STAT & UCSTPIFG) {

    }
    else if (UCB0STAT & UCNACKIFG) {

    }
    else if (UCB0STAT & UCALIFG) {

    }

 * 2. User could also exit from low power mode and continue with main
 *    program execution by using the following instruction before exiting
 *    this interrupt handler.
 *
 *    __bic_SR_register_on_exit(LPMx_bits);
 */
/*
 * IFG2, Interrupt Flag Register 2
 *
 * UCB0TXIFG -- Interrupt pending
 * UCB0RXIFG -- Interrupt pending
 * ~UCA0TXIFG -- No interrupt pending
 * ~UCA0RXIFG -- No interrupt pending
 *
 * Note: ~<BIT> indicates that <BIT> has value zero
 */
//IFG2 &= ~(UCB0TXIFG | UCB0RXIFG);
#pragma vector=USCIAB0RX_VECTOR

__interrupt void USCI0RX_ISR_HOOK(void)
{
	/*
	 * IE2, Interrupt Enable Register 2
	 *
	 * UCB0TXIE -- Interrupt enabled
	 * UCB0RXIE -- Interrupt enabled
	 * ~UCA0TXIE -- Interrupt disabled
	 * ~UCA0RXIE -- Interrupt disabled
	 *
	 * Note: ~<BIT> indicates that <BIT> has value zero
	 */

	if (IFG2 & UCA0RXIFG)
		{
			data = UCA0RXBUF;

			uart_puts("Received command: ");
			uart_putc(data);
			uart_puts("\n\r");

			switch(data){
			case 'B':
			{
				stateFlag=2;
				P2OUT |= BIT1;
				uartOverflowFlag=0;
			}
			break;
	//		case 'r':
	//		{
	//			P1OUT &= ~BIT0;
	//		}
	//		break;
			case 'E':
			{
				stateFlag=1;
				P2OUT &= ~BIT1;
			}
			break;

			default:
			{
				uart_puts("Unknown Command: ");
				uart_putc(data);
				uart_puts("\n\r");
			}
			}
		}


	//SPI Reading
	if (IFG2 & UCB0RXIFG) {
		spiData=UCB0RXBUF;
		//IE2 &= ~(UCB0RXIE);
		//IFG2 &= ~(UCB0RXIFG);
		//IFG2 = IFG2 & 0x0A; //Clears interrupt flags
		// USER CODE START (section: USCI0RX_ISR_HOOK)
		if(flag == 0)
		{
			xl = spiData;
		}
		else if (flag == 1)
		{
			xh = spiData;
		}
		else if (flag == 2)
		{
			yl = spiData;
		}
		else if (flag == 3)
		{
			yh = spiData;
		}
		else if (flag == 4)
		{
			zl = spiData;
		}
		else if (flag == 5)
		{
			zh = spiData;
		}
		//IE2 |= UCB0RXIE;
	}



	return;
	// USER CODE END (section: USCI0RX_ISR_HOOK)
}



/*
 *  ======== Timer1_A3 Interrupt Service Routine ========
 */
#pragma vector=TIMER1_A0_VECTOR
__interrupt void TIMER1_A0_ISR_HOOK(void)
{
	TA1CCTL0 &= ~CCIE;				   // Disable Interrupt
	/* USER CODE START (section: TIMER1_A0_ISR_HOOK) */
	if (counter>= 4)
	{


		accelerometerFlag=1; //Get our main program to check the accelerometers

		counter=1;
	}
	counter++;
	TA1CCTL0 |= CCIE;				   // Enable Interrupt
    /* USER CODE END (section: TIMER1_A0_ISR_HOOK) */
}

