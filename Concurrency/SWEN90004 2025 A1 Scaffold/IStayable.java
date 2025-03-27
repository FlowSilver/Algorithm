public interface IStayable {
    Cart loadCart();

    void deliverCart(Cart arrivedCart);

    void iWait();

    void iNotify();

    void iNotifyAll();
}
