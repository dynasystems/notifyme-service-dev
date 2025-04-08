package com.notifyme.delegate;

import com.notifyme.controller.ApiUtil;
import com.notifyme.controller.UsuarioApiDelegate;
import com.notifyme.mapper.UsuarioMapper;
import com.notifyme.mapper.UsuariosResponseMapper;
import com.notifyme.model.*;
import com.notifyme.models.PageableMapper;
import com.notifyme.models.UsuarioQueryParams;
import com.notifyme.persistence.Usuario;
import com.notifyme.security.TokenService;
import com.notifyme.services.UsuarioActivationService;
import com.notifyme.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioApiDelegateImpl implements UsuarioApiDelegate {

    private final UsuarioService usuarioService;
    private final UsuarioActivationService usuarioActivationService;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<Void> postUsuarioV1(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario newUsuario = UsuarioMapper.INSTANCE.convert(usuarioRequestDTO);
        usuarioService.novoUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> putUsuarioV1(Integer id, UpdateUsuarioRequestDTO updateUsuarioRequestDTO) {
        usuarioService.updateUsuario(id, updateUsuarioRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Void> deleteUsuario(Integer id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<Void> postUsuarioValidaTokenV1(PostUsuarioValidaTokenV1Request postUsuarioValidaTokenV1Request) {
          usuarioActivationService.validaToken(postUsuarioValidaTokenV1Request.getToken());
          return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> postUsuarioRedefinirSenhaV1(PostUsuarioRedefinirSenhaV1Request postUsuarioRedefinirSenhaV1Request) {
        usuarioService.redefinirSenha(postUsuarioRedefinirSenhaV1Request);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<Void> postUsuarioNovaSenhaV1(Integer id, NovaSenhaRequestDTO novaSenhaRequestDTO) {
        usuarioService.novaSenha(id, novaSenhaRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<UsuariosResponseWrapper> getCondominioV1(String authorization, Integer pageSize, Integer pageNumber, String nome,
                                                                    String cpf, String telefone, String email, StatusUsuario status) {

        Integer condomionioId = tokenService.extractCondominioIdFromToken(authorization);

        final UsuarioQueryParams usuarioQueryParams = UsuarioQueryParams.builder()
                .condominioId(1)
                .nome(nome)
                .cpf(cpf)
                .telefone(telefone)
                .email(email)
                .status(status)
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .build();

        final Page<Usuario> allBySpec = usuarioService.findByAllPaged(usuarioQueryParams);
        final UsuariosResponseWrapper usuariosResponseWrapper = buildUsuarioResponseInfoResponse(allBySpec);
        return  ResponseEntity.ok(usuariosResponseWrapper);
    }

    private UsuariosResponseWrapper buildUsuarioResponseInfoResponse(Page<Usuario> pageUsuarios) {
        UsuariosInfoListResponse  usuariosInfoListResponse = new UsuariosInfoListResponse();
        usuariosInfoListResponse.setSort(PageableMapper.buildSortDTO(pageUsuarios.getSort()));
        usuariosInfoListResponse.setUsuarios(UsuariosResponseMapper.INSTANCE.convert(pageUsuarios.getContent()));
        usuariosInfoListResponse.setNumberOfElements(pageUsuarios.getNumberOfElements());
        usuariosInfoListResponse.setPageNumber(pageUsuarios.getNumber());
        usuariosInfoListResponse.setPageSize(pageUsuarios.getSize());
        usuariosInfoListResponse.setTotalPages(pageUsuarios.getTotalPages());
        usuariosInfoListResponse.setTotalElements(pageUsuarios.getTotalElements());
        usuariosInfoListResponse.setPageable(PageableMapper.buildPageableDTO(pageUsuarios.getPageable()));
        return new UsuariosResponseWrapper().data(usuariosInfoListResponse);
    }
}
