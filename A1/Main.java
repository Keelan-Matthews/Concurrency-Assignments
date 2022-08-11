public class Main {
    public static void main(String[] args) {
        Project project = new Project();
        
        int developerCount = 5;
        int testerCount = 5;
	    Developer[] developers = new Developer[developerCount];
        Tester[] testers = new Tester[testerCount];

        Bakery devLock = new Bakery(developerCount);
        Bakery testLock = new Bakery(testerCount);

        for(int i = 0; i < developerCount; i++) {
            developers[i] = new Developer(devLock, project);
            developers[i].start();
        }

        for(int i = 0; i < testerCount; i++) {
            testers[i] = new Tester(testLock, project);
            testers[i].start();
        }
    }
}
