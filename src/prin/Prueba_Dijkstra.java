package prin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import imp.ConjuntoA;
import imp.GrafosA;
import inter.ConjuntoTDA;
import inter.GrafosTDA;

public class Prueba_Dijkstra {

	public static void MostrarConj(ConjuntoTDA conj) {
		ConjuntoTDA copia = new ConjuntoA();
		copia.InicializarConjunto();
		ConjuntoTDA inter = new ConjuntoA();
		inter.InicializarConjunto();
		
		while (!conj.ConjuntoVacio()) {
			inter.Agregar(conj.Elegir());
			conj.Sacar(conj.Elegir());
		}
		while (!inter.ConjuntoVacio()) {
			conj.Agregar(inter.Elegir());
			copia.Agregar(inter.Elegir());
			inter.Sacar(inter.Elegir());
		}
		System.out.print("[");
		while(!copia.ConjuntoVacio()) {
			System.out.print(copia.Elegir());
			copia.Sacar(copia.Elegir());
			if (!copia.ConjuntoVacio()) {
				System.out.print(" ,");
			}
		}
		System.out.println("]");
	}
	public static ConjuntoTDA vecindario(GrafosTDA G, int v) {
		ConjuntoTDA conj =  G.Vertices();
		ConjuntoTDA vecindario = new ConjuntoA();
		vecindario.InicializarConjunto();
		while(!conj.ConjuntoVacio()) {
			if(G.ExisteArista(v, conj.Elegir()) ||G.ExisteArista(conj.Elegir(),v)) {
				vecindario.Agregar(conj.Elegir());
			}
			conj.Sacar(conj.Elegir());
		}
		return vecindario;
	}
	public static ConjuntoTDA Copiarconjunto(ConjuntoTDA x) {
		
		ConjuntoTDA inter = new ConjuntoA();
		ConjuntoTDA copia = new ConjuntoA();
		inter.InicializarConjunto();
		copia.InicializarConjunto();
		
		while(!x.ConjuntoVacio()) {
			inter.Agregar(x.Elegir());
			x.Sacar(x.Elegir());
		}
		
		while(!inter.ConjuntoVacio()) {
			copia.Agregar(inter.Elegir());
			x.Agregar(inter.Elegir());
			inter.Sacar(inter.Elegir());
		}
		
		return copia;
		
	}
	public static void mostrarGrafo(GrafosTDA G) {
		ConjuntoTDA vertices = G.Vertices();
		int origen;
		int destino;
		while(!vertices.ConjuntoVacio()) {
			origen = vertices.Elegir();
			vertices.Sacar(origen);
			System.out.println("Vertice: " + origen);
			ConjuntoTDA destinos = G.Vertices();
			while(!destinos.ConjuntoVacio()) {
				destino = destinos.Elegir();
				destinos.Sacar(destino);
				if(G.ExisteArista(origen, destino))
					System.out.println("    conectado a " + destino + ", con peso " + G.PesoArista(origen, destino));
			
			}
		}
	}
	public static GrafosTDA Dijkstra (GrafosTDA G, int v) {
		GrafosTDA Dijkstra = new GrafosA();
		Dijkstra.InicializarGrafo();
		
		ConjuntoTDA visitados = new ConjuntoA();
		visitados.InicializarConjunto();
		visitados.Agregar(v);
		
		ConjuntoTDA vert = G.Vertices();
		
		while(!vert.ConjuntoVacio()) {
			Dijkstra.AgregarVertice(vert.Elegir());
			vert.Sacar(vert.Elegir());
		}
		
		vert=vecindario(G, v);
		
		while(!vert.ConjuntoVacio()) {
			Dijkstra.AgregarArista(v, vert.Elegir(), G.PesoArista(v, vert.Elegir()));
			Dijkstra.AgregarArista(vert.Elegir(), v, G.PesoArista(v, vert.Elegir()));
			vert.Sacar(vert.Elegir());
		}
		
		ConjuntoTDA candidatos = G.Vertices();
		candidatos.Sacar(v);
		
		while(!candidatos.ConjuntoVacio()) {
			int min = 10000;
			ConjuntoTDA auxCandidatos= Copiarconjunto(candidatos);
			
			int w=0;
			while(!auxCandidatos.ConjuntoVacio()) {
				if(Dijkstra.ExisteArista(v, auxCandidatos.Elegir()) && Dijkstra.PesoArista(v, auxCandidatos.Elegir())<min) {
					min=Dijkstra.PesoArista(v, auxCandidatos.Elegir());
					w = auxCandidatos.Elegir();
					
				}
				
				auxCandidatos.Sacar(auxCandidatos.Elegir());
			}
			visitados.Agregar(w);
			candidatos.Sacar(w);
			
			auxCandidatos= Copiarconjunto(candidatos);
			while(!auxCandidatos.ConjuntoVacio()) {
				int p = auxCandidatos.Elegir();
				auxCandidatos.Sacar(p);
				if(G.ExisteArista(w, p)) {
					if(Dijkstra.ExisteArista(v, p)) {
						if(Dijkstra.PesoArista(v, w) + G.PesoArista(w, p) < Dijkstra.PesoArista(v, p)) {
							Dijkstra.AgregarArista(v, p, Dijkstra.PesoArista(v, w)+G.PesoArista(w, p));
						}
					} else {
						Dijkstra.AgregarArista(v, p, Dijkstra.PesoArista(v, w)+G.PesoArista(w, p));
					}
				}
			}
			
		}
		
		return Dijkstra;
	}
	
