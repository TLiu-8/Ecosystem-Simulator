/**
 * Animal.java
 * Version: 1.0
 * Author: Theo Liu
 * Date: 12/09/2018
 * Description: Creates an Animal Object, can move, get pregnant, has gender, could be an alpha
 */

abstract class Animal extends Organism {
  
  abstract public String getType();
  
  private int gender = (int)( Math.random() * 2);
  private int alphaFactor = (int)( Math.random() * 100);
  private int lastPregnancy = 0;
  private int size = (int)(( Math.random()*3)+5);
 
  public int getAlpha(){
    return alphaFactor;
  }
  
  public int getGender(){ 
    return gender;
  }
 
  public int getSize(){
    return size;
  }
  
  public void setSize(int size){
    this.size = size;
  }

  public boolean checkPregnancy(){
    if (getAge() - lastPregnancy < 10 && getGender() == 0){
      return false;
    } else {
      return true;
    }
  }
  
  public void growing(){
   
  }
   
  private void moveRight(){
    setRow(getRow()+1);
  }
  
  private void moveLeft(){
    setRow(getRow()-1);
  }
  
  private void moveDown(){
    setCol(getCol()+1);
  }
  
  private void moveUp(){
    setCol(getCol()-1);
  }
  
  public void move(int side, Organism[][] map){
    boolean noLeft = false;
    boolean noDown = false;
    boolean noUp = false;
    boolean noRight = false;
        
    int currentY = getRow();
    int currentX = getCol();
          
    // get random direction
    int direction = (int)( Math.random() * 5);
          
    if (currentY == 0){
     noLeft = true;
    }
    if (currentY == (side-1)){
      noRight = true;
    }
    if (currentX == 0){
      noUp = true;
    }
    if (currentX == (side-1)){
      noDown = true;
    }
    
    if (noUp == true){
      if (noLeft == true){
        do {
          direction = (int)( Math.random() * 5);
        } while (direction == 3 || direction == 2);
      } else if (noRight == true){
        do {
          direction = (int)( Math.random() * 5);
        } while (direction == 3 || direction == 1);
      } else {
        do {
          direction = (int)( Math.random() * 5);
        } while (direction == 3);
      }
    } else if (noDown == true){
      if (noLeft == true){
        do {
          direction = (int)( Math.random() * 5);
        } while (direction == 4 || direction == 2);
      } else if (noRight == true){
        do {
          direction = (int)( Math.random() * 5);
        } while (direction == 4 || direction == 1);
      } else {
        do {
          direction = (int)( Math.random() * 5);
        } while (direction == 4);
      }
    } else if (noLeft == true){
      do {
        direction = (int)( Math.random() * 5);
      } while (direction == 2);
    }else if (noRight == true){
      do {
        direction = (int)( Math.random() * 5);
      } while (direction == 1);
    }
    
    if (getType().equals("S") && getNumHealth() < 10){

      if (getX() > 0 && getX() < side-1 && getY() > 0 && getY() < side-1){
        if (map[getX()+1][getY()] != null){
          if (map[getX()+1][getY()].getType().equals("P")){
            moveDown();
           }
        } else if (map[getX()+1][getY()] != null){
          if (map[getX()-1][getY()].getType().equals("P")){
            moveUp();
          }
        } else if (map[getX()][getY()+1] != null){
         if (map[getX()][getY()+1].getType().equals("P")){
            moveRight();
         }
        } else if (map[getX()][getY()-1] != null){
         if (map[getX()][getY()-1].getType().equals("P")){
          moveLeft();
         }
       }
      }
      
    } else if (getType().equals("W") && getNumHealth() < 10){
     if (getX() > 0 && getX() < side-1 && getY() > 0 && getY() < side-1){
      if (map[getX()+1][getY()] != null){
          if (map[getX()+1][getY()].getType().equals("S")){
            moveDown();
           }
        } else if (map[getX()+1][getY()] != null){
          if (map[getX()-1][getY()].getType().equals("S")){
            moveUp();
          }
        } else if (map[getX()][getY()+1] != null){
         if (map[getX()][getY()+1].getType().equals("S")){
            moveRight();
         }
        } else if (map[getX()][getY()-1] != null){
         if (map[getX()][getY()-1].getType().equals("S")){
          moveLeft();
         }
       }
     }
    //move animal
    } else if (direction == 1){
      moveRight();
    //  System.out.println("right");
    } else if (direction == 2){
      moveLeft();
    //  System.out.println("left");
    } else if (direction == 3){
      moveUp();
    //  System.out.println("up");
    } else if (direction == 4){
      moveDown();
      //System.out.println("down");
    }  
  } 
}