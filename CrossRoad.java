import java.util.*;

public class CrossRoad{

	public static float gamma;
	public static float probability;
	public int[] size;
	public Semaphore semaphore;
	public Lane[] lanes;
	public Cell map[][];
	public int width;
	public int height;
	public Cell start;
	public Cell goal;


	public CrossRoad(){
		createWorld(40 * 2 + 20, 40 * 2 + 20);
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				map[i][j].reward = Cell.edge;
				map[i][j].value = map[i][j].reward;
			}
		}
		size = new int[2];
		size[0] = 40;
		size[1] = 20;

		lanes = new Lane[2];
		semaphore = new Semaphore();
		semaphore.active = 0;
	}

	public void createWorld(int width, int height){
		this.width = height;
		this.height = width;
		map = new Cell[this.height][this.width];
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				Cell cell = new Cell(i, j);
				map[i][j] = cell;
			}
		}
	}

	public void addObstacle (int x, int y) {
		map[x][y].valid = true;
		map[x][y].reward = Cell.obstacle;
		map[x][y].value = map[x][y].reward;
	}

	public void addGoal (int x, int y) {
		map[x][y].valid = true;
		map[x][y].reward = Cell.goal;
		map[x][y].value = map[x][y].reward;
	}

	public void fill(int n, int lane) {
		if(lane < lanes.length){
			for(int i = 0; i < n; i++){
				lanes[lane].fill();
			}
		}
	}

	public void createRoad(int index, Lane lane){
		if(index >= 0 && index < 2){
			lanes[index] = lane;
			
			for(int i = 0; i < this.height; i++){
				for(int j = 0; j < this.width; j++){
					if(j >= lane.start[0] && j <= lane.start[0] + lane.width && i >= lane.start[1] && i <= lane.start[1] + lane.height){
						map[i][j].valid = true;
						map[i][j].reward = Cell.free;
						map[i][j].value = map[i][j].reward;
					}
				}
			}			
		}
	}

	public boolean noChange(Cell world[][]){
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				if(map[i][j].compareTo(world[i][j]) != 0){
					return false;
				}
			}
		}
		return true;
	}
	
	public Lane getIndex(int index) {
		if (index < lanes.length) {
			return lanes[index];
		}
		return null;
	}

	public int getTotal(){
		int count = 0;
		for(int i = 0; i < lanes.length; i++){
			if(lanes[i] != null){
				count += lanes[i].cars.size();
			}
		}
		return count;
	}

	public int carPosition(int x, int y){
		for(int i = 0; i < lanes.length; i++){
			if(lanes[i] != null){
				for(Car car : lanes[i].cars){
					if(car.current.x == x && car.current.y == y){
						return i;
					}
				}
			}
		}
		return -1;
	}

	public String write() {
		String result = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int lane = carPosition(i, j);
				if (lane == 1) {
					result += ">";
				} else if (lane == 0) {
					result += "v";
				} else if (!map[i][j].valid && lane == -1) {
					result += ".";
				} else if (map[i][j].valid && map[i][j].reward == Cell.obstacle) {
					result += "O";
				}  else  if (map[i][j].visited) {
					result += " ";
				} else if (map[i][j].valid && map[i][j].reward == Cell.goal) {
					result += ":";
				} else if (map[i][j].valid && map[i][j].reward == Cell.free) {
					result += " ";
				}
			}
			result += "\n";
		}
		return result;
	}

	public String directionMapFile(){
		String result = ""; 
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int lane = carPosition(i, j);
					switch(map[i][j].move.id){
						case "1":
							result += " U "; break;
						case "2":
							result += " UR"; break;
						case "3":
							result += " R "; break;
						case "4":
							result += " DR"; break;
						case "5":
							result += " D "; break;
						case "6":
							result += " DL"; break;
						case "7":
							result += " L "; break;
						case "8":
							result += " UL"; break;
						case "9":
							result += " S "; break;
					}
			}
			result += "\n";
		}
		return result;
	}

	public Cell[][] copy(){
		Cell[][] newMap = new Cell[this.height][this.width];
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				newMap[i][j] = map[i][j].copy();
			}
		}
		return newMap;
	}
}