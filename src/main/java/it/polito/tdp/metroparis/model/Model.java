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


