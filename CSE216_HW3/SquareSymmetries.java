import java.util.Collection;

public class SquareSymmetries implements Symmetries<Square>{
    public SquareSymmetries(){
        super();
    }

    public boolean areSymmetric(Square s1, Square s2){
        return true;
    }

    public Collection<Square> symmetriesOf(Square s){
        return null;
    }
}
