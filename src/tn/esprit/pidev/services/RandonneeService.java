package tn.esprit.pidev.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.pidev.entities.Randonnee;
import tn.esprit.pidev.entities.Reservation;
import tn.esprit.pidev.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandonneeService {

    public ArrayList<Randonnee> randonneeArrayList;
    public static RandonneeService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public RandonneeService() {
        req = new ConnectionRequest();
    }

    public static RandonneeService getInstance() {
        if (instance == null) {
            instance = new RandonneeService();
        }
        return instance;
    }

    public ArrayList<Randonnee> parseRandonnee(String jsonText) { //Parsing Issues with id and date type
        try {
            randonneeArrayList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> randonneeListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) randonneeListJson.get("root");
            for (Map<String, Object> obj : list) {
                Randonnee reservation = new Randonnee();
                randonneeArrayList.add(reservation);
            }
        } catch (IOException ex) {
        }
        return randonneeArrayList;
    }

    public ArrayList<Randonnee> showAll() {
        String url = Statics.BASE_URL + "randonnee/api/show"; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                randonneeArrayList = parseRandonnee(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return randonneeArrayList;
    }
}
