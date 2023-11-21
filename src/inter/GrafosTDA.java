package inter;

public interface GrafosTDA {
	void InicializarGrafo ();       // No hay precondiciones
	void AgregarVertice(int v);      // Grafo inicializado, v ausente 
	void EliminarVertice (int v);    //Grafo inicializado, v presente
	ConjuntoTDA Vertices();           // Grafo inicializado 
	void AgregarArista(int v1, int v2, int peso); //Grafo inicializado, v1, v2 presentes
	void EliminarArista(int v1, int v2);    //Grafo inicializado, v1, v2 presentes
	boolean ExisteArista(int v1, int v2);  //Grafo inicializado, v1, v2 presentes
	int PesoArista( int v1, int v2);   //Grafo inicializado, v1, v2 presentes
}
