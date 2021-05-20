package tn.esprit.pidev.views;

import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.pidev.entities.Reservation;
import tn.esprit.pidev.services.ReservationService;

import java.util.ArrayList;
import java.util.Collections;

public class ReservationForm extends Form {
    Form current;
    ArrayList<Reservation> reservationArrayList = new ArrayList<>();
    ReservationService reservationService = new ReservationService();
    public ReservationForm(){
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("My Reservations");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */
        reservationArrayList = reservationService.showAll();
        Collections.reverse(reservationArrayList);
        fillData();
        /* *** *SEARCHBAR* *** */
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);

                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Trier par destination", null, (evt) -> {
            removeAll();
            Collections.sort(reservationArrayList, Reservation.statusComparator);
            fillData();
        });
    }

    public void fillData() {
        for (Reservation reservation : reservationArrayList) {
             MultiButton multiButton = new MultiButton();
            multiButton.setTextLine1(reservation.getIdRandonnee()+"");
            multiButton.setTextLine2(reservation.getStatus());
            multiButton.setTextLine3("Date: "+reservation.getDate());
            multiButton.setUIID(reservation.getId() + "");
            multiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, "", 10.0f));
            multiButton.addActionListener(l -> new ReservationDetailForm(current, reservation).show());
            add(multiButton);
        }
    }

    }

