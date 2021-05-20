package tn.esprit.pidev.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.pidev.entities.Reservation;
import tn.esprit.pidev.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationService {

    public ArrayList<Reservation> reservationArrayList;
    public static ReservationService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ReservationService() {
        req = new ConnectionRequest();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public ArrayList<Reservation> parseReservation(String jsonText) { //Parsing Issues with id and date type
        try {
            reservationArrayList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> reservationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) reservationListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reservation reservation = new Reservation();
                reservationArrayList.add(reservation);
            }
        } catch (IOException ex) {
        }
        return reservationArrayList;
    }

    public ArrayList<Reservation> showAll() {
        String url = Statics.BASE_URL + "reservation/api/show"; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reservationArrayList = parseReservation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reservationArrayList;
    }
    public boolean addReservation(Reservation reservation){
        String url = Statics.BASE_URL + "planning/api/add?="; // MUST CHANGE THIS
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    public boolean deleteReservation(int id){
        String url = Statics.BASE_URL  + "reservation/api/delete?id="+id ; // MUST CHANGE THIS
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
