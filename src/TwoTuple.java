import java.io.Serializable;

public class TwoTuple<A,B> implements Serializable {
    public final A first; //First.
    public final B second; // Second.
    //Tuple constructor
    public TwoTuple(A a, B b)
    {
        this.first = a;
        this.second = b;
    }

    /** <b>Constructor for tuples that contains a and b generic objects</b>
     * @return Returns a and b.
     */
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
