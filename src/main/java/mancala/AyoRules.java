package mancala;

public class AyoRules extends GameRules {
    
    private int currPlayer;

    public AyoRules(){
        super();
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    @Override
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException{
        if(startPit < 1 || startPit > 12){
            throw new InvalidMoveException();
        }
        
        currPlayer = playerNum;
        int stonesToMove = getDataStructure().getNumStones(startPit);

        if(stonesToMove == 0){
            throw new InvalidMoveException();
        }

        int playerStoreBefore = getDataStructure().getStoreCount(playerNum);
        
        distributeStones(startPit);
       
        int playerStoreAfter = getDataStructure().getStoreCount(playerNum);

        return playerStoreAfter - playerStoreBefore;
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    public int distributeStones(int startPit){

       int numDistributed = 0;
       Countable pit;
       int stonesToMove = getDataStructure().getNumStones(startPit);
       int finalIndex = (startPit + stonesToMove) % 13; 
       if(finalIndex == 0) {
        finalIndex++;
       }
       stonesToMove = getDataStructure().removeStones(startPit);

       getDataStructure().setIterator(startPit,currPlayer,true);

       while(stonesToMove > 0){
            pit = getDataStructure().next();
            pit.addStone();
            stonesToMove--;
            numDistributed++;

            if(stonesToMove - 1 == 0 && getDataStructure().getNumStones(finalIndex) != 0){
                stonesToMove = getDataStructure().removeStones(finalIndex);
            } 
      }
      if(getDataStructure().getNumStones(finalIndex) == 1 && currPlayer == 1 && finalIndex<7) {
        stonesToMove += captureStones(finalIndex);
      }

      if(getDataStructure().getNumStones(finalIndex) == 1 && currPlayer == 2 && finalIndex < 13 && finalIndex > 6) {
        stonesToMove += captureStones(finalIndex);
      }

      
      return numDistributed;
   }


    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    public int captureStones(int stoppingPoint){

        int capturedStones = 0; 
        int pitOpposite = 13 - stoppingPoint;

        capturedStones = getDataStructure().removeStones(pitOpposite);
        
        getDataStructure().addToStore(currPlayer, capturedStones);
        
        return capturedStones;
    }
}