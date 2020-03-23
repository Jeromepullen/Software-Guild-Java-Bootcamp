/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Professional;

/**
 *
 * @author jeromepullenjr
 */
//read file collection object
//---------------------------
public interface DaoProfessional {

    public Professional getProfessional(String type) throws DaoPersistanceException;
}
