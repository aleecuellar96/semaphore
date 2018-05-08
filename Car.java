import java.util.*;

public class Car{

	private List open_list;
	private List closed_list;
	public Cell current;
	private Cell start;
	private Cell goal;

	public Car(){
		open_list = new List();
		closed_list = new List();
	}

	public void setStart(int x, int y){
		open_list = new List();
		start =  Main.world.map[x][y];
		open_list.append(start);
		current = start;
	}

	public void setGoal(int x, int y){
		goal = new Cell(x, y);
	}

	public int doGo(){
		Cell previous = current.copy();
		if(current.move != null){
			Cell next = Main.world.map[this.current.x + current.move.y][this.current.y + current.move.x].copy();
			if(next.valid && next.reward != Cell.obstacle){
				current = next;
			}else{
				ArrayList<Cell> neighbors = current.neighbors();
				for(Cell neighbor : neighbors){
					if(neighbor.valid && neighbor.reward == Cell.free){
						if(current.x > (Main.world.height/2)){
							if(neighbor.x >= current.x && !neighbor.equals(current)){
								current = neighbor;
							}
						}else if (current.y > (Main.world.width/2)){
							if(neighbor.y>= current.y && !neighbor.equals(current)){
								current = neighbor;
							}
						}else{
							if(neighbor.x >= current.x && neighbor.y >= current.y && !neighbor.equals(current)){
								current = neighbor;
							}
						}
					}

				}
			}
		}

		if(current.reward == Cell.goal){
			return 1;
		}else if(!previous.equals (current)){
			return 0;
		}else{
			return -1;
		}
	}

	public Car copy() {
		Car temp = new Car();
		temp.setStart(this.current.x, this.current.y);
		temp.setGoal(this.goal.x, this.goal.y);
		return temp;
	}

	@Override
	public boolean equals(Object o){
		Car other = (Car) o;
		return (this.current.equals(other.current));
	}

	@Override
	public String toString() {
		return String.format("[" + this.current.y + "," + this.current.x + "] GOAL: " + this.goal);
	}
}
