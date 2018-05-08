import java.util.*;

public class Lane{

	public int[] start;
	public int[] size;
	public int width;
	public int height;
	public ArrayList<Car> cars;
	public int goal;
	public String direction;

	public Lane(String direction, int width, int height, int x, int y){

		this.direction = direction;
		cars = new ArrayList<Car> ();
		this.start = new int[2];
		this.start[0] = x;
		this.start[1] = y;

		if(width > height){
			goal = width;
		}else{
			goal = height;
		}

		this.width = width;
		this.height = height;

		this.size = new int[2];
		if(x == 40){
			this.size[0] = 20;
			this.size[1] = 40;
		}else{
			this.size[0] = 40;
			this.size[1] = 20;
		}

	}

	public void move(){
		ArrayList<Car> temp = new ArrayList<Car>();
		for(int i = 0; i < cars.size (); i++){
			Car car = cars.get(i);

			Car clone = car.copy();
			Main.world.map[car.current.x][car.current.y].valid = true;
			int status = car.doGo();

			if(status == -1){
				temp.add (clone);
				Main.world.map[clone.current.x][clone.current.y].valid = false;
			}else if (status == 0){
				temp.add (car);
				Main.world.map[car.current.x][car.current.y].valid = false;
			}
		}
		cars = temp;
	}

	public boolean fill(){
		if(width < height){
			for(int i = start[1] + size[1] - 1; i >= start[1]; i--){
				for(int j = start[0] + size[0] - 1; j >= start[0]; j--){
					Car car = new Car ();
					car.setStart (i, j);
					car.setGoal (goal, j);
					if(cars.indexOf (car) < 0){
						cars.add (car);
						return true;
					}
				}
			}
		}else{
			for(int j =start[0] + size[0] -1; j >= start[0]; j--){
				for(int i = start[1] + size[1] -1; i >= start[1]; i--){
					Car car = new Car ();
					car.setStart (i, j);
					car.setGoal (i, goal);
					if(cars.indexOf (car) < 0){
						cars.add (car);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o){
		Lane other = (Lane) o;
		return (this.direction.equals(other.direction));
	}

}