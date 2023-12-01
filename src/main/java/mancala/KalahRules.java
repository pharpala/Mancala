package mancala;

public class KalahRules extends GameRules{
    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     * 
     */
    
     int currPlayer=1;

    public KalahRules() {
        super();
    }
    
    @Override
    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */

    public int moveStones(int startPit, int playerNum) throws InvalidMoveException { 
        if(startPit >12 || startPit <1) {
            throw new InvalidMoveException("Invalid move: try again");
        }

        currPlayer = playerNum;
        int stonesToMove = getDataStructure().getNumStones(startPit);

        if(stonesToMove == 0){
            throw new InvalidMoveException();
        }

        System.out.println("Contents of dataSet:"); 
        System.out.println(stonesToMove);

        int beforeStore = getDataStructure().getStoreCount(playerNum);
        System.out.println(beforeStore);

        distributeStones(startPit);

        int afterStore = getDataStructure().getStoreCount(playerNum);
        System.out.println(afterStore);

        return afterStore-beforeStore;
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    public int distributeStones(int startPit) {
        Countable chosenCountable;
        int lastIndex;

        getDataStructure().setIterator(startPit, currPlayer, false);

        int stonesToBeMoved = getDataStructure().removeStones(startPit);
        lastIndex = (stonesToBeMoved + startPit) % 13;
        if(lastIndex == 0) {
            lastIndex++;
        }

        for (int i = 0; i < stonesToBeMoved; i++) {
            chosenCountable = getDataStructure().next();
            chosenCountable.addStone();
        }

        if(currPlayer == 1 && lastIndex <7 && getDataStructure().getNumStones(lastIndex) == 1) {
            stonesToBeMoved += captureStones(lastIndex);
        } 
        
        if(currPlayer == 2 && lastIndex <13 && lastIndex >6 && getDataStructure().getNumStones(lastIndex) == 1) {
            stonesToBeMoved += captureStones(lastIndex);
        } 

        return stonesToBeMoved;
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
            int oppositeIndex = 13 - stoppingPoint; //get opposite pit to steal

            int stonesRobbed = getDataStructure().removeStones(oppositeIndex);
            stonesRobbed += getDataStructure().removeStones(stoppingPoint);

            getDataStructure().addToStore(currPlayer, stonesRobbed);

            return stonesRobbed;

        } catch (IndexOutOfBoundsException e) {
        throw new PitNotFoundException();
        }
    }

}