package fr.greta92.banqueappfx;


import java.util.Optional;

import fr.greta92.banqueappfx.model.Banque;
import fr.greta92.banqueappfx.model.Compte;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

public class MainController {
//	@FXML
//	ListView<String> compteListe;
	@FXML
	ListView<Compte> compteListe;
	@FXML
	SplitPane splitPane;
	@FXML
	AnchorPane leftPane;
	@FXML
	TextField numeroCompteTF;
	@FXML
	TextField titulaireTF;
	@FXML
	TextField soldeTF;
	@FXML
	public void supprimerCompte(ActionEvent event) {
		System.out.println(event);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("confirmer la suppresion ?");
		Optional<ButtonType> resultat = alert.showAndWait();
		//si resultat n'est pas vide
		if(!resultat.isEmpty()) {
			//on reupere le type de bouton
			ButtonType buttonType = resultat.get();
			if(buttonType == ButtonType.OK) {
				int selectedIndex = 
						compteListe.getSelectionModel().getSelectedIndex();
				compteListe.getItems().remove(selectedIndex);
				compteListe.refresh();
			}
		}
	}
	@FXML
	public void modifierCompte(ActionEvent event) {
		System.out.println(event);
		Compte compte = 
				compteListe.getSelectionModel().getSelectedItem();
		compte.setTitulaire(titulaireTF.getText());
		compte.setSolde(Double.valueOf(soldeTF.getText()));
		compteListe.refresh();
	}
	
	
	@FXML
	public void initialize() {
		//initialiser les widgets
		Banque b = new Banque();
		b.ouvrirCompte("titulaire 1", 1000);
		b.ouvrirCompte("titulaire 2", 2000);
		b.ouvrirCompte("titulaire 3", 3000);
		
//		compteListe.getItems().addAll("java", "Python", "C++");
		compteListe.setItems(b.getComptes());
		//ecouter pour les changments sur la liste des elements selectionnée
		compteListe.getSelectionModel()
					.getSelectedItems()
					.addListener(new ListSelectionListener());
		//désactiver le champ numeroCompte (en lecture seule)
		numeroCompteTF.setEditable(false);
		numeroCompteTF.setDisable(true);
		
		leftPane.maxWidthProperty()
			.bind(splitPane.widthProperty().multiply(0.3));
		leftPane.minWidthProperty()
			.bind(splitPane.widthProperty().multiply(0.3));	
	}
	
	class ListSelectionListener implements ListChangeListener<Compte>{

		@Override
		public void onChanged(Change<? extends Compte> change) {
			System.out.println(change.getList());
			ObservableList<? extends Compte> list = change.getList();
			if(list.isEmpty()) {
				numeroCompteTF.setText("");
				soldeTF.setText("");
				titulaireTF.setText("");
			}
			else {
				Compte compte = list.get(0);
				titulaireTF.setText(compte.getTitulaire());
				soldeTF.setText(""+compte.getSolde());
				numeroCompteTF.setText(""+compte.getNumeroCompte());
			}
		}
		
	}
	
	
}
