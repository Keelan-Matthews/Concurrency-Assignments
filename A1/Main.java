public class Main {
    public static void main(String[] args) {
        Project project = new Project();
        
        int teamCount = 4;

	    Developer[] developers = new Developer[teamCount];
        Tester[] testers = new Tester[teamCount];

        Bakery devLock = new Bakery(teamCount);
        Bakery testLock = new Bakery(teamCount);

        for(int i = 0; i < teamCount; i++) {
            developers[i] = new Developer(devLock, project);
            developers[i].setName("Developer-" + i);
            testers[i] = new Tester(testLock, project);
            testers[i].setName("Tester-" + i);
            
        }

        for(int i = 0; i < teamCount; i++) {
            developers[i].start();
            testers[i].start();
        }
    }
}
