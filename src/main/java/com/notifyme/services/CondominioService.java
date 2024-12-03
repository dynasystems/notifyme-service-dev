package com.notifyme.services;

import com.notifyme.dto.condominio.CondominioResponseDto;
import com.notifyme.dto.condominio.CondominioRequest;
import com.notifyme.dto.condominio.CondominioResponse;
import com.notifyme.exception.PerfilException;
import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.UsuarioCondominio;
import com.notifyme.persistence.enumated.CondominioStatusEnum;
import com.notifyme.repository.CondominioRepository;
import com.notifyme.repository.PerfilCondominioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CondominioService {

    Logger logger = LogManager.getLogger(CondominioService.class);

    @Autowired
    private CondominioRepository repository;
//
//    @Autowired
//    private PerfilService perfilService;

    @Autowired
    private PerfilCondominioRepository perfilCondominioRepository;

    public Condominio findById (String id) {
        return repository.findById(UUID.fromString(id)).orElseThrow(() -> new PerfilException("Condoninio não encontrado"));
    }

    public Page<Condominio> findByFilter (final CondominioResponseDto filter, Pageable pageable) {
        return repository.findAll(CondominioRepository.Specs.byFilter(filter), pageable);
    }

//    public CondominioResponse newCondominio (CondominioRequest dto, JwtAuthenticationToken token) {
//        //pegar usuario logado
//        var perfilId = perfilService.findById(token.getName());
//
//        var condominio = new Condominio();
//        condominio.setNome(dto.condominioNome());
//        condominio.setCnpj(dto.condominioCnpj());
//        condominio.setTelefone(dto.condominioTelefone());
//        condominio.setEmail(dto.condominioEmail());
//        condominio.setCep(dto.condominioCep());
//        condominio.setLogradouro(dto.condominioLogradouro());
//        condominio.setNumero(dto.condominioNumero());
//        condominio.setComplemento(dto.condominioComplemento());
//        condominio.setBairro(dto.condominioBairro());
//        condominio.setCidade(dto.condominioCidade());
//        condominio.setEstado(dto.condominioEstado());
//        condominio.setStatus(CondominioStatusEnum.ATIVO);
//        condominio.setDataCadastro(LocalDate.now());
//        condominio.setDataAlteracao(LocalDate.now());
//        var condominioSalvo = repository.save(condominio);
//
//        logger.info("PerfilId " + perfilId.getId());
//
//        UsuarioCondominio pfCondominio = new UsuarioCondominio();
//        pfCondominio.setUsuario(perfilId);
//        pfCondominio.setCondominio(condominioSalvo);
//
//        logger.info("pfCondominio " + pfCondominio.toString());
//
//        var perfilCondominio = perfilCondominioRepository.save(pfCondominio);
//
//        return new CondominioResponse(condominio.getId().toString(),
//                                      condominioSalvo.getNome(),
//                                      condominioSalvo.getCnpj(),
//                                      condominioSalvo.getTelefone(),
//                                      condominioSalvo.getEmail(),
//                                      condominioSalvo.getCep(),
//                                      condominioSalvo.getLogradouro(),
//                                      condominioSalvo.getNumero(),
//                                      condominioSalvo.getComplemento(),
//                                      condominioSalvo.getBairro(),
//                                      condominioSalvo.getCidade(),
//                                      condominioSalvo.getEstado(),
//                                      condominioSalvo.getStatus().name());
//
//    }


    public void delete (String id) {
        repository.deleteById(UUID.fromString(id));
    }

    public CondominioResponseDto toCondominioDto(Condominio entity) {
        CondominioResponseDto dto = new CondominioResponseDto();
        dto.setCondominioId(entity.getId().toString());
        dto.setCondominioNome(entity.getNome());
        dto.setCondominioCnpj(entity.getCnpj());
        dto.setCondominioTelefone(entity.getTelefone());
        dto.setCondominioEmail(entity.getEmail());
        dto.setCondominioCep(entity.getCep());
        dto.setCondominioLogradouro(entity.getLogradouro());
        dto.setCondominioNumero(entity.getNumero());
        dto.setCondominioComplemento(entity.getComplemento());
        dto.setCondominioBairro(entity.getBairro());
        dto.setCondominioCidade(entity.getCidade());
        dto.setCondominioEstado(entity.getEstado());
        dto.setCondominioAtivo(entity.getStatus().name());
        dto.setComdominioDataCadastro(entity.getDataCadastro());
        dto.setCondominioDataAlteracao(entity.getDataAlteracao());
        return dto;
    }

}
