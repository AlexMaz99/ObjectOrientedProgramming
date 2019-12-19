package agh.cs.mapElements;

import java.util.Arrays;
import java.util.Random;

public class Genes {
    private static final int NUMBEROFGENES = 32;
    private static final int NUMBEROFTYPE = 8;
    private int [] dna = new int [NUMBEROFGENES];
    private Random random = new Random();

    public Genes(){
        for (int i=0; i<NUMBEROFTYPE; i++){
            this.dna[i]=i;
        }
        for (int i=NUMBEROFTYPE; i<NUMBEROFGENES; i++)
            this.dna[i]=new Random().nextInt(NUMBEROFTYPE);
        Arrays.sort(this.dna);
    }

    Genes(Genes parent1Genes, Genes parent2Genes){
        int bound1 = random.nextInt(NUMBEROFGENES - 2) + 1;
        int bound2;
        do{
            bound2 = random.nextInt(NUMBEROFGENES - 2) + 1;
        }while (bound1 == bound2);

        if (bound1 > bound2){
            int tmp = bound2;
            bound2 = bound1;
            bound1 = tmp;
        }
        System.arraycopy(parent1Genes.dna, 0, this.dna, 0, bound1 + 1);
        System.arraycopy(parent2Genes.dna, bound1 + 1, this.dna, bound1 + 1, bound2 - bound1);
        System.arraycopy(parent1Genes.dna, bound2, this.dna, bound2 + 1, NUMBEROFGENES - bound2 - 1);
        Arrays.sort(this.dna);

        //check if genes contains all types of genes
        this.correctGenes();
    }

    private void correctGenes(){
        int [] typesOfGenes = new int[NUMBEROFTYPE];
        for (int i=0; i<32; i++) {
            typesOfGenes[this.dna[i]] ++;
        }
        for (int i=0; i<NUMBEROFTYPE; i++){
            if (typesOfGenes[i] <= 0){
                int x;
                do{
                    x = random.nextInt(NUMBEROFTYPE);
                } while (typesOfGenes[x]<2);
                typesOfGenes[x]--;
                typesOfGenes[i]++;
            }
        }
        int counter = 0;
        for (int i=0; i<NUMBEROFTYPE; i++){
            while (typesOfGenes[i]--!=0){
                this.dna[counter++] = i;
            }
        }
    }

    @Override
    public String toString(){
        String dna = "";
        for (int i=0; i<NUMBEROFGENES; i++){
            dna += this.dna[i];
        }
        return dna;
    }

    public int getGeneByIndex (int i){
        return this.dna[i];
    }
}
