package negocio;

import java.time.LocalDate;

import exceptions.ObjetoDuplicadoException;
import negocio.beans.Cliente;
import negocio.beans.Proposta;

public class TesteListaMain {
    public static void main(String[] args) throws ObjetoDuplicadoException {
        Cliente c = new Cliente();

        c.setUid(1);

        Proposta p = new Proposta();
        Proposta p1 = new Proposta();
        Proposta p2 = new Proposta();
        Proposta p3 = new Proposta();
        Proposta p4 = new Proposta();

        p.setCliente(c);
        p1.setCliente(c);
        p2.setCliente(c);
        p3.setCliente(c);
        p4.setCliente(c);

        p.setData(LocalDate.of(2021, 5, 1));
        p1.setData(LocalDate.of(2021, 1, 7));
        p2.setData(LocalDate.of(2021, 3, 27));
        p3.setData(LocalDate.of(2021, 6, 14));
        p4.setData(LocalDate.of(2021, 4, 24));

        ControladorProposta cp = new ControladorProposta();

        cp.criarProposta(p);
        cp.criarProposta(p1);
        cp.criarProposta(p2);
        cp.criarProposta(p3);
        cp.criarProposta(p4);

        System.out.println(cp.listarPropostasCliente(1));
    }
}
