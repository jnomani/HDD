import java.util.Date;


public class Scheduler  extends Thread{

	private Scheduler(int increment, int month){
		
		this.s = new scheduleEvent(increment, month);
		
	
		
	}

	
	private scheduleEvent s;

	public boolean isReady(){
		return s.isReady;
	}
	
	public static Scheduler schedule(int increment, int month){
		
		Scheduler t = new Scheduler(increment, month);
		t.setPriority(MIN_PRIORITY);
		
		t.start();
		
		return t;
	}
	
	}


	class scheduleEvent implements Runnable{

		private int month;
		private int increment;
		
		boolean isReady;
		
		public scheduleEvent(int increment, int month){
			this.increment = increment;
			this.month = month;
		}
		
		@Override
		public void run() {
			month += increment;
			
			
			
		}
		
	}

