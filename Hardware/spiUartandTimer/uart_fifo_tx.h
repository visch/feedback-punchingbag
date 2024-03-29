/*
 *uart_fifo_tx.h
 *Version: 1.0
 *Parker Dillmann
 *The Longhorn Engineer (c) 2013 
 *www.longhornengineer.com
 *
 *Check bottom of file for License.
 *
 *This is a uart_fifo.h file of the FIFO UART TX ONLY Hardware demo using USCI on the MSP430G2553 microcontroller.
 *Set your baud rate in your terminal to 9600 8N1.
 *When using your TI MSP-430 LaunchPad you will need to cross the TXD and RXD jumpers.
 *
*/

/*tx_fifo_full
* This flag is to be used by other modules to check and see if the rx fifo is full.
* This is READ ONLY. Do not write to it or the UART may crash.
*/
#define FIFO_SIZE 300
extern volatile unsigned int tx_fifo_full;
extern volatile unsigned char tx_char;			//This char is the most current char to go into the UART
extern volatile unsigned char tx_fifo[FIFO_SIZE];	//The array for the tx fifo
extern volatile unsigned int tx_fifo_ptA;			//Theses pointers keep track where the UART and the Main program are in the Fifos
extern volatile unsigned int tx_fifo_ptB;
extern volatile unsigned int tx_fifo_full;

/*uart_init
* Sets up the UART interface via USCI
* INPUT: None
* RETURN: None
*/
void uart_init(void);

/*uart_putc
* Send a char to the UART tx fifo
* INPUT: char
* RETURN: none
*/
void uart_putc(unsigned char c);


/*uart_puts
* Sends a string to the UART. Will wait if the UART is busy
* INPUT: Pointer to String to send
* RETURN: None
*/
void uart_puts(char *str);

/*
â”Œâ”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”�
â”‚                           TERMS OF USE: MIT License                                  â”‚
â”œâ”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”¤
â”‚Permission is hereby granted, free of charge, to any person obtaining a copy of this  â”‚
â”‚software and associated documentation files (the "Software"), to deal in the Software â”‚
â”‚without restriction, including without limitation the rights to use, copy, modify,    â”‚
â”‚merge, publish, distribute, sublicense, and/or sell copies of the Software, and to    â”‚
â”‚permit persons to whom the Software is furnished to do so, subject to the following   â”‚
â”‚conditions:                                                                           â”‚
â”‚                                                                                      â”‚
â”‚The above copyright notice and this permission notice shall be included in all copies â”‚
â”‚or substantial portions of the Software.                                              â”‚
â”‚                                                                                      â”‚
â”‚THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,   â”‚
â”‚INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A         â”‚
â”‚PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT    â”‚
â”‚HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION     â”‚
â”‚OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE        â”‚
â”‚SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.                                â”‚
â””â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”˜
*/
