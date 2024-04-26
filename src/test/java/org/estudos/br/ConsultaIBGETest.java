package org.estudos.br;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

public class ConsultaIBGETest {
 
    //teste padrão do professor
    // @Test
    // @DisplayName("Teste para consulta única de um estado")
    // public void testConsultarEstado() throws IOException {
    //     // Arrange
    //     String uf = "SP"; // Define o estado a ser consultado

    //     // Act
    //     String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado

    //     // Assert
    //     // Verifica se a resposta não está vazia
    //     assert !resposta.isEmpty();

    //     // Verifica se o status code é 200 (OK)
    //     HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
    //     int statusCode = connection.getResponseCode();
    //     assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    // }
    
    @Mock
    private HttpURLConnection connectionMock;


    private static final String JSON_MOCK_ESTADOS_RO = "{\"id\":11,\"sigla\":\"RO\",\"nome\":\"Rondônia\",\"regiao\":{\"id\":1,\"sigla\":\"N\",\"nome\":\"Norte\"}}";

    @ParameterizedTest
    @CsvSource({"SP, SP", "AM, AM", "RN, RN", "SC, SC", "MT, MT"})
    @DisplayName("Teste de estado parametrizado com um estado de cada região")
    public void testConsultarEstadoParametrizado(String estadoSelecionado, String estadoEsperado) throws IOException {
        String resposta = ConsultaIBGE.consultarEstado(estadoSelecionado); // Chama o método utilizado para teste
        JSONObject jsonResposta = new JSONObject(resposta); // Convertendo a resposta para JSON para facilitar a comparação

        assertEquals(estadoEsperado, jsonResposta.getString("sigla"), "A sigla do estado não corresponde com a resposta esperada.");
    
    }

    @ParameterizedTest
    @ValueSource(strings = {"PR", "RS", "SC"})
    @DisplayName("Teste de consulta de estados (passando como valores, os estados do Sul)")
    public void testConsultarEstado(String estado) throws IOException {
        String response = ConsultaIBGE.consultarEstado(estado);
        JSONObject jsonResponse = new JSONObject(response);
        assertEquals(estado, jsonResponse.getString("sigla"));
    }
    
    @Test
    @DisplayName("Teste Mockado de consulta de Estado: ")
    public void testMockConsultarEstadoPernambuco() throws IOException {
        String siglaEstado = "RO";

        String response = ConsultaIBGE.consultarEstado(siglaEstado);

        assertEquals(JSON_MOCK_ESTADOS_RO, response, "Há divergencia no body da response");
    }

}