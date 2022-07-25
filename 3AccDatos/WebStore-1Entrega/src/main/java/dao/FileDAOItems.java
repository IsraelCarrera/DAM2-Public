package dao;

import configuration.ConfigProperties;
import model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDAOItems implements DAOItems{

    @Override
    public Item get(int id) {
        Item item = null;
        for (Item i: getAll()) {
            if(i.getIdItem()==id){
                item=i;
            }
        }
        return item;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        File fichero = new File(ConfigProperties.getInstance().getProperty("Items"));
        try{
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            String aux;
            while((aux = br.readLine()) != null){
                items.add(new Item(aux));
            }
            br.close();
        }catch (IOException ex){
            Logger.getLogger("Error General in getAllItem").log(Level.SEVERE,ex.toString());
        }
        return items;
    }

    @Override
    public void save(Item t) {
        File fichero = new File(ConfigProperties.getInstance().getProperty("Items"));
        //Comprobar si el item a guardar existe.
        try (FileWriter writer = new FileWriter(fichero, true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(t.toStringTextFile() + "\n");
        } catch (IOException e) {
            Logger.getLogger("Error General in saveItem").log(Level.SEVERE,e.toString());
        }
    }
    @Override
    public void update(Item t) {
        //Está mal, vaya, hay que sobreescribir.
        List<Item> items = this.getAll();
    }
    @Override
    public boolean delete(Item t) {
        List<Item> items = this.getAll();
        if(items.contains(t)){
            items.remove(t);
            File fichero = new File(ConfigProperties.getInstance().getProperty("Items"));
            try (FileWriter writer = new FileWriter(fichero, false);
                BufferedWriter bw = new BufferedWriter(writer)){
                for (Item i: items) {
                    bw.write(i.toStringTextFile() + "\n");
                }
                return true;
            }catch(IOException e){
                Logger.getLogger("Error General in deleteItem").log(Level.SEVERE,e.toString());
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        boolean haveId=false;
        Item t = null;
        List<Item> items= this.getAll();
        for(Item i: items){
            if(i.getIdItem() == id){
                haveId=true;
                t=i;
            }
        }
        if(haveId){
            items.remove(t);
            File fichero = new File(ConfigProperties.getInstance().getProperty("Items"));
            try (FileWriter writer = new FileWriter(fichero, false);
                 BufferedWriter bw = new BufferedWriter(writer)){
                for (Item i: items) {
                    bw.write(i.toStringTextFile() + "\n");
                }
                return true;
            }catch(IOException e){
                Logger.getLogger("Error General in deleteItem").log(Level.SEVERE,e.toString());
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean existId(int id){
        boolean have=false;
        List<Item> items = this.getAll();
        for (Item i : items) {
            if (i.getIdItem() == id) {
                have=true;
            }
        }
        return have;
    }

}