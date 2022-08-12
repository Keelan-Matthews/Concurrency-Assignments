public class Tester extends Thread {
    private Bakery lock;
    private Project project;
	private int id;

    public Tester(Bakery l, Project p){
        lock = l;
        project = p;
		String s = Thread.currentThread().getName();
		id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
    }

	@Override
	public void run()
	{
		while (project.developCount > project.testCount) {
			System.out.println("Tester " + id + " is ready to test a component");
			lock.lock();
			try {
				Component c = project.getFromTesting();

				int work = (int)(Math.random() * (400)) + 100;
				try {
					Thread.sleep(work);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}

				project.setTestTime(c, work);

				if (c.testTime <= 0) {
					System.out.println("Tester " + id + " finished testing " + c.name);
					project.comleteTesting(c);
				}
				else {
					System.out.println("Tester " + id + " tested " + c.name + " for " + work + "ms. Time remaining: " + c.testTime + "ms");
					project.addToTesting(c);
				}
				
			} finally {
				lock.unlock();

				if (project.developCount > project.testCount) {
					int breaktime = (int)(Math.random() * (50)) + 50;
					try {
						System.out.println("Tester " + id + " is taking a break.");
						Thread.sleep(breaktime);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
