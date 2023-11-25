package mancala;

public class KalahRules extends GameRules{
    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2) 
    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     * 
     */

     public KalahRules(MancalaDataStructure gameBoard){
        this.gameBoard = gameBoard;
    }
    
    @Override
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException{
        
        if(currentPlayer == 2){
             // Check if the selected pit for playerTwo is valid
            if (gameBoard.getNumStones(startPit) == 0 || startPit < 7 || startPit > 12) {
                throw new InvalidMoveException();
            }
        } else if (currentPlayer == 1){
            if (gameBoard.getNumStones(startPit)==0 || startPit < 0 || startPit > 5) {
                throw new InvalidMoveException();
            }
        
        }                
        if (startPit < 13 && startPit >= 0) {
            if (gameBoard.getNumStones(startPit)>0) {  // Check if there are stones in the selected pit
                distributeStones(startPit);              
            } else {
                System.out.println("No stones inside selected pit!");
            }
        } else {
            System.out.println("Invalid pit."); 
        }
       return gameBoard.getStoreCount(playerNum);
        

    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    public int distributeStones(int startPit) throws PitNotFoundException{

        // Check if the starting point is outside the valid range (0 to 12)
        int myAddedStones = 0;
        int stonesStolen = 0;
        int toDistributeStones = gameBoard.removeStones(startPit - 1);
        int counterIndex = startPit - 1;
        
        if(startPit < 0 || startPit > 12 || startPit == 6 || startPit == 12){
            throw new PitNotFoundException();
        }
        
        while(toDistributeStones > 0){
            
            if(currentPlayer==1 && counterIndex == 12 && toDistributeStones > 0){
                counterIndex = 13;    
            } else if(currentPlayer==2 && counterIndex == 5 && toDistributeStones > 0){
                counterIndex = 6;    
            }
            counterIndex++;
            if(counterIndex > 13){
                counterIndex = 0;
            }
            gameBoard.addStones(counterIndex , 1);
            toDistributeStones--;
            myAddedStones++;
            if(toDistributeStones == 1 && gameBoard.getNumStones(counterIndex + 1) == 0 && currentPlayer == 1){
                stonesStolen = captureStones(counterIndex + 1);
                gameBoard.addStones(6,stonesStolen);
            } else if (toDistributeStones == 1 && gameBoard.getNumStones(counterIndex) == 0 && currentPlayer == 2){
                stonesStolen = captureStones(counterIndex + 1);
                gameBoard.addStones(12,stonesStolen);
            }
            stonesStolen = 0;
        }
                 
        return myAddedStones;
    }

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    public int captureStones(int stoppingPoint) throws PitNotFoundException{

        // Initialize the number of myStones captured to zero
        int capturedStones = 0;
        int indexStarting = stoppingPoint - 1; // Calculate the starting index of the pit to capture myStones
        int pitOpposite;
         // Check if the stopping point is outside the valid range (0 to 12)
        if(stoppingPoint < 0 || stoppingPoint > 12){
            throw new PitNotFoundException();
        }
       
 // Check if the starting index is within the valid range (0 to 11) and if the pit has one stone
        if (gameBoard.getNumStones(indexStarting) == 1 && indexStarting >= 0 && indexStarting < 12) {
            pitOpposite = 11 - indexStarting; // Calculate the index of the opposite pit
            // Check if the opposite pit has myStones to capture
            if(gameBoard.getNumStones(pitOpposite)!=0) {
                 // Capture myStones from both the starting pit and the opposite pit
                capturedStones = gameBoard.removeStones(pitOpposite);
                capturedStones += gameBoard.removeStones(indexStarting);
            }
        }
        return capturedStones;
    }    
}