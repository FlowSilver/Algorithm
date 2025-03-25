/**
 * The miner class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Miner extends Thread{
    private Station station;
    public Miner(Station station) {
        this.station = station;
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep(Params.MINING_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            station.depositGem();
        }
    }
}
