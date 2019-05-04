package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.dto.response.ApiResponse;
import br.com.londrisoft.uniqueweb.model.entity.common.Arquivo;
import br.com.londrisoft.uniqueweb.model.entity.common.Documento;
import br.com.londrisoft.uniqueweb.repository.ArquivoRepository;
import br.com.londrisoft.uniqueweb.repository.DocumentoRepository;
import br.com.londrisoft.uniqueweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoEndpoint {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @GetMapping
    public ResponseEntity<?> listar() {
        AcessoDTO acesso = usuarioService.acesso();
        // Alerta que não foi selecionada nenhuma empresa antes de realizar o envio do documento
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false,"Não há empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        List<Documento> documentos = documentoRepository.findByEmpresaId(acesso.getEmpresa().getId());
        return ResponseEntity.ok()
                .body(new ApiResponse(documentos));
    }

    @PostMapping
    public ResponseEntity<?> enviar(@RequestBody Documento request) {
        AcessoDTO acesso = usuarioService.acesso();
        // Alerta que não foi selecionada nenhuma empresa antes de realizar o envio do documento
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false,"Não há empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        Arquivo arquivo = new Arquivo();
        arquivo.setEmpresaId(acesso.getEmpresa().getId());
        arquivo.setConteudo(Base64.getDecoder().decode(request.getConteudo()));

        arquivo = arquivoRepository.save(arquivo);
        request.setArquivoId(arquivo.getId());
        request.setEmpresaId(acesso.getEmpresa().getId());

        Documento documento = documentoRepository.save(request);
        documento.setConteudo(null);
        return new ResponseEntity<>(new ApiResponse("Arquivo enviado com sucesso.", documento), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false,"Não há empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        Documento documento = documentoRepository.findById(id).orElse(null);
        if (documento == null || !documento.getEmpresaId().equals(acesso.getEmpresa().getId())) {
            return new ResponseEntity<>(new ApiResponse(false, "Arquivo não encontrado."), HttpStatus.NOT_FOUND);
        }

        arquivoRepository.deleteById(documento.getArquivoId());
        documentoRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Arquivo removido com sucesso."), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable Long id) {
        Documento documento = documentoRepository.findById(id).orElse(null);
        if (documento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Atualizar quantidade de Downloads
        Integer quantidadeDownload = documento.getQuantidadeDownload();
        documento.setQuantidadeDownload(quantidadeDownload +1);
        documentoRepository.save(documento);

        // Buscar o conteúdo do arquivo
        Arquivo arquivo = arquivoRepository.findById(documento.getArquivoId()).orElse(null);
        byte[] data = arquivo != null ? arquivo.getConteudo() : null;

        String[] split = documento.getArquivoNome().split("\\.");
        String extensao = split[split.length -1].toUpperCase()  ;
        String contentType = "";
        String contentDisposition = "filename=\"" + documento.getArquivoNome() + "\"";

        switch (extensao) {
            case "JPG":
                contentType = "image/jpeg";
                break;
            case "PNG":
                contentType = "image/png";
                break;
            case "PDF":
                contentType = "application/pdf";
                break;
            default:
                contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .header("Content-Disposition", contentDisposition)
                .body(data);
    }

}
