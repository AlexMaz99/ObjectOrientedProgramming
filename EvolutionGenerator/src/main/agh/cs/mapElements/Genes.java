package agh.cs.mapElements;

import java.util.Arrays;
import java.util.Random;

public class Genes {
    private static final int NUMBEROFGENES = 32;
    private static final int NUMBEROFTYPE = 8;
    private int [] genes = new int [NUMBEROFGENES];
    private Random random = new Random();

    public Genes(){
        generateGens();
    }
    public Genes(Genes parent1, Genes parent2){
        generateGens(parent1, parent2);
    }

    private void generateGens(){
        for (int i=0; i<NUMBEROFTYPE; i++){
            this.genes[i]=i;
        }
        for (int i=NUMBEROFTYPE; i<NUMBEROFGENES; i++)
            this.genes[i]=new Random().nextInt(NUMBEROFTYPE);
        Arrays.sort(this.genes);
    }

    private void generateGens(Genes parent1Genes, Genes parent2Genes){
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
        System.arraycopy(parent1Genes.genes, 0, this.genes, 0, bound1 + 1);
        System.arraycopy(parent2Genes.genes, bound1 + 1, this.genes, bound1 + 1, bound2 - bound1);
        System.arraycopy(parent1Genes.genes, bound2, this.genes, bound2 + 1, NUMBEROFGENES - bound2 - 1);
        Arrays.sort(this.genes);

        //check if genes contains all types of genes
        int [] typesOfGenes = new int[8];
        for (int i=0; i<32; i++) {
            typesOfGenes[this.genes[i]] += 1;
        }
        for (int i=0; i<NUMBEROFTYPE; i++){
            if (typesOfGenes[i] <= 0)
                this.genes[i] = i;
        }
        Arrays.sort(this.genes);
    }

    @Override
    public String toString(){
        String dna = "";
        for (int i=0; i<NUMBEROFGENES; i++){
            dna += this.genes[i];
        }
        return dna;
    }

    public int getGeneByIndex (int i){
        return this.genes[i];
    }
}
