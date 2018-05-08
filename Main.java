/*
Cinthya Daniela Lugo Novoa A01332942
Alejandra Cuellar Gonzalez A01333324
*/

import java.util.*;
import java.io.*;

public class Main {

	public static CrossRoad world;
	public static int arrived;
	public static Cell[][] temp;
	public static void main (String args[]) {
		
		Cell.obstacle = -10.0f;
		Cell.free = -0.04f;
		Cell.goal = 30.0f;
		Cell.edge = -1000.0f;

		CrossRoad.gamma = 0.8f;
		CrossRoad.probability = 0.9f;

		addElements();

		LightTraffic light = new LightTraffic();
		MediumTraffic medium = new MediumTraffic();
		HeavyTraffic heavy = new HeavyTraffic();

		world.semaphore.addScheme(light);
		world.semaphore.addScheme(medium);
		world.semaphore.addScheme(heavy);
		world.semaphore.target = 0;
		world.semaphore.timeTraffic(world.getTotal());

		write(current(),"fuzzy.txt");

		int iterations = 0;
		do{
			temp = world.copy();
			for(int i = 0; i < world.height; i++){
				for(int j = 0; j < world.width; j++){
					world.map[i][j].calculate();;
				}
			}
			iterations++;

		}while(world.noChange(temp) != true);

		write(world.directionMapFile() ,"fuzzy.txt");

		double timeCounter = 0;
		int counter = 0;
		double totalTime = 0;
		while (world.getTotal() > 0) {
			if (timeCounter >= world.semaphore.time) {
				world.semaphore.swap (world.getTotal());
				timeCounter = 0;
				counter++;
				write(current(),"fuzzy.txt");
			}
			world.getIndex(world.semaphore.active).move();
			timeCounter += 1;
			totalTime += 1;
		}
		System.out.println("The result is in the file: fuzzy.txt");
		write(current(),"fuzzy.txt");
	}

	public static void addElements() {

		CrossRoad crossing = new CrossRoad ();

		Lane northSouth = new Lane("N-S", 19, 99, 40, 0);
		Lane westEast = new Lane("W_E", 99, 19, 0, 40);

		crossing.createRoad(0, northSouth);
		crossing.createRoad(1, westEast);

		crossing.addObstacle (48, 52);crossing.addObstacle (47, 51);crossing.addObstacle (53, 51);crossing.addObstacle (54, 51);crossing.addObstacle (44, 95);
		crossing.addObstacle (48, 47);crossing.addObstacle (49, 47);crossing.addObstacle (50, 47);crossing.addObstacle (51, 47);crossing.addObstacle (52, 47);
		crossing.addObstacle (48, 48);crossing.addObstacle (49, 48);crossing.addObstacle (50, 48);crossing.addObstacle (51, 48);crossing.addObstacle (52, 48);
		crossing.addObstacle (48, 49);crossing.addObstacle (52, 49);crossing.addObstacle (50, 49);crossing.addObstacle (51, 49);crossing.addObstacle (49, 49);
		crossing.addObstacle (46, 49);crossing.addObstacle (47, 49);crossing.addObstacle (53, 49);crossing.addObstacle (54, 49);crossing.addObstacle (48, 50);
		crossing.addObstacle (49, 50);crossing.addObstacle (52, 50);crossing.addObstacle (51, 50);crossing.addObstacle (50, 50);crossing.addObstacle (46, 50);
		crossing.addObstacle (47, 50);crossing.addObstacle (53, 50);crossing.addObstacle (54, 50);crossing.addObstacle (48, 51);crossing.addObstacle (49, 51);
		crossing.addObstacle (50, 51);crossing.addObstacle (52, 51);crossing.addObstacle (51, 51);crossing.addObstacle (46, 51);crossing.addObstacle (49, 52);
		crossing.addObstacle (50, 52);crossing.addObstacle (51, 52);crossing.addObstacle (52, 52);crossing.addObstacle (46, 52);crossing.addObstacle (47, 52);
		crossing.addObstacle (53, 52);crossing.addObstacle (54, 52);crossing.addObstacle (48, 53);crossing.addObstacle (49, 53);crossing.addObstacle (50, 53);
		crossing.addObstacle (51, 53);crossing.addObstacle (52, 53);crossing.addObstacle (48, 54);crossing.addObstacle (49, 54);crossing.addObstacle (50, 54);
		crossing.addObstacle (51, 54);crossing.addObstacle (52, 54);crossing.addObstacle (49, 95);crossing.addObstacle (54, 95);crossing.addObstacle (95, 49);
		crossing.addObstacle (95, 44);crossing.addObstacle (95, 54);

		crossing.addGoal (40, 99);crossing.addGoal (41, 99);crossing.addGoal (42, 99);crossing.addGoal (43, 99);
		crossing.addGoal (45, 99);crossing.addGoal (46, 99);crossing.addGoal (47, 99);crossing.addGoal (48, 99);
		crossing.addGoal (50, 99);crossing.addGoal (51, 99);crossing.addGoal (52, 99);crossing.addGoal (53, 99);
		crossing.addGoal (55, 99);crossing.addGoal (56, 99);crossing.addGoal (57, 99);crossing.addGoal (58, 99);crossing.addGoal (59, 99);
		crossing.addObstacle (44, 99);crossing.addObstacle (44, 98);crossing.addObstacle (44, 97);crossing.addObstacle (44, 96);
		crossing.addObstacle (49, 99);crossing.addObstacle (49, 98);crossing.addObstacle (49, 97);crossing.addObstacle (49, 96);
		crossing.addObstacle (54, 99);crossing.addObstacle (54, 98);crossing.addObstacle (54, 97);crossing.addObstacle (54, 96);

		crossing.addGoal ( 99, 40);crossing.addGoal ( 99, 41);crossing.addGoal ( 99, 42);crossing.addGoal ( 99, 43);
		crossing.addGoal ( 99, 45);crossing.addGoal ( 99, 46);crossing.addGoal ( 99, 47);crossing.addGoal ( 99, 48);
		crossing.addGoal ( 99, 50);crossing.addGoal ( 99, 51);crossing.addGoal ( 99, 52);crossing.addGoal ( 99, 53);
		crossing.addGoal ( 99, 55);crossing.addGoal ( 99, 56);crossing.addGoal ( 99, 57);crossing.addGoal ( 99, 58);crossing.addGoal ( 99, 59);
		crossing.addObstacle ( 99, 44);crossing.addObstacle ( 98, 44);crossing.addObstacle ( 97, 44);crossing.addObstacle ( 96, 44);		
		crossing.addObstacle ( 99, 49);crossing.addObstacle ( 98, 49);crossing.addObstacle ( 97, 49);crossing.addObstacle ( 96, 49);
		crossing.addObstacle ( 99, 54);crossing.addObstacle ( 98, 54);crossing.addObstacle ( 97, 54);crossing.addObstacle ( 96, 54);
		world = crossing;
		crossing.fill(800, 0);
		crossing.fill(800, 1);
		world = crossing;
	}

	public static String current(){
		String str = "";
		str += "\n";
		str += "For Lane " + world.getIndex(world.semaphore.active).direction +" the time is: " + world.semaphore.time + "\n";
		str += "Semaphore mode: " + world.semaphore.current.level + "\n";
		str += "Total cars: " + world.getTotal() + "\n";
		for (Lane lane: world.lanes) {
			str += "Cars in " + lane.direction + " : " + lane.cars.size() + "\n";
		}
		str += "\n";
		str += world.write();
		return str;
	}

    public static void write(String contents, String file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(contents);
            writer.close();
        }catch(Exception e){
        	System.out.println("error" + e);
        }
    }
}