package IModel;

public interface Subjet {
	
	public void registerObserver(Observer observer);
	public void removeObserver(Observer observer);
	public void notifyObserver();
	
}
