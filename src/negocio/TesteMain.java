package negocio;

import exceptions.ClienteCPFInvalidoException;
import exceptions.ClienteDuplicadoException;
import negocio.beans.Cliente;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class TesteMain {
    public static void main(String[] args) {
        ControladorCliente controladorCliente = new ControladorCliente();
        Cliente clienteTeste = new Cliente();
        clienteTeste.setUid(1);
        clienteTeste.setNome("Steffano Pereira");
        clienteTeste.setEmail("steffanoxpereira@gmail.com");
        clienteTeste.setCpf("94927417093");
        clienteTeste.setTelefone("(81)3301-4735");
        clienteTeste.setEndereco("Rua Aracatu, 448");
        clienteTeste.setDataNascimento(LocalDate.of(2020,4,1));
        String senha = "Minha Senha";

        try {
            controladorCliente.cadastrarCliente(clienteTeste, senha);
        } catch (ClienteDuplicadoException e) {
            System.out.println("Cliente Cadastrado");
        } catch (ClienteCPFInvalidoException e) {
            System.out.println("Cliente com CPF Inválido");
        }

        for (Cliente cliente : controladorCliente.getRepoCliente().listar()) {
            System.out.println("-----------------");
            System.out.println(cliente.getNome());
            System.out.println(cliente.getEmail());
            System.out.println(cliente.getSenha());
        }
    }
}
