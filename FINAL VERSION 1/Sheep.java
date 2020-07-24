/**
 * Sheep.java
 * Version: 1.0
 * Author: Theo Liu
 * Date: 12/09/2018
 * Description: Creates Sheep object
 */

class Sheep extends Animal {
  
  private static String type = "S";
  
  public String getType(){
    return type;
  }
  
  public Sheep(int health, int x, int y) {
    if (getAlpha() % 20 == 0){
      setMaxHealth(health*2);
      setNumHealth(health*2);
    } else {
      setMaxHealth(health*2);
      setNumHealth(health*2);
    }
    setCol(x);
    setRow(y);
    setAge(0);
  }
}