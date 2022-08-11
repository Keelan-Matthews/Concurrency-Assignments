import java.util.LinkedList;
import java.util.Queue;


public class Project
{
	private volatile Queue<Component> develop = new LinkedList<>(), testing = new LinkedList<>();
	
	public Project(){
		develop.add(new Component('s', "Calculator"));
		develop.add(new Component('m', "Calendar"));
		develop.add(new Component('s', "Billing"));
		develop.add(new Component('l', "Timetable"));
		develop.add(new Component('m', "Phonebook"));
		develop.add(new Component('l', "User Manager"));
		develop.add(new Component('s', "Api"));
	}

}
