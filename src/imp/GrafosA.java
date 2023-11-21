package imp;

import inter.ConjuntoTDA;
import inter.GrafosTDA;

public class GrafosA implements GrafosTDA {
	int maximo = 60;
	int [][] mAdy;
	int [] etiquetas;
	int cantNodos;

	public void InicializarGrafo(){
		mAdy = new int [maximo][maximo];
		etiquetas = new int [maximo];
		cantNodos = 0;
	}

	@Override
	public void AgregarVertice(int v) {
		etiquetas[cantNodos] = v;
		for(int i = 0; i <= cantNodos; i++) {
			mAdy[cantNodos][i] = 0;
			mAdy[i][cantNodos] = 0;
		}
		cantNodos++;
	}

	@Override
	public void EliminarVertice(int v) {
		int inx = vertice2Indice(v);
		etiquetas[inx] = etiquetas[cantNodos];
		for(int i = 0; i < cantNodos; i++)
			mAdy[inx][i] = mAdy[cantNodos][i];
		for(int i =0; i < cantNodos; i++)
			mAdy[i][inx] = mAdy[i][cantNodos];
		cantNodos--;
	}

	@Override
	public ConjuntoTDA Vertices() {
		ConjuntoTDA c = new ConjuntoA();
		c.InicializarConjunto();
		for(int i =0; i < cantNodos; i++)
			c.Agregar(etiquetas[i]);
		return c;
	}

	@Override
	public void AgregarArista(int v1, int v2, int peso) {
		int origen = vertice2Indice(v1);
		int destino = vertice2Indice(v2);
		mAdy[origen][destino] = peso;
	}

	@Override
	public void EliminarArista(int v1, int v2) {
		int origen = vertice2Indice(v1);
		int destino = vertice2Indice(v2);
		mAdy[origen][destino] = 0;
	}

	@Override
	public boolean ExisteArista(int v1, int v2) {
		int origen = vertice2Indice(v1);
		int destino = vertice2Indice(v2);
		return mAdy[origen][destino] != 0;
	}

	@Override
	public int PesoArista(int v1, int v2) {
		int origen = vertice2Indice(v1);
		int destino = vertice2Indice(v2);
		return mAdy[origen][destino];
	}
	
	private int vertice2Indice(int v) {
		int i = cantNodos;
		while(i >= 0 && etiquetas[i] != v)
			i--;
		return i;		
	}

}
