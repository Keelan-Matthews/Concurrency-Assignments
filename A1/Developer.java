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
		while (!project.isDevelopEmpty()) {
			System.out.println(Thread.currentThread().getName() + " is ready to develop a component");
			lock.lock();
			try {
				Component c = project.getFromDevelop();

				int work = (int)(Math.random() * (400)) + 100;
				try {
					Thread.sleep(work);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}

				project.setDevelopTime(c, work);

				if (c.developTime <= 0) {
					System.out.println(Thread.currentThread().getName() + " finished developing " + c.name);
					project.addToTesting(c);
				}
				else {
					System.out.println(Thread.currentThread().getName() + " developed " + c.name + " for " + work + "ms. Time remaining: " + c.developTime + "ms");
					project.addToDevelop(c);
				}

			} catch (NullPointerException e) {}
			finally {
				System.out.println(Thread.currentThread().getName() + " is taking a break.\n");
				lock.unlock();

				if (!project.isDevelopEmpty()) {
					int breaktime = (int)(Math.random() * (50)) + 50;
					try {
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
