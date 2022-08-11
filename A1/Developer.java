public class Developer extends Thread {
    private Bakery lock;
    private Project project;

    public Developer(Bakery l, Project p){
        lock = l;
        project = p;
    }

	@Override
	public void run()
	{
		String s = Thread.currentThread().getName();
		int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
		for (int i = 0; i < 5; i++) {
			lock.lock();
			try {
				
			} finally {

				lock.unlock();
			}
		}
	}
}
