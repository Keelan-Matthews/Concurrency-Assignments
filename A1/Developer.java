public class Developer extends Thread {
    private Bakery lock;
    private Project project;
	private int id;

    public Developer(Bakery l, Project p){
        lock = l;
        project = p;
		String s = Thread.currentThread().getName();
		id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
    }

	@Override
	public void run()
	{
		while (!project.isDevelopEmpty()) {
			System.out.println("Developer " + id + " is ready to develop a component");
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
					System.out.println("Developer " + id + " finished developing " + c.name);
					project.addToTesting(c);
				}
				else {
					System.out.println("Developer " + id + " developed " + c.name + " for " + work + "ms. Time remaining: " + c.developTime + "ms");
					project.addToDevelop(c);
				}

			} finally {
				lock.unlock();

				if (!project.isDevelopEmpty()) {
					int breaktime = (int)(Math.random() * (50)) + 50;
					try {
						System.out.println("Developer " + id + " is taking a break.");
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
