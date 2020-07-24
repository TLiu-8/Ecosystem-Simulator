/**
 * EcosystemSimulator.java
 * Version: 1.0
 * Author: Theo Liu
 * Date: 12/09/2018
 * Description: The simulator itself, animals move, reproduce, plants grow, over time in a simulation.
 */

class EcoSystemSimulator {
  
  private int wolfNum = 0;
  private int wolfHealth = 0;
  private int sheepNum = 0;
  private int sheepHealth = 0;
  private int plantHealth = 0;
  private int plantNum = 0;
  private double plantGrowth = 0;
  private int side;
  private Organism[][] map;
  private Organism[] store;
  
  EcoSystemSimulatorOriginal2(int wolfNum, int wolfHealth, int sheepNum, int sheepHealth, int plantHealth, int plantNum, double plantGrowth, int side){
    this.wolfNum = wolfNum;
    this.wolfHealth = wolfHealth;
    this.sheepNum = sheepNum;
    this.sheepHealth = sheepHealth;
    this.plantHealth = plantHealth;
    this.plantNum = plantNum;
    this.plantGrowth = plantGrowth;
    this.side = side;
  }
  
  public void simulate(){
    

    // build a two D grid map to display ecosystem
    map = new Organism[side][side];
    // build a one D array to hold Organism objects
    store = new Organism[side*side];
    
    int x, y,counter = 0;

    // put sheeps on grid
    for(int i = 0; i < sheepNum; i++){
      do {
        x = (int)( Math.random() * side);
        y = (int)( Math.random() * side);
      } while (map[x][y] != null);
      Sheep objSheep = new Sheep(sheepHealth,x,y);
      store[counter++] = objSheep;
      objSheep.setAge(20);
      map[x][y] = objSheep;
    }
    
    // put wolves on grid
    for (int i = 0; i < wolfNum; i++){
      do {
        x = (int)( Math.random() * side);
        y = (int)( Math.random() * side);
      } while (map[x][y] != null);
      
      // draw image wolf on current cell
      Wolf objWolf = new Wolf(wolfHealth,x,y);
      store[counter++] = objWolf;
      objWolf.setAge(20);
      map[x][y] = objWolf;
    }
    
    // put plants on grid
    for(int i = 0; i < plantNum; i++){
      do {
        y = (int)( Math.random() * side);
        x = (int)( Math.random() * side);
      } while (map[x][y] != null);
      
      Plant objPlant = new Plant(plantHealth,x,y);
      store[counter++] = objPlant;
      
      map[x][y] = objPlant;

    }
    
    DisplayGrid grid = new DisplayGrid(map);
    grid.refresh();
    
    try{ Thread.sleep(800); }catch(Exception e) {};
 
    int numRun = 1; // count turns of habitation 
    System.out.println("Day: " + (numRun++) );
      System.out.println("Number of Plants: "+ plantNum);
      System.out.println("Number of Sheep: "+ sheepNum);
      System.out.println("Number of Wolves: "+ wolfNum);
      System.out.println();
    
    do {
      
      // store object's old location
      for(int i = 0; i < counter; i++){
        if (store[i] != null){
             Organism object = store[i];
             object.setX(object.getCol());
             object.setY(object.getRow());
        }
      }
      
      // Get new location for every animals
      for(int i = 0; i < counter; i++){
        if (store[i] != null){
          // skip plant
          if(!store[i].getType().equals("P")){
             Organism objAnimal = store[i];
            ((Animal)objAnimal).move(side,map);
          }
        }
      }

      // make sure each location has less than two object(s)
      boolean multiple = false;
      do {
      multiple = false;
      int[][] checkMultiple = new int[side][side];
      for (int i = 0; i < side; i++){
        for (int j = 0; j < side; j++){
          checkMultiple[i][j] = 0;
        }
      }
      for (int i = 0; i < counter; i++){
        checkMultiple[store[i].getRow()][store[i].getCol()]++;
      }
      for (int i = 0; i < side; i++){
        for (int j = 0; j < side; j++){
          if (checkMultiple[i][j] > 2){
            multiple = true;
              for (int l = 0; l < counter; l++){               
                if (store[l].getRow() == i && store[l].getCol() == j){
                  store[l].setCol(store[l].getX());
                  store[l].setRow(store[l].getY());
                  checkMultiple[i][j]--;
                }
              } 
          }else{multiple = false;}
        }
      }
      } while (multiple == true);
      
      // variables to store new Organism when created.
      Organism[] newOrg = new Organism[side*side];
      int newCount = 0;
      
      // check each organism's location to action
      for(int i = 0; i < counter; i++){
        
        if(store[i] != null){
            
          Organism objOrganism = store[i];
          int currentY = objOrganism.getRow();
          int currentX = objOrganism.getCol();
        
          for( int j = i + 1; j < counter; j++) {
            if(store[j] != null){
            
            Organism objOrganismNext = store[j];
            int nextY = objOrganismNext.getRow();
            int nextX = objOrganismNext.getCol();
            
          if( currentY == nextY && currentX == nextX ){
            
            String currentType = objOrganism.getType(); // current organism
            String nextType = objOrganismNext.getType(); // next organism
            
            // a wolf stomps and kills a plant
            if (currentType.equals("W") && nextType.equals("P")){
              plantNum--;
              store[j] = null; // remove the plant from store
              
            // a sheep moves onto a plant
            } else if (currentType.equals("S") && nextType.equals("P")){
              // sheep eat plant and get plant's health number
              objOrganism.setNumHealth(objOrganism.getNumHealth() + objOrganismNext.getNumHealth());
              plantNum--;
              store[j] = null; // remove the plant from store
              objOrganism.setLastEaten();
              
            // two same type animals meet
            } else if (currentType.equals(nextType) && !currentType.equals("P")){
              // two animals with different gender meet
              if(currentType.equals(nextType) && ((Animal)objOrganism).getGender() != ((Animal)objOrganismNext).getGender()){
                // new baby happen if old and strong enough
                if(((Animal)objOrganism).checkAge() && ((Animal)objOrganismNext).checkAge() &&
                   objOrganism.getNumHealth() > 20 && objOrganismNext.getNumHealth() > 20 && ((Animal)objOrganism).checkPregnancy() && ((Animal)objOrganismNext).checkPregnancy()
                  && objOrganism.checkEaten() && objOrganismNext.checkEaten()){
                  
                  // parents stay where they are before.
                  objOrganism.setCol(objOrganism.getX());
                  objOrganism.setRow(objOrganism.getY());
                  objOrganismNext.setCol(objOrganismNext.getX());
                  objOrganismNext.setRow(objOrganismNext.getY());
                  
                  boolean check = false;
                  int checkingX = 0, checkingY = 0;
                  // look around neighbour to get nearby empty space to put new baby
                  for (int l = currentY - 1; l < currentY + 2; l++){
                    for (int h = currentX -1; h < currentX + 2; h++){
                      if (h > 0 && h < side && l > 0 && l < side && map[l][h] == null){
                          check = true;
                          checkingX = h;
                          checkingY = l;
                      }
                    }
                  }
             
                    if(currentType.equals("S")){
                      sheepNum++;
                      Sheep objSheep = new Sheep(sheepHealth,checkingX,checkingY);
                      newOrg[newCount++] = (Organism)objSheep; //add to newOrg
                    }else if(currentType.equals("W")){
                      wolfNum++;
                      Wolf objWolf = new Wolf(wolfHealth,checkingX,checkingY);
                      newOrg[newCount++] = (Organism)objWolf; //add to newOrg
                    }
                    
                    objOrganism.setNumHealth(objOrganism.getNumHealth() - 10);
                    objOrganismNext.setNumHealth(objOrganismNext.getNumHealth() - 10);
                  
              } else{
                  objOrganism.setCol(objOrganism.getX());
                  objOrganism.setRow(objOrganism.getY());
                  objOrganismNext.setCol(objOrganismNext.getX());
                  objOrganismNext.setRow(objOrganismNext.getY());
                }
            
              } else if(currentType.equals("S")){
                  // do nothing and they stay where they are before.
                  objOrganism.setCol(objOrganism.getX());
                  objOrganism.setRow(objOrganism.getY());
                  objOrganismNext.setCol(objOrganismNext.getX());
                  objOrganismNext.setRow(objOrganismNext.getY());
              } else if(currentType.equals("W")) {
                // two male meet, they fight to get the spot
                if(((Animal)objOrganism).getGender() == ((Animal)objOrganismNext).getGender() && ((Animal)objOrganism).getGender() == 1){
                  
                  if( ( (Wolf)objOrganism ).compareTo( (Wolf)objOrganismNext ) == 0){
                    if (((Animal)objOrganism).getSize() > ((Animal)objOrganismNext).getSize()){
                      objOrganism.setNumHealth(objOrganism.getNumHealth() - 5);               
                      objOrganismNext.setNumHealth(objOrganismNext.getNumHealth() - 15);
                    } else if (((Animal)objOrganism).getSize() < ((Animal)objOrganismNext).getSize()){
                      objOrganism.setNumHealth(objOrganism.getNumHealth() - 15);               
                      objOrganismNext.setNumHealth(objOrganismNext.getNumHealth() - 5);
                    } else {
                      objOrganism.setNumHealth(objOrganism.getNumHealth() - 10);               
                      objOrganismNext.setNumHealth(objOrganismNext.getNumHealth() - 10);
                    }
                      // stay where they are before.
                      objOrganism.setCol(objOrganism.getX());
                      objOrganism.setRow(objOrganism.getY());
                      objOrganismNext.setCol(objOrganismNext.getX());
                      objOrganismNext.setRow(objOrganismNext.getY());
                    
                  }else if(((Wolf)objOrganism).compareTo((Wolf)objOrganismNext) == 1){
                    // System.out.println("ok2-strong/weak");
                    objOrganism.setNumHealth(objOrganism.getNumHealth() - 5);
                    objOrganismNext.setNumHealth(objOrganismNext.getNumHealth() - 15);
                    // loser back to old position.
                    objOrganismNext.setCol(objOrganismNext.getX());
                    objOrganismNext.setRow(objOrganismNext.getY());
                  } else {
                    // System.out.println("ok3-weak/strong");
                    objOrganism.setNumHealth(objOrganism.getNumHealth() - 15);
                    objOrganismNext.setNumHealth(objOrganismNext.getNumHealth() - 5);
                    // loser back to old position.
                    objOrganism.setCol(objOrganism.getX());
                    objOrganism.setRow(objOrganism.getY());
                  }
                } else {
                  objOrganism.setCol(objOrganism.getX());
                  objOrganism.setRow(objOrganism.getY());
                  objOrganismNext.setCol(objOrganismNext.getX());
                  objOrganismNext.setRow(objOrganismNext.getY());
                }
              }
            //a wolf moves onto a sheep
            } else if (currentType.equals("W") && nextType.equals("S")){
              // wolf eat sheep and get sheep's health number
              objOrganism.setNumHealth(objOrganism.getNumHealth() + objOrganismNext.getNumHealth());
              sheepNum--;
              store[j] = null;
              objOrganism.setLastEaten();
            }
            // a sheep moves onto a wolf
            else if(currentType.equals("S") && nextType.equals("W")){
              objOrganismNext.setNumHealth(objOrganismNext.getNumHealth() + objOrganism.getNumHealth());
              sheepNum--;
              store[i] = null;
              objOrganismNext.setLastEaten();
            }
          }
        }
      }   
      }
     }
        
      // each turn animal lose 1 health and grow 1 age
      for(int i = 0; i < counter; i++){
        if(store[i] != null){
          Organism objOrganism = store[i];
          if(!objOrganism.getType().equals("P")){
            objOrganism.setNumHealth(objOrganism.getNumHealth() - 1);
          }
          // dead when it too weak
          if (objOrganism.getNumHealth() < 1){
            store[i] = null;
            if (objOrganism.getType().equals("S")){
              sheepNum--;
            } else if (objOrganism.getType().equals("W")){
              wolfNum--;
            }
          }
          
          objOrganism.setAge(objOrganism.getAge() + 1);
          // dead when it too old
          if (objOrganism.checkOldAge()){
            store[i] = null;
            if (objOrganism.getType().equals("S")){
              sheepNum--;
            } else if (objOrganism.getType().equals("W")){
              wolfNum--;
            } else {
              plantNum --;
            }
          }
        }
      }
      
        // now update store to remove null object and append new objects
        Organism[] temp = new Organism[side*side];
        int tempCount = 0;
        // copy exist object to temp
        for(int i = 0; i < counter; i++){
          if(store[i] != null){
             temp[tempCount++] = store[i];
          }
        }
        // add new object to temp
        for(int i = 0; i < newCount; i++){
          if (tempCount < (side*side)-1){
            temp[tempCount++] = newOrg[i];
          }
        }
        // update counter and store
        if (tempCount >= side*side){
          counter = (side*side)-1;
        } else {
          counter = tempCount;
        }
        for (int i = 0; i < store.length; i++){
          if(i < tempCount){
            store[i] = temp[i];
          } else {
            store[i] = null;
          }
        }
        
        // now update map to display relocated objects
        for(int i = 0; i < side; i++){
          for(int j = 0; j < side; j++){
              map[i][j] = null;
          }
        }
        for (int i = 0; i < store.length; i++){
          if(store[i] != null){
            map[store[i].getCol()][store[i].getRow()] = store[i];
          }
        }
        
        // now growth new plants
        if (plantGrowth != 0) {
        double plantsGrownPerDay = plantGrowth;
        int days = 1;
        if (plantGrowth < 1){
          days = (int)(1.0/plantGrowth);
          plantsGrownPerDay = 1;
        }
        
        int numOfEmpty = 0;
        for (int k = 0; k < side; k++){
          for (int l = 0; l< side; l++){
            if (map[k][l] == null){
              numOfEmpty++;
            }
          }
        }
        
        if (numRun % days == 0){
            for (int i = 0; i < plantsGrownPerDay; i++){
              if (numOfEmpty != 0){
                numOfEmpty--;
                
              int randX, randY;
              do {
                randX = (int)( Math.random() * side);
                randY = (int)( Math.random() * side);
              } while (map[randX][randY] != null);
              
                Organism objPlant = new Plant(plantHealth,randX,randY);
                map[randX][randY] = objPlant;
                plantNum++;
                if(counter < (side*side)-1)
                  store[counter++] = objPlant;
            }
          }
        }
      }
        
        plantNum = 0;
        sheepNum = 0;
        wolfNum = 0;
        for (int i = 0; i < side; i++){
          for (int j = 0; j < side; j++){
            if (map[i][j] != null){
              if (map[i][j].getType().equals("P")){
                plantNum++;
              } else if (map[i][j].getType().equals("S")){
                sheepNum++;
              } else if (map[i][j].getType().equals("W")){
                wolfNum++;
              }
            }
          }
        }
        
       
        
      System.out.println("Day" + (numRun++) );
      System.out.println("Number of Plants: "+ plantNum);
      System.out.println("Number of Sheep: "+ sheepNum);
      System.out.println("Number of Wolves: "+ wolfNum);
      System.out.println();
      
      grid.refresh();
      try{ Thread.sleep(200); }catch(Exception e) {};
      
    } while( sheepNum > 0);
  }
}