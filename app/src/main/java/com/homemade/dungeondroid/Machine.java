package com.homemade.dungeondroid;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.homemade.dungeondroid.Battle.Move;
import com.homemade.dungeondroid.Battle.Result;

import static com.homemade.dungeondroid.Battle.Move.PAPER;
import static com.homemade.dungeondroid.Battle.Move.ROCK;
import static com.homemade.dungeondroid.Battle.Move.SCISSORS;
import static com.homemade.dungeondroid.Battle.Result.DRAW;
import static com.homemade.dungeondroid.Battle.Result.LOST;
import static com.homemade.dungeondroid.Battle.Result.WIN;

/**
 * Created by joaosousa on 12/12/16.
 */
public class Machine {

    public Move antecipateByPairOfMoves(List<Move> myMoves, List<Move> machineMoves){

        int patternSize = 3;
        Move[] myLastMoves = new Move[patternSize + 1];
        Move[] machineLastMoves = new Move[patternSize + 1];
        Move playerNextMove = null;
        Move machineNextMove = null;

        while (machineNextMove == null) {

            //Get last (n = factor) moves
            for (int i = myMoves.size() - patternSize - 1; i < myMoves.size(); i++){
                myLastMoves[i - (myMoves.size() - patternSize - 1)] = myMoves.get(i);
                machineLastMoves[i - (myMoves.size() - patternSize - 1)] = machineMoves.get(i);
            }

            //Numero de vezes que a proxima jogada e rock, paper ou scissors
            int playerDidRock = 0;
            int playerDidPaper = 0;
            int playerDidScissors = 0;

            for (int i = 0; i < myMoves.size() - (patternSize + 1); i++) {
                Move[] myCandidate = new Move[patternSize + 1];
                Move[] machineCandidate = new Move[patternSize + 1];

                for (int j = i; j < i + (patternSize + 1); j++){
                    myCandidate[j - i] = myMoves.get(j);
                    machineCandidate[j - i] = machineMoves.get(j);
                }

                if (Arrays.equals(myCandidate, myLastMoves) && Arrays.equals(machineCandidate, machineLastMoves)){
                    playerNextMove = myMoves.get(i+1);

                    if (playerNextMove == ROCK){
                        playerDidRock += 1;
                    } else if (playerNextMove == PAPER){
                        playerDidPaper += 1;
                    } else if (playerNextMove == SCISSORS){
                        playerDidScissors += 1;
                    }

                }
            }

            if (foundPattern(playerDidRock, playerDidPaper, playerDidScissors)) {

                int maxRepeatedMove = Math.max(Math.max(playerDidRock, playerDidPaper), playerDidScissors);

                if (playerDidMoreTimes(playerDidRock, maxRepeatedMove)) {
                    machineNextMove = PAPER;
                } else if (playerDidMoreTimes(playerDidPaper, maxRepeatedMove)) {
                    machineNextMove = SCISSORS;
                } else if (playerDidMoreTimes(playerDidScissors, maxRepeatedMove)) {
                    machineNextMove = ROCK;
                }

            } else {
                patternSize -= 1;

                if (patternSize == -1){
                    machineNextMove = antecipateByPlayerMoves(myMoves, machineMoves);
                }
            }
        }
        return machineNextMove;
    }

    private boolean foundPattern(int playerDidRock, int playerDidPaper, int playerDidScissors) {
        return playerDidRock != 0 || playerDidPaper != 0 || playerDidScissors != 0;
    }

    private boolean playerDidMoreTimes(int playerDid, int maxRepeatedMove) {
        return playerDid == maxRepeatedMove;
    }

    public Move antecipateByPlayerMoves(List<Move> myMoves, List<Move> machineMoves){
        Move[] myReference;
        Move playerNextMove;
        Move machineNextMove = null;
        int patternSize = 3;

        while (machineNextMove == null){

            myReference = getPlayerLastMoves(patternSize, myMoves);

            int playerDidRock = 0;
            int playerDidPaper = 0;
            int playerDidScissors = 0;

            for (int i = 0; i < myMoves.size() - (patternSize + 1); i++){
                Move[] candidate = new Move[patternSize + 1];

                for (int j = i; j < i + (patternSize + 1); j++){
                    candidate[j - i] = myMoves.get(j);
                }

                if (Arrays.equals(candidate, myReference)){
                    playerNextMove = myMoves.get(i+1);

                    if (playerNextMove == ROCK){
                        playerDidRock += 1;
                    } else if (playerNextMove == PAPER){
                        playerDidPaper += 1;
                    } else if (playerNextMove == SCISSORS){
                        playerDidScissors += 1;
                    }

                }
            }

            if (foundPattern(playerDidRock, playerDidPaper, playerDidScissors)) {
                int maxRepeatedMove = Math.max(Math.max(playerDidRock, playerDidPaper), playerDidScissors);

                if (playerDidMoreTimes(playerDidRock, maxRepeatedMove)) {
                    machineNextMove = PAPER;
                } else if (playerDidMoreTimes(playerDidPaper, maxRepeatedMove)) {
                    machineNextMove = SCISSORS;
                } else if (playerDidMoreTimes(playerDidScissors, maxRepeatedMove)) {
                    machineNextMove = ROCK;
                }
            } else {
                patternSize -= 1;

                if (patternSize == -1){
                    machineNextMove = randomMove();
                }
            }
        }
        return machineNextMove;
    }

    private Move[] getPlayerLastMoves(int factor, List<Move> myMoves) {
        Move[] myReference = new Move[factor + 1];

        for (int i = myMoves.size() - factor - 1 ;i < myMoves.size() ; i++){
            myReference[i - (myMoves.size() - factor - 1)] = myMoves.get(i);
        }

        return myReference;
    }

    public Move randomMove() {
        Random r = new Random();
        return Move.values()[r.nextInt(3) + 1];
    }

    public Result getResult(Move player, Move machine) {
        Result result;
        if ((player == ROCK && machine == SCISSORS)
                || (player == PAPER && machine == ROCK)
                || (player == SCISSORS && machine == PAPER)) {
            result = WIN;
        } else if ((player == ROCK && machine == PAPER)
                || (player == PAPER && machine == SCISSORS)
                || (player == SCISSORS && machine == ROCK)) {
            result = LOST;
        } else {
            result = DRAW;
        }
        return result;
    }

}
