/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author lucas
 */
class DateAdapter extends XmlAdapter<String, Date> {

    @Override
    public Date unmarshal(String valor) throws Exception {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(valor);
    }

    @Override
    public String marshal(Date valor) throws Exception {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(valor);
    }

}
