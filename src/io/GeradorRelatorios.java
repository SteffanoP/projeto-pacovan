package io;

import io.models.RelatorioCliente;
import io.models.RelatorioEmpregado;

public interface GeradorRelatorios {

    void gerarRelatorioCliente(RelatorioCliente relatorioCliente);

    void gerarRelatiorioEmpregado(RelatorioEmpregado relatorioEmpregado);
}
