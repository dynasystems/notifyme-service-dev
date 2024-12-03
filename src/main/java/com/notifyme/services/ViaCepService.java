package com.notifyme.services;

import com.notifyme.dto.EnderecoDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ViaCepService {
    public List<EnderecoDto> getCepByEndereco(String estado, String cidade, String endereco) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = montaURL(estado, cidade, endereco);

        List<EnderecoDto> enderecosDto = new ArrayList<>();
        try {
            ResponseEntity<List<EnderecoDto>> listEnderecoRest = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<EnderecoDto>>() {
                    });

            enderecosDto = listEnderecoRest.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return enderecosDto;

    }
    public ResponseEntity<EnderecoDto> getEnderecoByCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = "http://viacep.com.br/ws/{cep}/json/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("cep", cep);
        EnderecoDto enderecoDTO = restTemplate.getForObject(uri, EnderecoDto.class, params);
        return new ResponseEntity<EnderecoDto>(enderecoDTO, HttpStatus.OK);
    }

    public  String montaURL(String estado,String cidade,String endereco) {
        String cidadeNormalize  = "";
        String enderecoNormalize = "";
        if(!cidade.isEmpty()) {
            cidadeNormalize = Normalizer.normalize(cidade, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
        if(!endereco.isEmpty()) {
            enderecoNormalize = Normalizer.normalize(endereco, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }

        String url = "https://viacep.com.br/ws/"+estado+"/"+cidadeNormalize+"/"+enderecoNormalize+"/json/";
        return url;
    }
}

