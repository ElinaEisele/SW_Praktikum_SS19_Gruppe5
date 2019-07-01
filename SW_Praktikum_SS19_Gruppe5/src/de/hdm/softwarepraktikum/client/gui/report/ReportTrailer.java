package de.hdm.softwarepraktikum.client.gui.report;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Group;

/**
 * Diese Klasse stellt die Texte fuer Impressum und Credits mittels
 * HTML-Format dar. Die Inhalte werden in einer DialogBox angezeigt.
 * 
 * @author LeoniFriedrich & CarlaHofmann
 *
 */
public class ReportTrailer extends VerticalPanel{
	
	private ImpressumDialogBox impressumDialogBox;
	private CreditsDialogBox creditsDialogBox;
	private HorizontalPanel buttonsPanel = new HorizontalPanel();
	private Button impressumButton = new Button("Impressum");
	private Button creditsButton = new Button("Credits");
		
	public void onLoad() {
		super.onLoad();
		
		buttonsPanel.add(impressumButton);
		buttonsPanel.add(creditsButton);
		
		this.add(buttonsPanel);
		this.setWidth("100%");
		this.setCellHorizontalAlignment(buttonsPanel, ALIGN_RIGHT);
		
		
		impressumButton.addClickHandler(new ImpressumButtonClickHandler());
		creditsButton.addClickHandler(new CreditsButtonClickHandler());
		
		impressumButton.setStyleName("TrailerButton");
		creditsButton.setStyleName("TrailerButton");
		
	}
	/**
	 * Innere Klasse zur Darstellung des Impressums des
	 * Shared Shoppinglisten Systems mittels HTML-Format.
	 */
	public class Impressum extends HTML{
		
		public Impressum() {
			this.setHTML(("<div class = 'Impressum'>" + "</br>" + "<b>Hochschule der Medien</b>" + "</br>"
							+ "<b>Wirtschaftsinformatik und digitale Medien</b></br>"
							+ "Nobelstra�e 10</br>" + "70563 Stuttgart</br></br>"
							+ "Kontakt</br>Telefon: 0711 8923-3242</br> "
							+ "E-Mail: <a href=\"mailto:info-wi7@hdm-stuttgart.de\" "
							+ "target=\"_top\">info-wi7@hdm-stuttgart.de "
							+ "<br><a href=\"https:\\www.hdm-stuttgart.de\""
							+ "impressum\"TARGET=\"_blank\">Impressum der Hochschule</a></br></br>" 
							+ "</div>"));
		}
		
	}
	
	/**
	 * Innere Klasse zur Darstellung des Impressums des
	 * Shared Shoppinglisten Systems mittels HTML-Format.
	 */
	private class Credits extends HTML{
		
		public Credits() {
			this.setHTML(("<div class = 'Credits'>" + "</br>" + "<b>Special Thanks goes to: "
					+ "</b></br></br> <br> <div>Icons made by <a "
					+ "href=\"https://www.flaticon.com/authors/kiranshastry\" "
					+ "title=\"Kiranshastry\">Kiranshastry</a> from <a "
					+ "href=\"https://www.flaticon.com/\" 			    "
					+ "title=\"Flaticon\">www.flaticon.com</a> is licensed by "
					+ "<a href=\"http://creativecommons.org/licenses/by/3.0/\" 			    "
					+ "title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div></br>"));
		}
	}
	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */
	
	/**
	 * Klasse zur Verarbeitung eines Klicks auf den <code>impressumButton</code>.
	 */
	public class ImpressumButtonClickHandler implements ClickHandler{
		public void onClick(ClickEvent event) {
				impressumDialogBox = new ImpressumDialogBox();
			}
	}
	
	/**
	 * Klasse zur Verarbeitung eines Klicks auf den <code>creditsButton</code>.
	 */
	public class CreditsButtonClickHandler implements ClickHandler{
		public void onClick(ClickEvent event) {
			creditsDialogBox = new CreditsDialogBox();
		}
	}
	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der DialogBox
	 * ***************************************************************************
	 */
	
	/**
	 * Innere Klasse zur Darstellung einer DialogBox mit dem Impressum als Inhalt, 
	 * wenn der Button <code>impressumButton</code> aktiviert wird.
	 *
	 */
	public class ImpressumDialogBox extends DialogBox{
		
		private Button closeButton = new Button("Schlie�en");
		private VerticalPanel vp = new VerticalPanel();
		private Impressum impressum = new Impressum();
		
		public ImpressumDialogBox() {
			
			this.setGlassEnabled(true);
			vp.add(impressum);
			vp.add(closeButton);
			this.add(vp);
			this.center();
			
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ImpressumDialogBox.this.hide();
				}
			});
		}
	}
	
	public class CreditsDialogBox extends DialogBox{
		
		private Button closeButton = new Button("Schlie�en");
		private VerticalPanel vp = new VerticalPanel();
		private Credits credits = new Credits();
		public CreditsDialogBox() {
			
			this.setGlassEnabled(true);
			vp.add(credits);
			vp.add(closeButton);
			this.add(vp);
			this.center();
			
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					CreditsDialogBox.this.hide();
				}
				
			});
			
		}
		
	}

}
