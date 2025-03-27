/**
 * The station class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Station implements IStayable {

    Cart cart = null;

    private int id;

    private boolean hasGem = false;

    public Station(int id) {
        this.id = id;
    }

    public synchronized void depositGem() {

        // wait for station to be empty
        while (hasGem) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // mines and deposit gem
        hasGem = true;
        notifyAll();
    }

    private synchronized void loadGem() {
        // wait for gem
        while (!hasGem) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // Get gem
        this.cart.gems++;
        hasGem = false;

        // Print statement
        System.out.println(this.cart + " loaded with a gem");
    }

    @Override
    public synchronized void iWait() {
        try {
            wait();
        }
        catch (InterruptedException e) {}
    }

    @Override
    public synchronized void iNotify() {
        notify();
    }

    @Override
    public synchronized void iNotifyAll() {
        notifyAll();
    }


    @Override
    public synchronized Cart loadCart() {
        // wait for cart
        while (cart == null) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // load a gem
        loadGem();

        // Cart moves
        Cart removedCart = this.cart;
        this.cart = null;
        notifyAll();

        return removedCart;
    }

    @Override
    public synchronized void deliverCart(Cart arrivedCart) {
        // Wait for station to be empty
        while (cart != null) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // Cart arrives
        this.cart = arrivedCart;
        notifyAll();
    }

    @Override
    public String toString() {
        return "station " + id;
    }
}
