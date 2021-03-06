/**
 * Wolf.java
 * Version: 1.0
 * Author: Theo Liu
 * Date: 12/09/2018
 * Description: Creates Wolf Object, can be compared to other wolves.
 */

class Wolf extends Animal implements Comparable<Wolf>{
  
  private static String type = "W";
  private int health = getNumHealth();
   
  public String getType(){ 
    return type;
  }
  
  public Wolf(int health, int x, int y) {
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
  
  public int compareTo(Wolf fighter){
    if (health == fighter.getNumHealth()){
      return 0;
    } else if (health > fighter.getNumHealth()){
      return 1;
    } else {
      return -1;
    }
  }
}