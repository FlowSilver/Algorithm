/**
 * The Engine class simulates the transportation between stations
 * Each engine operates as a thread that
 * continuously shuttles carts between two connected IStayable locations
 * (stations or the elevator).
 *
 *
 * The engine follows a continuous loop:
 * 1. Loads cart from start station (if at start position)
 * 2. Travels to end station (with simulated delay)
 * 3. Delivers cart to end station
 * 4. Returns empty to start position
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Engine extends Thread{
    /** The starting station for this engine's route. */
    private IStayable startStation;

    /** The destination station for this engine's route. */
    private IStayable endStation;

    /** Flag indicating whether the engine is at the start. */
    private boolean atStart = true;

    /** Cart carried by the engine. */
    private Cart carriedCart;

    /**
     * Constructs an Engine connecting two IStayable instances.
     *
     * @param startStation The starting station for this engine's route
     * @param endStation The destination station for this engine's route
     */
    public Engine(IStayable startStation, IStayable endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
    }

    /**
     * Simulates the engine's travel time between stations.
     * - Introduces a delay
     * - Toggles the engine's position (atStart flag)
     */
    private void travel() {
        // travel
        try {
            sleep(Params.ENGINE_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        atStart = !atStart;
    }

    /**
     * Main execution loop for the Engine thread.
     *
     * 1. If at start station:
     *    - Loads a cart from start station (blocks until available)
     *    - Prints collection notification
     * 2. Travels to opposite station
     * 3. If at end station:
     *    - Delivers cart to end station (blocks until station is ready)
     *    - Prints delivery notification
     * 4. Resets carried cart and repeats
     */
    @Override
    public void run() {
        while(true) {
            if(atStart) {
                carriedCart = startStation.loadCart();

                // Print statement
                System.out.println(carriedCart + " collected from " + endStation);
            }

            travel();

            if(!atStart) {
                endStation.deliverCart(carriedCart);

                // Print statement
                System.out.println(carriedCart + " delivered to " + endStation);
            }
            carriedCart = null;
        }
    }
}
