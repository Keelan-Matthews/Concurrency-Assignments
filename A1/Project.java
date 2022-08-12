import java.util.LinkedList;
import java.util.Queue;


public class Project
{
	private volatile Queue<Component> develop = new LinkedList<>(), testing = new LinkedList<>();
	public volatile int testCount = 0;
	public volatile int developCount;
	
	public Project(){
		develop.add(new Component('s', "Calculator"));
		develop.add(new Component('m', "Calendar"));
		develop.add(new Component('s', "Billing"));
		develop.add(new Component('l', "Timetable"));
		develop.add(new Component('m', "Phonebook"));
		develop.add(new Component('l', "User Manager"));
		develop.add(new Component('s', "Api"));
		developCount = develop.size();
	}

	public synchronized void addToDevelop(Component c){
		develop.add(c);
	}

	public synchronized void addToTesting(Component c){
		develop.remove(c);
		testing.add(c);
	}

	public void comleteTesting(Component c){
		testing.remove(c);
		testCount++;
	}

	public synchronized Component getFromDevelop(){
		return develop.poll();
	}

	public synchronized void setDevelopTime(Component c, long time){
		c.developTime -= time;
	}

	public synchronized void setTestTime(Component c, long time){
		c.testTime -= time;
	}

	public synchronized Component getFromTesting(){
		return testing.poll();
	}

	public synchronized boolean isDevelopEmpty(){
		return develop.isEmpty();
	}

	public synchronized boolean isTestingEmpty(){
		return testing.isEmpty();
	}

	public synchronized int getDevelopSize(){
		return develop.size();
	}

	public synchronized int getTestingSize(){
		return testing.size();
	}

}
