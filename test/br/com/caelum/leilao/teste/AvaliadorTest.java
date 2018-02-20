package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;


public class AvaliadorTest {	
	
	//testando o commit pelo eclipse.
	
	//testando o commit pelo eclipse 2222222222.
    private final double DELTA_DOUBLE_JUNIT = 0.00001; 
    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;
    
    
    @Before
    public void criarObjetosTestes() {
        this.leiloeiro = new Avaliador();
        
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");

    }
    
    @Test(expected = RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
        //parte 1: cenário
        Leilao leilao = new CriadorDeLeilao()
                        .para("Playstation 3 Novo")
                        .construir();   
        
        leiloeiro.avalia(leilao);
    }
    
    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
      //parte 1: cenário
        Leilao leilao = new CriadorDeLeilao()
                        .para("Playstation 3 Novo")
                        .lance(joao, 250.0)
                        .lance(jose, 300.0)
                        .lance(maria, 400.0)
                        .construir();
        
        //parte 2: ação
        leiloeiro.avalia(leilao);
        
        //parte 3: validação
        //JUnit test
//      assertEquals(400.0, leiloeiro.getMaiorLance(), DELTA_DOUBLE_JUNIT);
//      assertEquals(250.0, leiloeiro.getMenorLance(), DELTA_DOUBLE_JUNIT);
        
        //Hamcrest test
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
    }
    
    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        //parte 1: cenário
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 1000.0)
                .construir();        
        
        //parte 2: ação
        leiloeiro.avalia(leilao);
        
        //parte 3: validação
          //JUnit test
//        assertEquals(1000.0, leiloeiro.getMaiorLance(), DELTA_DOUBLE_JUNIT);
//        assertEquals(1000.0, leiloeiro.getMenorLance(), DELTA_DOUBLE_JUNIT);

        //Hamcrest test
        assertThat(leiloeiro.getMaiorLance(), equalTo(1000.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(1000.0));
       
    }
    
    @Test
    public void deveEncontrarOsTresMaioresLances() {
        //parte 1: cenário
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(maria, 400.0)
                .construir();
        
        //parte 2: ação
        leiloeiro.avalia(leilao);
        
        List<Lance> maiores = leiloeiro.getTresMaiores();
        
        //parte 3: validação
        //JUnit teste
        assertEquals(3, maiores.size());
//        assertEquals(400.0, maiores.get(0).getValor(), DELTA_DOUBLE_JUNIT);
//        assertEquals(300.0, maiores.get(1).getValor(), DELTA_DOUBLE_JUNIT);
//        assertEquals(200.0, maiores.get(2).getValor(), DELTA_DOUBLE_JUNIT);
        
        //Hamcrest Test
        assertThat(maiores, hasItems(
                                        new Lance (maria, 400),
                                        new Lance (joao, 300),
                                        new Lance (maria, 200)));
        
       
    }
    
 }
