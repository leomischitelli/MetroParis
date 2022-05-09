package it.polito.tdp.metroparis;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Model;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class MetroController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> boxArrivo;

    @FXML
    private ComboBox<Fermata> boxPartenza;

    @FXML
    private Button btnCerca;

    @FXML
    private TextArea txtResult;
    
    @FXML
    private TableColumn<Fermata, String> colFermata; //tipo di elemento, come lo stampo

    @FXML
    private TableView<Fermata> tblPercorso;

    @FXML
    void doCerca(ActionEvent event) {
    	
    	Fermata partenza = boxPartenza.getValue();
    	Fermata arrivo = boxArrivo.getValue();
    	if(partenza!=null && arrivo!= null && !partenza.equals(arrivo)) {
    		List<Fermata> percorso = model.calcolaPercorso(partenza, arrivo);
    		
    		tblPercorso.setItems(FXCollections.observableArrayList(percorso)); //observable piace agli items della tabella
    		txtResult.setText("Percorso trovato con " + percorso.size() + " stazioni\n");
    		
    		
    	} else {
    		txtResult.setText("Devi selezionare due stazioni, diverse tra loro!");
    	}

    }

    @FXML
    void initialize() {
        assert boxArrivo != null : "fx:id=\"boxArrivo\" was not injected: check your FXML file 'Metro.fxml'.";
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'Metro.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Metro.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Metro.fxml'.";
        assert colFermata != null : "fx:id=\"colFermata\" was not injected: check your FXML file 'Metro.fxml'.";
        assert tblPercorso != null : "fx:id=\"tblPercorso\" was not injected: check your FXML file 'Metro.fxml'.";
        
        colFermata.setCellValueFactory(new PropertyValueFactory<Fermata, String>("nome")); //attributo di fermata che vogliamo visualizzare
    }
    
	public void setModel(Model m) {
		this.model = m;
		List<Fermata> fermate = model.getFermate();
		boxPartenza.getItems().addAll(fermate);
		boxArrivo.getItems().addAll(fermate);
		
	}

}
