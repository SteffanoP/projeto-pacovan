package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.PessoaInexistenteException;
import negocio.beans.Bens;
import negocio.beans.Cliente;
import negocio.beans.Movimentacao;

import java.time.LocalDate;
import java.util.*;

public class ControladorBENS {
    private Repositorio<Bens> repoBENS;

    public ControladorBENS() {
        this.repoBENS = new RepositorioCRUD<>();
    }

    public void inserirBens(Bens bens) {

    }

    public Map<LocalDate,Bens> listarBensEmpresa() {
        NavigableMap<LocalDate, Bens> mapaBens = new TreeMap<>();
        List<Bens> benEmpresaList = repoBENS.listar();
            for( Bens ben : benEmpresaList){
               mapaBens.put(ben.getDataCadastro(), ben);
            }
        return mapaBens;
    }

    public Map<LocalDate,Bens> listarBensCliente(long uidCliente) throws PessoaInexistenteException {
        NavigableMap<LocalDate, Bens> mapaBensCliente = new TreeMap<>();
        boolean benClienteExiste = false;
        List<Bens> benClienteList = repoBENS.listar();

        for(Bens ben : benClienteList){
            if(ben.getCliente().getUid() == uidCliente) {

                benClienteExiste = true;
                mapaBensCliente.put(ben.getDataCadastro(), ben);
            }

        }if(!benClienteExiste)  throw new PessoaInexistenteException("Cliente Não existe!");
            return mapaBensCliente;
}


    public  Map<LocalDate,Bens> listarBensPendentes(long uidCliente) throws PessoaInexistenteException{

        NavigableMap<LocalDate, Bens> mapaBensPendentes = new TreeMap<>();
        boolean pendente = false;
        List<Bens> pendenteList = repoBENS.listar();

        for(Bens ben : pendenteList){
            if(ben.getCliente().getUid() == uidCliente && ben.isPendente()) {

                pendente = true;

                mapaBensPendentes.put(ben.getDataCadastro(), ben);
            }
        }
        if(!pendente)  throw new PessoaInexistenteException("Cliente Não existe!");

        return mapaBensPendentes;
    }

    public  Map<LocalDate,Bens> listarBensAprovados(long uidCliente) throws PessoaInexistenteException{
        NavigableMap<LocalDate, Bens> mapaBensaprovados = new TreeMap<>();
        boolean aprovado = false;
        List<Bens> aproveList = repoBENS.listar();

        for(Bens ben : aproveList){
            if(ben.getCliente().getUid() == uidCliente && (!ben.isPendente())) {
              aprovado = true;

              mapaBensaprovados.put(ben.getDataCadastro(), ben);
            }
        }if(!aprovado)  throw new PessoaInexistenteException("Cliente Não existe!");

        return mapaBensaprovados;
    }

    public Map<LocalDate,Bens> listarBensGarantia (long uidCliente) throws PessoaInexistenteException{
        NavigableMap<LocalDate, Bens> mapaBensGarantia = new TreeMap<>();
        boolean garantia = false;
        List<Bens> garantiaList = repoBENS.listar();

            for(Bens ben: garantiaList){
                if(ben.getCliente().getUid() == uidCliente && ben.isGarantia()){
                    garantia = true;
                    mapaBensGarantia.put(ben.getDataCadastro(), ben);
                }
            }if(!garantia)  throw new PessoaInexistenteException ("Cliente Não existe!");

        return mapaBensGarantia;
    }

    public double calcularValorBensCliente(long uidCliente) throws PessoaInexistenteException{
        double valor = 0;
        boolean existevalor  = false;
        List<Bens> valorBenList = repoBENS.listar();
        for(Bens ben: valorBenList) {
            if (ben.getCliente().getUid() == uidCliente){
                existevalor = true;
                valor = ben.getValor() ;
            }
        }if(!existevalor)  throw new PessoaInexistenteException("Cliente Não existe!");

        return valor;
    }

    public void alterarBens(Bens bens) {

    }

    public void removerBens(Bens bens) {

    }
}
