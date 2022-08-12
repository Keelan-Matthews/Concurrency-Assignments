public class Tester extends Thread {
    private Bakery lock;
    private Project project;

    public Tester(Bakery l, Project p){
        lock = l;
        project = p;
    }

	@Override
	public void run()
	{
		System.out.println(Thread.currentThread().getName() + " is ready to test a component");
		while (project.developCount > project.testCount && !project.isTestingEmpty()) {
			
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
					System.out.println(Thread.currentThread().getName() + " finished testing " + c.name);
					project.comleteTesting(c);
				}
				else {
					System.out.println(Thread.currentThread().getName() + " tested " + c.name + " for " + work + "ms. Time remaining: " + c.testTime + "ms");
					project.addToTesting(c);
				}
				
			} finally {
				System.out.println(Thread.currentThread().getName() + " is taking a break.\n");
				lock.unlock();

				if (project.developCount > project.testCount) {
					int breaktime = (int)(Math.random() * (50)) + 50;
					try {
						Thread.sleep(breaktime);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("It seems that all components have been tested.");
				}
			}
		}
	}
}
