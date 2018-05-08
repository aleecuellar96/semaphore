import java.util.ArrayList;

public class Cell implements Comparable<Cell>{

	public Movement move;
	public int x;
	public int y;
	public boolean valid;
	public boolean path;
	public boolean visited;
	public float reward;
	public float value;
	public static float goal;
	public static float obstacle;
	public static float free;
	public static float edge;

	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		value = 0;
	}

	public Cell copy(){
		Cell cell = new Cell(x, y);
		cell.reward = reward;
		cell.value = value;
		cell.move = move;
		cell.valid = valid;
		cell.visited = visited;
		return cell;
	}

	public void calculate(){
		ArrayList<Cell> neighbors = this.neighbors();
		float q = (1.0f-CrossRoad.probability)/(neighbors.size () -1);
		float max = -20001;
		Movement maxMove = null;
		
		for(Movement move : Movement.values()){	
			if(this.x + move.y >= 0 && this.x + move.y < Main.world.height){
				if(this.y + move.x >= 0 && this.y + move.x < Main.world.width){				
				}else{
					continue;
				}
			}else if (move.x == 0 && move.y == 0){
				continue;
			}else{
				continue;
			}

			float newValue = 0;
			for(Movement move2 : Movement.values ()) {
				if(this.x + move2.y >= 0 && this.x + move2.y < Main.world.height){
					if(this.y + move2.x >= 0 && this.y + move2.x < Main.world.width){
						Cell neighbor = Main.temp[this.x + move2.y][this.y + move2.x];
							if(neighbor.x == (this.x + move.y) && neighbor.y == (this.y + move.x) ){
							newValue += CrossRoad.probability * neighbor.value;
						} else {
							newValue += q * neighbor.value;
						}
					}
				}else{}
			}

			if(newValue > max){
				max = newValue;
				maxMove = move;
			}
		}

		this.value = this.reward + max * CrossRoad.gamma;
		this.move = maxMove;
	}

	public ArrayList<Cell> neighbors(){
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(Movement move : Movement.values ()){
			if(this.x + move.y >= 0 && this.x + move.y < Main.world.height){
				if(this.y + move.x >= 0 && this.y + move.x < Main.world.width){
					Cell cell = Main.world.map[this.x + move.y][this.y + move.x];
					cells.add(cell.copy());
				}
			}
		}
		return cells;
	}

	@Override
	public boolean equals(Object o){
		Cell other =(Cell) o;
		return (this.x == other.x && this.y == other.y);
	}

	@Override
	public int compareTo(Cell other){
		if(this.value > other.value){
			return 1;
		}else if (this.value < other.value){
			return -1;
		}else{
			return 0;
		}
	}

}
