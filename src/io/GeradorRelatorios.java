package io;

import io.models.RelatorioCliente;
import io.models.RelatorioEmpregado;

public interface GeradorRelatorios {

    void gerarRelatorioCliente(String path);

    void gerarRelatorioEmpregado(String path);
}
