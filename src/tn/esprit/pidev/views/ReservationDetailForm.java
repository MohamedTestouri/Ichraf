package tn.esprit.pidev.views;

import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.plaf.UIManager;
import tn.esprit.pidev.entities.Reservation;

public class ReservationDetailForm extends Form {
Form current;

    public ReservationDetailForm(Form previous, Reservation reservation) {
        Label dateLabel = new Label("Date: "+reservation.getDate());
        Label statusLabel = new Label("Status: "+reservation.getStatus());
        Label placeLabel = new Label("Nombre place: "+reservation.getPlaces());
        Button cancelButton = new Button("Cancel Reservation");
        cancelButton.addActionListener(evt -> {
            // reservation action
        });
        addAll(dateLabel, statusLabel, placeLabel,cancelButton);
        /* *** *BACK BUTTON* *** */
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Share", FontImage.createMaterial(FontImage.MATERIAL_SHARE, UIManager.getInstance().getComponentStyle("TitleCommand")), (evt) -> {
            //SENDING EMAIL
            Display.getInstance().sendMessage(new String[]{""}, "Do Not forget!", new Message("Do: " + reservation.getIdRandonnee() + " it's on: " + reservation.getDate()));
        });
    }
}
