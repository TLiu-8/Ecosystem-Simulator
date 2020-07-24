/**
 * EcosystemSimulatorDemo.java
 * Version: 1.0
 * Author: Theo Liu
 * Date: 12/09/2018
 * Description: Takes inputs and runs the EcoSim
 * Add Ons:
 * 1. 
 */

import java.util.Scanner;  // import statements

class EcoSystemSimulatorDemo {
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
       
    int side, total;
    int wolfNum, sheepNum, plantNum, wolfHealth, sheepHealth, plantHealth;
    double plantGrowth = 0;
    
    // recieving inputs
     do {
      System.out.println("What do you want the square side length to be:");
      side = input.nextInt();
    } while(side <= 1);
    input.nextLine();
    
    do{
      wolfNum = 0;
      sheepNum = 0;
      plantNum = 0; 
      wolfHealth = 0;
      sheepHealth = 0;
      plantHealth = 0;
      plantGrowth = 0;
     
      System.out.println("Input the number of wovles you want");
      wolfNum = input.nextInt();
      System.out.println("Input the health of wolves you want");
      wolfHealth = input.nextInt();
      input.nextLine();
    
      System.out.println("Input the number of sheep you want");
      sheepNum = input.nextInt();
      System.out.println("Input the health of sheep you want");
      sheepHealth = input.nextInt();
      input.nextLine();
    
    
      System.out.println("Input the number of plant you want");
      plantNum = input.nextInt();
      System.out.println("Input the health of plants you want");
      plantHealth = input.nextInt();
   
      System.out.println("What do you want the plant growing rate to be? (Ex: if you want 4 plants to grow per day, input 4, if you want 1 plant to grow every four days input 0.25)");
      plantGrowth = input.nextDouble();
   
      total = plantNum + sheepNum + wolfNum;
    } while (total > side*side);
    
    // simulator
    EcoSystemSimulator world = new EcoSystemSimulator(wolfNum,wolfHealth, sheepNum,sheepHealth,plantHealth,plantNum, plantGrowth,side);
    world.simulate();
    
    System.out.println("Enter to exit");
    input.nextLine();
    input.nextLine();
    input.close();
    System.exit(0);
  }
}