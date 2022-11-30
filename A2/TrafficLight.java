public class TrafficLight extends Thread {
    private State state[];
    private int numStates;
    private int currentState;
    boolean running = true;
    int adjIntersection1;
    int adjIntersection2;

    public TrafficLight(int id1, int id2) {
        state = new State[4];
        state[0] = new State(200, "red", "green");
        state[1] = new State(400, "red", "yellow");
        state[2] = new State(300, "green", "red");
        state[3] = new State(400, "yellow", "red");
        numStates = state.length;
        currentState = 0;
        adjIntersection1 = id1;
        adjIntersection2 = id2;
    }

    @Override
    public void run() {
        while (true && running) {
            try {
                Thread.sleep(state[currentState].time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeState();
        }
    }

    public void stopLight() {
        running = false;
    }

    public class State {
        int time; // how long the state lasts
        String e1; // the state of the light for edge 1
        String e2; // the state of the light for edge 2
        int e1Intersection; // the intersection that edge 1 is connected to
        int e2Intersection; // the intersection that edge 2 is connected to

        public State(int time, String e1, String e2) {
            this.time = time;
            this.e1 = e1;
            this.e2 = e2;
        }
    }

    public void changeState() {
        currentState = (currentState + 1) % numStates;
    }

    public State getLightState() {
        return state[currentState];
    }
}
