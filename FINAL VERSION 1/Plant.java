/**
 * Plant.java
 * Version: 1.0
 * Author: Theo Liu
 * Date: 12/09/2018
 * Description: Creates Plant Object, has special type (grass, flowers, plants,
 */

class Plant extends Organism {

  private static String type = "P";
  
  private int specialType = (int) (Math.random()*100);
  
  public String getSpecialType() {
    if (specialType % 10 == 0){
      return "F";
    } else if (specialType == 99){
      return "G";
    } else {
      return "GR";
    }
  }
  
  public Plant(int health, int x, int y){
    if (specialType % 10 == 0){
      setMaxHealth(health*2);
    } else if (specialType == 99){
      setMaxHealth(health*3);
    } else {
      setMaxHealth(health);
    }
    setNumHealth(health);
    setCol(x);
    setRow(y);
    setAge(0);
  }
  
  public String getType(){ 
    return type;
  }
  
}