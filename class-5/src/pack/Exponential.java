package pack;

public class Exponential extends  Series {
    public Exponential(int inA0, int inProgDiff) throws Series.ArgException{
        super(inA0, inProgDiff);
    }
    public Exponential(String str) throws ArgException {
        super(str);
    }

    int element(int index) throws IndexOutOfBoundsException {
        if(index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if(index == 0) {
            return this.getA0();
        }
        return this.getA0() * (int)(Math.pow(this.getProgDiff(), index));
    }
    int progSum(int index) throws IndexOutOfBoundsException {
        if(index <= 0) {
            throw new IndexOutOfBoundsException();
        }
        if(index == 1) {
            return this.getA0();
        }
        return (int)(( this.getA0() * ((int)(Math.pow(this.getProgDiff(), index))-1)/(this.getProgDiff()-1)));
    }
}

