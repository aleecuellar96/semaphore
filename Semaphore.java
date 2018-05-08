import java.util.*;

public class Semaphore{

	public ArrayList<Traffic> modes;
	public double time;
	public Traffic current;
	public int target;
	public int active;

	public Semaphore(){
		modes = new ArrayList<Traffic>();
	}

	public void addScheme(Traffic scheme){
		modes.add(scheme);
	}

	public void setTime(double time){
		this.time = time;
	}

	public Traffic getMode(int traffic){
		for(Traffic scheme : modes){
			if(scheme.fit(traffic)){
				return scheme;
			}
		}
		return null;
	}

	public void timeTraffic(int traffic){

		current = getMode(traffic);
		double timeFromTraffic = Math.floor((0.078125*traffic)+35);
		double scores[] = new double[modes.size()];
		double noChange = -1;

		for(int i = 0; i < modes.size (); i++){
			scores[i] = modes.get(i).membership (timeFromTraffic);
			if(i == current.level){
				noChange = scores[i];
			}
		}
		if(current.level < target){
			current = modes.get (modes.size () - 1);
			this.time = (timeFromTraffic + current.limits[current.limits.length - 1])/2;
		}else if(current.level > target){
			current = getMode(traffic);
			this.time = (timeFromTraffic + current.limits[current.limits.length - 1])/2;
		}else{
			this.time = (timeFromTraffic + current.limits[current.limits.length - 1])/2;
		}
	}

	public void swap(int traffic){
		timeTraffic(traffic);
		if(active == 0){
			active = 1;
		}else{
			active = 0;
		}
	}

}
