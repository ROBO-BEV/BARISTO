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
 public static void GetInput (String[] args){
   // create a scanner so we can read the command-line input
   Scanner scanner = new Scanner(System.in);
   
   System.out.print("Which mode would you like to boot up in? (2 = PRODUCTION, 1 = FIELD, 0 = TESTING): ");
   int bootMode = scanner.nextInt(); // Get user input as a Integer

   //Prompt for their age
   System.out.print("What kiosk version are you connected to? (e.g. 0.1, 1.5, .99.42, etc");

   // get the age as an double
   double verNum = scanner.nextDouble();

   System.out.println(String.format("Booting InfiniFill kiosk V %d in %d mode. ", verNum, bootMode));

 }//END main() FUNCTION

}//END JavaTerminal() CLASS