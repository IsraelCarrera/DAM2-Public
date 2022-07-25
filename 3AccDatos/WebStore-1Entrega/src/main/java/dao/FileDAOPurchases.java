package dao;

import configuration.ConfigProperties;
import model.Purchase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDAOPurchases implements DAOPurchases{
    @Override
    public Purchase get(int id) {
        List<Purchase> allPurcharses = getAll();
        Purchase purchase = null;
        for (Purchase p: allPurcharses) {
            if(p.getIdPurchase()==id){
                purchase = p;
            }
        }
        return purchase;
    }

    @Override
    public List<Purchase> getAll() {
        Path file = Paths.get(ConfigProperties.getInstance().getProperty("Purchases"));
        List<String> allPurchases;
        List<Purchase> purchases = null;
        try{
            allPurchases = Files.readAllLines(file);
            purchases = new ArrayList<>();
            for (String s:allPurchases) {
                Purchase p = new Purchase(s);
                purchases.add(p);
            }
        }catch (IOException ex){
            Logger.getLogger("Error IOException in getAll").log(Level.SEVERE,ex.toString());
        }catch (Exception ex){
            Logger.getLogger("Error General in getAll").log(Level.SEVERE,ex.toString());

        }
        return purchases;
    }

    @Override
    public boolean save(Purchase t) {
        boolean saveDone = false;
        Path file = Paths.get(ConfigProperties.getInstance().getProperty("Purchases"));
        try {
            List<Purchase> all = getAll();
            if(all.size() == 0){
                t.setIdPurchase(1);
            }else {
                t.setIdPurchase(all.get(all.size() - 1).getIdPurchase() + 1);
            }
            if(!all.contains(t)) {
                if(all.size() == 0){
                    t.setIdPurchase(1);
                }else {
                    t.setIdPurchase(all.get(all.size() - 1).getIdPurchase() + 1);
                }
                String add =t.toStringTexto() + "\n";
                Files.write(file, add.getBytes(), StandardOpenOption.APPEND);
                saveDone = true;
            }
        }catch (Exception ex){
            Logger.getLogger("Error General in save").log(Level.SEVERE,ex.toString());
        }
        return saveDone;
    }

    @Override
    public void update(Purchase t) {

    }

    @Override
    public boolean delete(Purchase t) {
        boolean deleteDone = false;
        Path file = Paths.get(ConfigProperties.getInstance().getProperty("Purchases"));
        try {
            List<Purchase> all = getAll();
            if(all.contains(t)){
                all.remove(t);
                List<String> add = new ArrayList<>();
                for (Purchase p: all) {
                    add.add(p.toStringTexto());
                }

                Files.write(file, add);
                deleteDone = true;
            }
        }catch (Exception ex){
            Logger.getLogger("Error General in save").log(Level.SEVERE,ex.toString());
        }
        return deleteDone;
    }

    public List<Purchase> checkPurchaseByIdItem(int idItem){
        List<Purchase> purchases=new ArrayList<>();
        for (Purchase p: getAll()){
            if(p.getIdItem()==idItem){
                purchases.add(p);
            }
        }
        if (purchases.isEmpty()){
            purchases=null;
        }
        return purchases;
    }

    public List<Purchase> getPurchasesByDate(LocalDate date){
        List<Purchase> purchases = new ArrayList<>();
        for (Purchase p: getAll()) {
            if(p.getDate().equals(date)){
                purchases.add(p);
            }
        }
        if(purchases.isEmpty()){
            purchases=null;
        }
        return purchases;
    }
}
