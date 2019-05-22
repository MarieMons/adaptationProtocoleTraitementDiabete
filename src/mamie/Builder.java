package mamie;

import java.util.List;
import java.util.Stack;

public abstract class Builder<T> {
	
	protected T result;
	protected Stack<String> parenthesage = new Stack<String>();
	
	public T getResult() {
		return this.result;
	}

	public abstract void build(List<String> lignes, Protocole protocole) throws Exception;
	
}
