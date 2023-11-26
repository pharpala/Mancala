package mancala;

public class AyoRules extends GameRules {
    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)


    public AyoRules(MancalaDataStructure gameBoard){
        this.gameBoard = gameBoard;
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
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException {
        if (currentPlayer == 2) {
            // Check if the selected pit for playerTwo is valid
            if (gameBoard.getNumStones(startPit) == 0 || startPit < 7 || startPit > 12) {
                throw new InvalidMoveException();
            }
        } else if (currentPlayer == 1) {
            if (gameBoard.getNumStones(startPit) == 0 || startPit < 0 || startPit > 5) {
                throw new InvalidMoveException();
            }
        }
    
        if (startPit < 13 && startPit >= 0) {
            int stonesToDistribute = gameBoard.getNumStones(startPit);
    
            if (stonesToDistribute > 0) {
                distributeStones(startPit);
    
                // Check if the last stone landed in an empty pit or a pit with 1 or 2 stones
                int lastPit = (startPit + stonesToDistribute - 1) % 14;
                int lastStoneCount = gameBoard.getNumStones(lastPit);
    
                if ((lastStoneCount == 1 || lastStoneCount == 2) && gameBoard.getNumStones(lastPit) > 0) {
                    // Player gets another turn
                    System.out.println("Player gets another turn!");
                    return gameBoard.getStoreCount(playerNum);
                }
            } else {
                System.out.println("No stones inside selected pit!");
            }
        } else {
            System.out.println("Invalid pit.");
        }
    
        // Switch to the next player
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    
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
        int endindex = startPit+toDistributeStones;
        
        if(startPit < 0 || startPit > 12 || startPit == 6 || startPit == 13){
            throw new PitNotFoundException();
        }
        
        while(toDistributeStones > 0){
            gameBoard.setIterator(startPit, currentPlayer, true);
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
            
                if(gameBoard.getNumStones(endindex)<4){
                   stonesStolen= captureStones(endindex);
                   gameBoard.addStones(6,stonesStolen);
                }
            
            
            
                if(gameBoard.getNumStones(endindex)<4){
                   stonesStolen= captureStones(endindex);
                   gameBoard.addStones(6,stonesStolen);
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
        // int indexStarting = stoppingPoint - 1; // Calculate the starting index of the pit to capture myStones
        // int pitOpposite;
         // Check if the stopping point is outside the valid range (0 to 12)
        if(stoppingPoint < 0 || stoppingPoint > 12){
            throw new PitNotFoundException();
        }
        if(currentPlayer == 2){ 
        if( stoppingPoint > 13){
           stoppingPoint = 0;
        }
         for(int i=stoppingPoint; i>=7 || i<=12;i--){
            if(gameBoard.getNumStones(stoppingPoint)<4){
                capturedStones =+ gameBoard.removeStones(i);
            }
            else{
                return capturedStones;
            }
        }}
        else if(currentPlayer ==1){
               
                 if( stoppingPoint > 13){
                stoppingPoint = 0;
            }
            for(int i=stoppingPoint; i>=0 || i<=5;i--){
            if(gameBoard.getNumStones(stoppingPoint)<4){
                capturedStones =+ gameBoard.removeStones(i);
            }
            else{
                return capturedStones;
            }
        }}
        
        return capturedStones;
    }

    public int getNumberStones(int startPit) {
        return 1;
    }
}