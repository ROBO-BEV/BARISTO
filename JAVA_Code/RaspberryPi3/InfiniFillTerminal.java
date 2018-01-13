/**
 * @file     InfiniFillTerminal.java
 * @author   Blaze Sanders (@ROBO_BEV)
 * @email    blaze@infinifill.com
 * @updated  13 JAN 2018
 *
 * @version 0.1
 * @brief InfiniFill specific JAVA terminal class
 *
 * @section DESCRIPTION
 *
 * Yes we are programming a coffee robot in JAVA...
 */
import java.util.Scanner;

public class InfiniFillTerminal {

/**
* @brief Promt and then get user input via terminal
* @section DESCRIPTION
*
* Based off a Java Scanner class from http://alvinalexander.com
*/
 public static void GetUserInput (String[] args){
   
   //Create a scanner so we can read the command-line input
   Scanner scanner = new Scanner(System.in);

   //Prompt user to enter kiosk boot up mode
   System.out.print("Select boot mode (2 = PRODUCTION, 1 = FIELD, 0 = TESTING): ");
   int bootMode = scanner.nextInt(); // Get user input as a Integer
   if(bootMode < 0 || bootMode >2){
   	 if(InfiniFill.DEBGUG_STATEMENTS_ON){
       System.out.print("You entered an invalid boot mode, please try again.");
   	   //TO-DO: KEEP LOOPING
   	 }
   }
   
   //Prompt user to enter version of hardware they are interacting with
   System.out.print("Enter Kiosk hardware version (e.g. 1.5, 0.1, 0.42, etc): ");
   double verNum = scanner.nextDouble();
   if(verNum < 0 || verNum > InfiniFill.CURRENT_KIOSK_HW_VERSION){
   	 if(InfiniFill.DEBGUG_STATEMENTS_ON){
       System.out.print("You entered an invalid kiosk hardware version number, please try again.");
   	   //TO-DO: KEEP LOOPING
   	 }
   }
   

   //System.out.println(String.format("Booting InfiniFill kiosk V %d in %i mode. ", verNum, bootMode));

 }//END main() FUNCTION
}//END JavaTerminal() CLASS