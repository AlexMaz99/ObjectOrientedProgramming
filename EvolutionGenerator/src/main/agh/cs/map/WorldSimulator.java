package agh.cs.map;

import agh.cs.mapElements.Animal;

public class WorldSimulator {
    private IWorldMap map;
    private int day;

    public WorldSimulator(JSONReader jsonReader){
        this.map = new WorldMap(jsonReader.getMapWidth(),
                jsonReader.getMapHeight(),
                jsonReader.getJungleRatio(),
                jsonReader.getPlantEnergy(),
                jsonReader.getMoveEnergy(),
                jsonReader.getStartEnergy(),
                jsonReader.getStartNumberOfAnimals());
        this.day = 0;
    }

    public void anotherDay(){
        ((WorldMap)this.map).removeDeadAnimals();
        for (Animal animal : this.map.getAnimals()){
            animal.move();
        }
        ((WorldMap)this.map).eatGrass();
        ((WorldMap)this.map).procreate();
        ((WorldMap)this.map).generateGrass();
        this.day++;
    }

    public String getStatistics(){
        int averageAge = 0;
        int averageEnergy = 0;

        for (Animal animal: this.map.getAnimals()){
            averageEnergy += animal.getEnergy();
        }
        if (this.map.getAnimals().size() != 0) {
            averageEnergy /= this.map.getAnimals().size();
        }
        if (this.map.getNumberOfDeadAnimals() != 0) {
            averageAge = this.map.getSumOfAgeDeadAnimals() / this.map.getNumberOfDeadAnimals();
        }
        return "Day: " + this.day + "\n"
                + "Number of living animals: " + this.map.getAnimals().size() + "\n"
                + "Average energy of living animals: " + averageEnergy + "\n"
                + "Number of dead animals: " + this.map.getNumberOfDeadAnimals() + "\n"
                + "Average age of dead animals: " + averageAge;
    }

    public IWorldMap getMap() {
        return map;
    }
}
