package mancala;
import java.util.ArrayList;

public class KalahRules extends GameRules{

    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    private ArrayList<Countable> dataSet; 
    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     * 
     */
     public KalahRules(){
        super();
    }

     public KalahRules(MancalaDataStructure gameBoard){
        super();
        this.gameBoard = gameBoard;
        dataSet = new ArrayList<>(gameBoard.getData());
    }*/
    
    @Override
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException{ //moves stones for the player starting at a specific pit
        
        if(startPit >12 || startPit <1) {
            throw new InvalidMoveException("Invalid move: try again");
        }
    
        Countable chosenPit = dataSet.get(startPit-1); //DATASET IS THE NEW PITS AND STORES
        int stonesToMove = chosenPit.removeStones();
        gameBoard.updateData(dataSet);

        int stonesToMove2 = gameBoard.getNumStones(startPit);


        System.out.println("Contents of dataSet:");
        for (Countable pit : dataSet) {
            System.out.println("Pit: " + pit.getStoneCount()); // Assuming Countable has a meaningful toString method
        }


        System.out.println(stonesToMove);
        System.out.println(stonesToMove2);
        int capturedStones= 0;
        //STORE 1 IS AT 7
        // STORE 2 IS AT 14

        int currentPitIndex = startPit;
        while(stonesToMove > 0) {
            currentPitIndex++;
            if(currentPitIndex > 14) {
                currentPitIndex =1;
            }
            
            if(currentPitIndex == 7 && playerNum == 2) {
                currentPitIndex = 8; //skip store if player 2 is playing

            } else if (currentPitIndex == 14 && playerNum == 1) {
                currentPitIndex = 1; //skip store if player 1 is playing
            } else {
                dataSet.get(currentPitIndex-1).addStone();
                stonesToMove--;
            }
        }

        int lastPit = currentPitIndex;
        if(playerNum == 1 && lastPit <7 && (dataSet.get(lastPit-1).getStoneCount()== 1)) {
            try{
                capturedStones += captureStones(lastPit);
                return capturedStones;
            } catch (IndexOutOfBoundsException e) {
            throw new PitNotFoundException();
            }
        }

        if(playerNum == 2 && lastPit <14 && lastPit >7 && (dataSet.get(lastPit-1).getStoneCount()== 1)) {
            try {
                capturedStones += captureStones(lastPit);
                return capturedStones;
            } catch (IndexOutOfBoundsException e) {
            throw new PitNotFoundException();
            }
        }
        return capturedStones;
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

        try {
            int capturedStones=0;
            //System.out.println("********"+stoppingPoint+"*********");
            int oppositeIndex= 13-stoppingPoint;
            if(stoppingPoint < 7) {
                capturedStones += dataSet.get(stoppingPoint-1).removeStones() + dataSet.get(oppositeIndex).removeStones();
                dataSet.get(6).addStones(capturedStones);
                System.out.println("This is what Capture stones returns: "+capturedStones);
                return capturedStones;
            } else {
                capturedStones += dataSet.get(stoppingPoint-1).removeStones() + dataSet.get(oppositeIndex).removeStones();
                dataSet.get(13).addStones(capturedStones);
                return capturedStones;
            }
        } catch (IndexOutOfBoundsException e) {
        throw new PitNotFoundException();
        }
    }

    public int getNumberStones(int startPit) {
        Countable chosenPit = dataSet.get(startPit-1); //DATASET IS THE NEW PITS AND STORES
        int stonesToMove = chosenPit.getStoneCount();
        return stonesToMove;
    }
}