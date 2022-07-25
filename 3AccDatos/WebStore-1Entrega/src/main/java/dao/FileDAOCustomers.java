package dao;

import configuration.ConfigProperties;
import model.Customer;
import model.Customers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDAOCustomers implements DAOCustomers{

    @Override
    public Customer get(int id) {
        Customer customer = null;
        for (Customer c: getAll()) {
            if(c.getIdCustomer()==id){
                customer = c;
            }
        }
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> listCustomer = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Customers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Path xmlFile= Paths.get(ConfigProperties.getInstance().getProperty("Customers"));
            Customers customers = (Customers) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
            listCustomer = customers.getCustomer();
        } catch (JAXBException e) {
            Logger.getLogger("Error in JAXBContext").log(Level.SEVERE,e.toString());
        }catch( IOException e){
            Logger.getLogger("Error in newInputStream").log(Level.SEVERE,e.toString());
        }
        return listCustomer;
    }

    @Override
    public boolean save(Customer t) {
        boolean exist=false;
        boolean bool=false;
        try {
            JAXBContext context = JAXBContext.newInstance(Customers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Path xmlFile= Paths.get(ConfigProperties.getInstance().getProperty("Customers"));
            Customers customers = (Customers) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
            for (Customer c: customers.getCustomer()) {
                if (c.equals(t)){
                    exist=true;
                }
            }
            if(!exist) {
                //Cuando he creado al customer, lo creo sin ID, y se lo añado ahora, cogiendo el último ID del elemento de la lista y sumandole 1.
                if(customers.getCustomer().size()==0){
                    t.setIdCustomer(1);
                }else {
                    t.setIdCustomer(customers.getCustomer().get(customers.getCustomer().size() - 1).getIdCustomer() + 1);
                }
                customers.getCustomer().add(t);
                marshaller.marshal(customers, Files.newOutputStream(xmlFile));
                bool = true;
            }
        } catch (JAXBException e) {
            Logger.getLogger("Error in JAXBContext").log(Level.SEVERE,e.toString());
        }catch( IOException e){
            Logger.getLogger("Error in newInputStream").log(Level.SEVERE,e.toString());
        }catch(Exception e){
            Logger.getLogger("Error in Exception(General)").log(Level.WARNING,e.toString());

        }
        return bool;
    }

    @Override
    public boolean update(Customer t) {
        return true;

    }

    @Override
    public boolean delete(Customer t) {
        boolean exist=false;
        boolean bool=false;
        try {
            JAXBContext context = JAXBContext.newInstance(Customers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Path xmlFile= Paths.get(ConfigProperties.getInstance().getProperty("Customers"));
            Customers customers = (Customers) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
            for (Customer c: customers.getCustomer()) {
                if (c.equals(t)){
                    exist=true;
                }
            }
            if(exist) {
                //Ahora lo eliminamos.
                customers.getCustomer().remove(t);
                marshaller.marshal(customers, Files.newOutputStream(xmlFile));
                bool = true;
            }
        } catch (JAXBException e) {
            Logger.getLogger("Error in JAXBContext").log(Level.SEVERE,e.toString());
        }catch( IOException e){
            Logger.getLogger("Error in newInputStream").log(Level.SEVERE,e.toString());
        }catch(Exception e){
            Logger.getLogger("Error in Exception(General)").log(Level.WARNING,e.toString());

        }
        return bool;
    }
    

}
