package com.homemade.dungeondroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by joaosousa on 12/12/16.
 */
public class Machine {

    private static final String TAG = "MyActivity";

    public int AiMove;

    public int JogaRandom() {
        Random r = new Random();
        return r.nextInt(3) + 1;
    }

    public String Resultado(int Meu, int AiMove) {
        String resultado = "";
        if ((Meu == 1 && AiMove == 3) || (Meu == 2 && AiMove == 1) || (Meu == 3 && AiMove == 2)) {
            resultado = "Ganhou";
        } else if ((Meu == 1 && AiMove == 2) || (Meu == 2 && AiMove == 3) || (Meu == 3 && AiMove == 1)) {
            resultado = "Perdeu";
        } else {
            resultado = "Empatou";
        }
        return resultado;
    }

    public int PreveJogada1(ArrayList<Integer> jogadas_minhas, ArrayList<Integer> jogadas_maquina){

        int AiMove = -1;
        int fator = 3;
        int[] referencia_meu = new int[fator + 1];
        int[] referencia_maquina = new int[fator + 1];

        while (AiMove == -1){

            //Buscar referencias
//            Log.e("TAG","Referencias:");
            for (int i = jogadas_minhas.size() - fator - 1 ;i < jogadas_minhas.size() ; i++){
                referencia_meu[i - (jogadas_minhas.size() - fator - 1)] = jogadas_minhas.get(i);
                referencia_maquina[i - (jogadas_minhas.size() - fator - 1)] = jogadas_maquina.get(i);
//                Log.e("TAG","Ref_meu: "+referencia_meu[i - (jogadas_minhas.size() - fator - 1)]+" Ref_maq: "+referencia_maquina[i - (jogadas_minhas.size() - fator - 1)]);
            }

            ArrayList<Integer> proxima_jogada = new ArrayList<Integer>();
            //Numero de vezes que a proxima jogada e 1,2 ou 3
            int NumberOfOnes = 0;
            int NumberOfTwos = 0;
            int NumberOfThrees = 0;

            for (int i = 0; i < jogadas_minhas.size() - (fator + 1); i++){
                int[] candidato1 = new int[fator + 1];
                int[] candidato2 = new int[fator + 1];

//                Log.e("TAG","Candidatos:");
                for (int j = i; j < i + (fator + 1); j++){
                    candidato1[j - i] = jogadas_minhas.get(j);
                    candidato2[j - i] = jogadas_maquina.get(j);
//                    Log.e("TAG","Cand1: "+candidato1[j - i]+" Cand2 "+candidato2[j - i]);
                }

                if (compareArrays(candidato1,referencia_meu) && compareArrays(candidato2,referencia_maquina)){
                    if(jogadas_minhas.get(i+1) == 1){
                        NumberOfOnes += 1;
                    } else if (jogadas_minhas.get(i+1) == 2){
                        NumberOfTwos += 1;
                    } else if (jogadas_minhas.get(i+1) == 3){
                        NumberOfThrees += 1;
                    }

//                    Log.v("TAG","Entrei aqui mesmo");
                }
            }

            //Se for encontrado algum padrao escolher o que ocorreu mais vezes
            if (NumberOfOnes != 0 || NumberOfTwos != 0 || NumberOfThrees != 0) {

                int max =  Math.max(Math.max(NumberOfOnes,NumberOfTwos),NumberOfThrees);

                //Neste caso preve-se que o jogador jogue 1 logo responde-se com 2
                if (NumberOfOnes == Math.max(Math.max(NumberOfOnes,NumberOfTwos),NumberOfThrees)) {
                    AiMove = 2;
                } else if (NumberOfTwos == Math.max(Math.max(NumberOfOnes,NumberOfTwos),NumberOfThrees)) {
                    AiMove = 3;
                } else if (NumberOfThrees == Math.max(Math.max(NumberOfOnes,NumberOfTwos),NumberOfThrees)) {
                    AiMove = 1;
                }
            } else{
                fator -= 1;

                if (fator == -1){
                    AiMove = PreveJogada2(jogadas_minhas,jogadas_maquina);
                }
            }
        }
        return AiMove;
    }

    public int PreveJogada2(ArrayList<Integer> jogadas_minhas, ArrayList<Integer> jogadas_maquina){
        int AiMove = -1;
        int fator = 3;
        int[] referencia_meu = new int[fator + 1];

        while (AiMove == -1){

            //Buscar referencias
//            Log.e("TAG","Referencias:");
            for (int i = jogadas_minhas.size() - fator - 1 ;i < jogadas_minhas.size() ; i++){
                referencia_meu[i - (jogadas_minhas.size() - fator - 1)] = jogadas_minhas.get(i);
//                Log.e("TAG","Ref_meu: "+referencia_meu[i - (jogadas_minhas.size() - fator - 1)]);
            }


            ArrayList<Integer> proxima_jogada = new ArrayList<Integer>();
            //Numero de vezes que a proxima jogada e 1,2 ou 3
            int NumberOfOnes = 0;
            int NumberOfTwos = 0;
            int NumberOfThrees = 0;

            for (int i = 0; i < jogadas_minhas.size() - (fator + 1); i++){
                int[] candidato1 = new int[fator + 1];


//                Log.e("TAG","Candidatos:");
                for (int j = i; j < i + (fator + 1); j++){
                    candidato1[j - i] = jogadas_minhas.get(j);
//                    Log.e("TAG","Cand1: "+candidato1[j - i]);
                }

                if (compareArrays(candidato1,referencia_meu)){
                    if(jogadas_minhas.get(i+1) == 1){
                        NumberOfOnes += 1;
                    } else if (jogadas_minhas.get(i+1) == 2){
                        NumberOfTwos += 1;
                    } else if (jogadas_minhas.get(i+1) == 3){
                        NumberOfThrees += 1;
                    }

//                    Log.e("TAG","Entrei aqui mesmo");
                }
            }

            //Se for encontrado algum padrao escolher o que ocorreu mais vezes
            if (NumberOfOnes != 0 || NumberOfTwos != 0 || NumberOfThrees != 0) {

                int max =  Math.max(Math.max(NumberOfOnes,NumberOfTwos),NumberOfThrees);

                //Neste caso preve-se que o jogador jogue 1 logo responde-se com 2
                if (NumberOfOnes == Math.max(Math.max(NumberOfOnes,NumberOfTwos),NumberOfThrees)) {
                    AiMove = 2;
                } else if (NumberOfTwos == Math.max(Math.max(NumberOfOnes,NumberOfTwos),NumberOfThrees)) {
                    AiMove = 3;
                } else if (NumberOfThrees == Math.max(Math.max(NumberOfOnes,NumberOfTwos),NumberOfThrees)) {
                    AiMove = 1;
                }
            } else{
                fator -= 1;

                if (fator == -1){
                    AiMove = JogaRandom();
                }
            }
        }
        return AiMove;
    }


    public boolean compareArrays(int[] array1, int[] array2) {
        boolean b = true;
        if (array1 != null && array2 != null){
            if (array1.length != array2.length)
                b = false;
            else
                for (int i = 0; i < array2.length; i++) {
                    if (array2[i] != array1[i]) {
                        b = false;
                    }
                }
        }else{
            b = false;
        }
        return b;
    }

    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

}
