package negocio;

import java.time.LocalDate;

import exceptions.ClienteInexistenteException;
import exceptions.ObjetoDuplicadoException;
import negocio.beans.Cliente;
import negocio.beans.Proposta;

public class TesteListaMain {
    public static void main(String[] args) throws ObjetoDuplicadoException, ClienteInexistenteException {
        Cliente c = new Cliente();
        Cliente c2 = new Cliente();

        c.setUid(1);
        c2.setUid(2);

        Proposta p = new Proposta();
        Proposta p1 = new Proposta();
        Proposta p2 = new Proposta();
        Proposta p3 = new Proposta();
        Proposta p4 = new Proposta();

        p.setCliente(c2);
        p1.setCliente(c);
        p2.setCliente(c2);
        p3.setCliente(c);
        p4.setCliente(c);

        p.setData(LocalDate.of(2021, 5, 1));
        p1.setData(LocalDate.of(2021, 1, 7));
        p2.setData(LocalDate.of(2021, 3, 27));
        p3.setData(LocalDate.of(2021, 6, 14));
        p4.setData(LocalDate.of(2021, 4, 24));

        p2.setContraproposta(true);
        p4.setContraproposta(true);

        ControladorProposta cp = new ControladorProposta();

        cp.criarProposta(p);
        cp.criarProposta(p1);
        cp.criarProposta(p2);
        cp.criarProposta(p3);
        cp.criarProposta(p4);

        System.out.println(cp.listarPropostasCliente(1) + "\n");
        System.out.println(cp.listarContraPropostas(1) + "\n");
        System.out.println(cp.listarPropostasCliente(2) + "\n");
        System.out.println(cp.listarContraPropostas(2) + "\n");
        System.out.println(cp.listarPropostasCliente(12));
    }
}
