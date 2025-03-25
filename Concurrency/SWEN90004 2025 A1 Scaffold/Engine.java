/**
 * The engine class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Engine extends Thread{
    private IStayable startStation;
    private IStayable endStation;
    private boolean atStart = true;
    private Cart carriedCart;
    public Engine(IStayable startStation, IStayable endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
    }

    private void travel() {
        // travel
        try {
            sleep(Params.ENGINE_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        atStart = !atStart;
    }
    @Override
    public void run() {
        while(true) {
            if(atStart) {
                carriedCart = startStation.getCart();
            }
            else {
                carriedCart = endStation.getCart();
            }

            travel();

            if(atStart) {
                startStation.cartArrived(carriedCart);
            }
            else {
                endStation.cartArrived(carriedCart);
            }
            carriedCart = null;
        }
    }
}
