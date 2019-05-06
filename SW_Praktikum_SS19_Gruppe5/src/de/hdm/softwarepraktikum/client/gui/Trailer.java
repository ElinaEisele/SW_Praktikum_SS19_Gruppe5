package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Diese Klasse stellt die Texte für Impressum und Credits mittels
 * HTML-Format dar. Die Inhalte werden in einer DialogBox angezeigt.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Trailer extends VerticalPanel{
	
	private ImpressumDialogBox impressumDialogBox;
	private CreditsDialogBox creditsDialogBox;
	private Label applicationName = new Label("Maul Tasche");
	private HorizontalPanel buttonsPanel = new HorizontalPanel();
	private Button impressumButton = new Button("Impressum");
	private Button creditsButton = new Button("Credits");
		
	public void onLoad() {
		super.onLoad();
		
		this.add(applicationName);
		buttonsPanel.add(impressumButton);
		buttonsPanel.add(creditsButton);
		this.add(buttonsPanel);
		this.setWidth("100%");
		this.setCellHorizontalAlignment(applicationName, ALIGN_CENTER);
		this.setCellHorizontalAlignment(buttonsPanel, ALIGN_CENTER);
		
		/**
		 * Anonyme innere Klasse zur Verarbeitung eines Klicks auf den <code>impressumButton</code>.
		 */
		impressumButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				impressumDialogBox = new ImpressumDialogBox();
			}
		});
		
		/**
		 * Anonyme innere Klasse zur Verarbeitung eines Klicks auf den <code>creditsButton</code>.
		 */
		creditsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				creditsDialogBox = new CreditsDialogBox();
			}
		});
		
	}
	
	/**
	 * Innere Klasse zur Darstellung einer DialogBox mit dem Impressum als Inhalt, 
	 * wenn der Button <code>impressumButton</code> aktiviert wird.
	 *
	 */
	public class ImpressumDialogBox extends DialogBox{
		
		private VerticalPanel vp = new VerticalPanel();
		private HTML impressum = new HTML("Hier steht das Impressum.");
		
		public ImpressumDialogBox() {
			
			this.setGlassEnabled(true);
			vp.add(impressum);
			this.add(vp);
			this.center();
		}
	}
	
	public class CreditsDialogBox extends DialogBox{
		
		private VerticalPanel vp = new VerticalPanel();
		private HTML credits = new HTML("Hier stehen die Credits.");
		
		public CreditsDialogBox() {
			
			this.setGlassEnabled(true);
			vp.add(credits);
			this.add(vp);
			this.center();
		}
		
	}

}
