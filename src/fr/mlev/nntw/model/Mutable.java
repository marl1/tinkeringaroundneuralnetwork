package fr.mlev.nntw.model;

public interface Mutable {
	public void mutate();
	public void remember();
	public void forget();
}
