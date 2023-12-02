package mancala;

public class AyoRules extends GameRules {
    
    private int currPlayer=1;

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
    public int moveStones(final int startPit,final int playerNum) throws InvalidMoveException{
        if(startPit < 1 || startPit > 12){
            throw new InvalidMoveException();
        }
        
        currPlayer = playerNum;
        final int stonesToMove = getDataStructure().getNumStones(startPit);

        if(stonesToMove == 0){
            throw new InvalidMoveException();
        }

        final int playerStoreBefore = getDataStructure().getStoreCount(playerNum);
        
        distributeStones(startPit);
       
        final int playerStoreAfter = getDataStructure().getStoreCount(playerNum);

        return playerStoreAfter - playerStoreBefore;
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    public int distributeStones(final int startPit){

       Countable pit;
       int stonesToMove = getDataStructure().removeStones(startPit);
       int finalIndex = (startPit + stonesToMove)%13;
       if(finalIndex > 12) {
        finalIndex = finalIndex % 12;
       }

       int temp;
       int numDistributed = stonesToMove;

       getDataStructure().setIterator(startPit,currPlayer,true);

       while(stonesToMove > 0) {
            pit = getDataStructure().next();
            pit.addStone();
            stonesToMove--;

            if(stonesToMove == 1 && getDataStructure().getNumStones(finalIndex) != 0) {
                stonesToMove += getDataStructure().removeStones(finalIndex);
            } 
      }
      
        if(getDataStructure().getNumStones(finalIndex) == 1 && currPlayer == 1 && finalIndex<7) {
            numDistributed += captureStones(finalIndex);
        }

        if(getDataStructure().getNumStones(finalIndex) == 1 && currPlayer == 2 && finalIndex < 13 && finalIndex > 6) {
            numDistributed += captureStones(finalIndex);
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
    public int captureStones(final int stoppingPoint){

        int capturedStones = 0; 
        final int pitOpposite = 13 - stoppingPoint;

        capturedStones = getDataStructure().removeStones(pitOpposite);
        
        getDataStructure().addToStore(currPlayer, capturedStones);
        
        return capturedStones;
    }
}