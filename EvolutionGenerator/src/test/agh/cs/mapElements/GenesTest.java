package agh.cs.mapElements;

import agh.cs.mapElements.Genes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenesTest {
    @Test
    void Genes(){
        Genes genes = new Genes();
        Genes genes2 = new Genes();
        Genes genesFromParents = new Genes(genes, genes2);
        for (int i=0; i< 32; i++) {
            assertTrue(genes.getGeneByIndex(i) >= 0 && genes.getGeneByIndex(i) <= 7);
            assertTrue(genes2.getGeneByIndex(i) >= 0 && genes2.getGeneByIndex(i) <= 7);
            assertTrue(genesFromParents.getGeneByIndex(i) >= 0 && genesFromParents.getGeneByIndex(i) <= 7);
        }
        int [] typesOfGenes = new int[8];
        int [] typesOfGenes2 = new int[8];
        int [] typesOfGenesFromParents = new int[8];
        for (int i=0; i<32; i++) {
            typesOfGenes[genes.getGeneByIndex(i)] += 1;
            typesOfGenes2[genes2.getGeneByIndex(i)] += 1;
            typesOfGenesFromParents[genesFromParents.getGeneByIndex(i)] += 1;
        }
        for (int i=0; i<8; i++) {
            assertTrue(typesOfGenes[i] > 0);
            assertTrue(typesOfGenes2[i] > 0);
            assertTrue(typesOfGenesFromParents[i] > 0);
        }

    }
}

