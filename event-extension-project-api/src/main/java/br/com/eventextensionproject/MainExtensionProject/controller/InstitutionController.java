package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.dto.InstitutionDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Institution;
import br.com.eventextensionproject.MainExtensionProject.entity.record.InstitutionResponse;
import br.com.eventextensionproject.MainExtensionProject.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/institution")
@CrossOrigin(value = "*")
public class InstitutionController {

    @Autowired
    private InstitutionService service;

    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<InstitutionResponse> result = service.getAll().stream().map(institution -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/institution/image/")
                    .path(Long.toString(institution.getIdIntitution()))
                    .toUriString();

            return new InstitutionResponse(
                    institution.getIdIntitution(),
                    institution.getNameInstitution(),
                    institution.getCnpjInstitution(),
                    institution.getSituation(),
                    institution.getNameImage(),
                    fileDownloadUri,
                    institution.getTypeImage());
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Institution institution = service.findById(id);
        String type = institution.getTypeImage();
        String[] parts = type.split("/");
        String extension = "";
        if (parts.length > 1) {
            extension = parts[1];
        }

        File file = new File(FILE_DIRECTORY + id + '-' + institution.getNameInstitution() + '.' + extension);
        byte[] imagemBytes = new byte[0];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            imagemBytes = new byte[(int) file.length()];
            inputStream.read(imagemBytes);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + institution.getNameImage() + "\"")
                .body(imagemBytes);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(
            @RequestParam("nameInstitution") String nameInstitution,
            @RequestParam("cnpjInstitution") String cnpjInstitution,
            @RequestParam("imageInstitution") MultipartFile imageInstitution) throws IOException {

        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setNameInstitution(nameInstitution);
        institutionDTO.setCnpjInstitution(cnpjInstitution);
        institutionDTO.setImageInstitution(imageInstitution);

        Institution institution = service.save(institutionDTO);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/institution/image/")
                .path(Long.toString(institution.getIdIntitution()))
                .toUriString();

        InstitutionResponse result = new InstitutionResponse(
                institution.getIdIntitution(),
                institution.getNameInstitution(),
                institution.getCnpjInstitution(),
                institution.getSituation(),
                institution.getNameImage(),
                fileDownloadUri,
                institution.getTypeImage());

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idInstitution}")
    public ResponseEntity<Object> delete(@PathVariable Long idInstitution) {
        HashMap<String, Object> result = service.delete(idInstitution);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idInstitution}")
    public ResponseEntity<Object> findById(@PathVariable Long idInstitution) {
        Institution institution = service.findById(idInstitution);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/institution/image/")
                .path(Long.toString(institution.getIdIntitution()))
                .toUriString();

        InstitutionResponse result = new InstitutionResponse(
                institution.getIdIntitution(),
                institution.getNameInstitution(),
                institution.getCnpjInstitution(),
                institution.getSituation(),
                institution.getNameImage(),
                fileDownloadUri,
                institution.getTypeImage());

        return ResponseEntity.ok().body(result);
    }


    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(
            @RequestParam("idIntitution") Long idIntitution,
            @RequestParam("nameInstitution") String nameInstitution,
            @RequestParam("cnpjInstitution") String cnpjInstitution,
            @RequestParam("imageInstitution") MultipartFile imageInstitution) throws IOException {

        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setIdIntitution(idIntitution);
        institutionDTO.setNameInstitution(nameInstitution);
        institutionDTO.setCnpjInstitution(cnpjInstitution);
        institutionDTO.setImageInstitution(imageInstitution);

        Institution institution = service.update(institutionDTO);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/institution/image/")
                .path(Long.toString(institution.getIdIntitution()))
                .toUriString();

        InstitutionResponse result = new InstitutionResponse(
                institution.getIdIntitution(),
                institution.getNameInstitution(),
                institution.getCnpjInstitution(),
                institution.getSituation(),
                institution.getNameImage(),
                fileDownloadUri,
                institution.getTypeImage());

        return ResponseEntity.ok().body(result);
    }

}
