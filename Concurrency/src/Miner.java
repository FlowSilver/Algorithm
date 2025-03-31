/**
 * The Miner class simulates a gem miner in the mine.
 * Each miner operates continuously at a specific station, periodically mining gems
 * and depositing them at their assigned station.
 *
 * Key Behavior:
 * - Operates as an independent thread
 * - Mines gems at intervals defined by Params.MINING_TIME
 * - Deposits mined gems at its assigned station
 * - Runs indefinitely until interrupted
 *
 * Synchronization Note:
 * - The depositGem() method of Station handles thread safety for gem deposition
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Miner extends Thread{

    /** The station where this miner deposits mined gems */
    private Station station;

    /**
     * Constructs a Miner assigned to a specific station.
     * @param station The station where this miner will deposit gems
     */
    public Miner(Station station) {
        this.station = station;
    }

    /**
     * The main execution loop for the miner thread.
     * 1. Waits for the specified mining time
     * 2. Deposits a gem at the assigned station
     * 3. Repeats indefinitely
     *
     */
    @Override
    public void run() {
        while(true) {
            try {
                sleep(Params.MINING_TIME);
            } catch (InterruptedException e) {}
            station.depositGem();
        }
    }
}
