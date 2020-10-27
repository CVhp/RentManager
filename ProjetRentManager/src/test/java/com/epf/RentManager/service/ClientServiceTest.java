package com.epf.RentManager.service;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;
import org.junit.Test;

import java.sql.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ClientServiceTest {
    ClientService clientService = ClientService.getInstance(true);

    @Test
    public void testCreate(){
        Client c = new Client();
        c.setNom("nom");
        c.setPrenom("prenom");
        c.setEmail("babar@epf.fr");
        c.setNaissance(Date.valueOf("2000-01-01"));

        try{
            clientService.create(c);
        }catch (ServiceException e){
            fail();
        }
    }

    @Test (expected = ServiceException.class)
    public  void testCreateFailDate() throws ServiceException{
        Client c = new Client();
        c.setNom("nom");
        c.setPrenom("prenom");
        c.setEmail("babar@epf.fr");
        c.setNaissance(Date.valueOf("2011-12-12"));
        clientService.create(c);
    }

    @Test (expected = ServiceException.class)
    public  void testCreateFailName() throws ServiceException{
        Client c = new Client();
        c.setNom("");
        c.setPrenom("prenom");
        c.setEmail("babar@epf.fr");
        c.setNaissance(Date.valueOf("2000-01-01"));
        clientService.create(c);
    }

    @Test (expected = ServiceException.class)
    public  void testCreateFailNickname() throws ServiceException{
        Client c = new Client();
        c.setNom("nom");
        c.setPrenom("");
        c.setEmail("babar@epf.fr");
        c.setNaissance(Date.valueOf("2000-01-01"));
        clientService.create(c);
    }

    @Test (expected = ServiceException.class)
    public  void testCreateFailEmail() throws ServiceException{
        Client c = new Client();
        c.setNom("nom");
        c.setPrenom("prenom");
        c.setEmail("truc");
        c.setNaissance(Date.valueOf("2000-01-01"));
        clientService.create(c);
    }

    @Test
    public void testDelete(){
        Client c = new Client();
        c.setNom("nom");
        c.setPrenom("prenom");
        c.setEmail("babar@epf.fr");
        c.setNaissance(Date.valueOf("2000-01-01"));

        try{
            clientService.delete(c);
        }catch (ServiceException e){
            e.printStackTrace();
            fail();
        }
    }


}
