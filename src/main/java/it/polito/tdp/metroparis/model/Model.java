 package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	
	private List<Fermata> fermate;
	private Graph <Fermata, DefaultEdge> grafo;
	private Map<Integer, Fermata> fermateIdMap;
	
	
	public List<Fermata> getFermate(){
		if(this.fermate == null) {
			
			MetroDAO dao = new MetroDAO();
			this.fermate = dao.getAllFermate();
		}
		this.fermateIdMap = new HashMap<Integer, Fermata>();
		for(Fermata f : this.fermate) {
			fermateIdMap.put(f.getIdFermata(), f);
		}
		
		return this.fermate;
	}
	
	public List<Fermata> calcolaPercorso(Fermata partenza, Fermata arrivo) {
		creaGrafo();
		Map<Fermata, Fermata> alberoInverso = visitaGrafo(partenza);
		
		return null;
	}
	
/*	public void creaGrafo() {
		this.grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		MetroDAO dao = new MetroDAO();
		List<Fermata> fermate = dao.getAllFermate();
		Map<Integer, Fermata> fermateIdMap = new HashMap<Integer, Fermata>();
		for(Fermata f : fermate) {
			fermateIdMap.put(f.getIdFermata(), f);
		}
		Graphs.addAllVertices(this.grafo, fermate);
		
		//metodo 1: itero su ogni coppia di vertici
//		for(Fermata partenza : fermate) {
//			for(Fermata arrivo : fermate) {
//				if(dao.isFermataConnessaFermata(partenza, arrivo)) {
//					this.grafo.addEdge(partenza, arrivo);
//				}
//			}
//		}
		
		//metodo 2: dato ciascun vertice, trovo i vertici ad esso adiacenti
		//posso iterare su fermate oppure su this.grafo.vertexSet()
//		for(Fermata partenza : fermate) {
//			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza);
//			for(Integer id : idConnesse) {
//		//		Fermata arrivo = (fermata che possiede questo "id");
//				Fermata arrivo = null;
//				for(Fermata f : fermate) {
//					if(f.getIdFermata() == id) {
//						arrivo = f;
//						break;
//					}
//				}
//				this.grafo.addEdge(partenza, arrivo);
//			}
//		}
		
		
//		metodo 2b: il DAO restituisce un elenco di oggetti fermata
		
		
		for(Fermata partenza: fermate) {
			List<Fermata> arrivi = dao.getFermateConnesse(partenza);
			for(Fermata arrivo : arrivi) {
				this.grafo.addEdge(partenza, arrivo);
			}
		}
		
		
		//metodo 2c: il DAO restituisce id numerici che converto in oggetti tramte una map<Integer, Fermata> (identity map)
		for(Fermata partenza : fermate) {
			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza);
			for(int id : idConnesse) {
				Fermata arrivo = fermateIdMap.get(id);
				this.grafo.addEdge(partenza, arrivo);
			}
		} 
		
		//metodo 3: faccio una sola query che restituisce le coppie di fermate da collegare (preferisco 3c con identity map
		List<CoppiaId> fermateDaCollegare = dao.getAllFermateConnesse();
		for(CoppiaId coppia : fermateDaCollegare) {
			this.grafo.addEdge(fermateIdMap.get(coppia.getIdPartenza()),
					fermateIdMap.get(coppia.getIdArrivo()));
		}
		
		System.out.println(this.grafo);
		System.out.println("Vertici: " + this.grafo.vertexSet().size());
		System.out.println("Archi: " + this.grafo.edgeSet().size());

		visitaGrafo(fermate.get(0));
	}  */
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(this.grafo, getFermate());
		MetroDAO dao = new MetroDAO();
		List<CoppiaId> fermateDaCollegare = dao.getAllFermateConnesse();
		for(CoppiaId coppia : fermateDaCollegare) {
			this.grafo.addEdge(fermateIdMap.get(coppia.getIdPartenza()), fermateIdMap.get(coppia.getIdArrivo()));
		}
		
		

	}
	
	
	
	
	public Map<Fermata, Fermata> visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata, DefaultEdge> visita = new BreadthFirstIterator<>(this.grafo, partenza); //grafo e vertice di partenza
		Map<Fermata, Fermata> alberoInverso = new HashMap<>();
		alberoInverso.put(partenza, null); // non esiste predecessore
		
		visita.addTraversalListener(new RegistraAlberoDiVisita(alberoInverso, grafo));
		while(visita.hasNext()) {
			Fermata f = visita.next();
			System.out.println(f);
		}
		
		List<Fermata> percorso = new ArrayList<>();
//		fermata = arrivo;
//		while(fermata!=null)
//			fermata = alberoInverso.get(fermata);
	}


}