	public static void main(String[] args) {	
		/*
		GrafosTDA grafo = new GrafosA();
		grafo.InicializarGrafo();
		
		for (int x=0;x<58;x++) {
			grafo.AgregarVertice(x);
		}
		
		try {
			File myObj = new File("src/archivos/rutas.txt");
		    Scanner myReader = new Scanner(myObj);
		    while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		    	if(data.contains(",")) {
		    		String[] datos = data.split(",", 5);
		    		System.out.println(datos[0] + " " + datos[1] + " " + datos[2]);
		    		grafo.AgregarArista(Integer.parseInt(datos[0]),Integer.parseInt(datos[1]),Integer.parseInt(datos[2]));
		    	}
		    	
		    }
		    myReader.close();
		    } catch (FileNotFoundException e) {
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		    }
		
		GrafosTDA dij0 = Dijkstra(grafo, 50);
		GrafosTDA dij1 = Dijkstra(grafo, 51);
		GrafosTDA dij2 = Dijkstra(grafo, 52);
		GrafosTDA dij3 = Dijkstra(grafo, 53);
		GrafosTDA dij4 = Dijkstra(grafo, 54);
		GrafosTDA dij5 = Dijkstra(grafo, 55);
		GrafosTDA dij6 = Dijkstra(grafo, 56);
		GrafosTDA dij7 = Dijkstra(grafo, 57);
		mostrarGrafo(dij7);
		*/
		
		int[] costoUnit = new int[8];
		int[] costoFijo = new int[8];
		
		int[] volProduAnual = new int[50];
		
		try {
			File myObj = new File("src/archivos/clientesYCentros.txt");
		    Scanner myReader = new Scanner(myObj);
		    int n = 0;
		    while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		    	if(data.contains(",") && n<10) {
		    		String[] datos = data.split(",", 5);
		    		//System.out.println(datos[0] + " " + datos[1] + " " + datos[2]);
		    		costoUnit[Integer.parseInt(datos[0])] = Integer.parseInt(datos[1]);
		    		costoFijo[Integer.parseInt(datos[0])] = Integer.parseInt(datos[2]);
		    	} else if (data.contains(",") && n>10) {
		    		String[] datos = data.split(",", 3);
		    		//System.out.println(datos[0] + " " + datos[1]);
		    		volProduAnual[Integer.parseInt(datos[0])] = Integer.parseInt(datos[1]);
		    	}
		    	n++;
		    	
		    }
		    myReader.close();
		    } catch (FileNotFoundException e) {
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		    }
		
	}

}
