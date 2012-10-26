package arida.ufc.br.moap.core.beans;

import java.sql.Timestamp;


public class Interval<T> implements Comparable<Interval> {

	private T begin;
	private T end;
	
	public Interval(T arriveT, T leaveT) {
		super();
		this.begin = arriveT;
		this.end = leaveT;
	}

	public T getBegin() {
		return begin;
	}

	public void setBegin(T arriveT) {
		this.begin = arriveT;
	}

	public T getEnd() {
		return end;
	}

	public void setEnd(T leaveT) {
		this.end = leaveT;
	}

	@Override
	public int compareTo(Interval o) {
		// TODO Auto-generated method stub
		return 0;
	}
        
        public void getDuration(){
           
        }
	
	@Override
	public String toString(){
		return "["+begin+","+end+"]";
	}
	
	
}
