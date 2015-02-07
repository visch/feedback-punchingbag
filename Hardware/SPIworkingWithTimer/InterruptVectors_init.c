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

/* USER CODE START (section: InterruptVectors_init_c_prologue) */
extern int xl, xh, yl, yh, zl, zh;
extern int flag;
extern int tempx, tempy, tempz;
extern double xAccel, yAccel, zAccel;

extern void readAccelData(int csBit);
extern void computeAccels(void);
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
	/* USER CODE START (section: USCI0TX_ISR_HOOK) */
	IE2 &= ~(UCB0TXIE);
	IFG2 &= ~(UCB0TXIFG);
	IE2 |= UCB0TXIE;
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

	int deleteMe=0;
	deleteMe=10;
	if (IFG2 & UCB0RXIFG) {
		IE2 &= ~(UCB0RXIE);
		//IFG2 &= ~(UCB0RXIFG);
		IFG2 = IFG2 & 0x0A; //Clears interrupt flags
		/* USER CODE START (section: USCI0RX_ISR_HOOK) */
		if(flag == 0)
		{
			xl = UCB0RXBUF;
		}
		else if (flag == 1)
		{
			xh = UCB0RXBUF;
		}
		else if (flag == 2)
		{
			yl = UCB0RXBUF;
		}
		else if (flag == 3)
		{
			yh = UCB0RXBUF;
		}
		else if (flag == 4)
		{
			zl = UCB0RXBUF;
		}
		else if (flag == 5)
		{
			zh = UCB0RXBUF;
		}
	}
	IE2 |= UCB0RXIE;
	return;
	/* USER CODE END (section: USCI0RX_ISR_HOOK) */
}

/*
 *  ======== Timer1_A3 Interrupt Service Routine ========
 */
#pragma vector=TIMER1_A0_VECTOR
__interrupt void TIMER1_A0_ISR_HOOK(void)
{
    /* USER CODE START (section: TIMER1_A0_ISR_HOOK) */
	TA1CCTL0 &= ~CCIE;				   // Disable Interrupt

	TA1CCTL0 |= CCIE;				   // Enable Interrupt
    /* USER CODE END (section: TIMER1_A0_ISR_HOOK) */
}