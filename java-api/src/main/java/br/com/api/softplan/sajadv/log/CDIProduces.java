/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.log;

import javax.ws.rs.Produces;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author lucas
 */
public class CDIProduces {

    @Produces
    public Logger log() {
        return LogManager.getRootLogger();
    }

}
