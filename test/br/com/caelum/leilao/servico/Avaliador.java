package br.com.caelum.leilao.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {
    
    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private List<Lance> maiores;
    
    public void avalia(Leilao leilao) {
        
        if (leilao.getLances().size() == 0) {
            throw new RuntimeException("N�o � poss�vel realizar um leil�o sem lances.");
        }
        
        for (Lance lance : leilao.getLances()) {
            if (lance.getValor() > maiorDeTodos ) {
                maiorDeTodos = lance.getValor();
            } 
            if (lance.getValor() < menorDeTodos) {
                menorDeTodos = lance.getValor(); 
            }
        }
        
        maiores = new ArrayList<Lance>(leilao.getLances());
        Collections.sort(maiores, new Comparator<Lance>() {

            public int compare(Lance arg0, Lance arg1) {
                if (arg0.getValor() < arg1.getValor()) return 1;
                if (arg0.getValor() > arg1.getValor()) return -1;
                return 0;
            }
        });
        
        
        maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
        
    }
    
    public List<Lance> getTresMaiores() {
        return maiores;
    }
    
    public double getMaiorLance() {
        return maiorDeTodos;
    }
    
    public double getMenorLance() {
        return menorDeTodos;
    }    
}
