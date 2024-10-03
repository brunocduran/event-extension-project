package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.dto.BannerDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Banner;
import br.com.eventextensionproject.MainExtensionProject.entity.record.BannerResponse;
import br.com.eventextensionproject.MainExtensionProject.service.BannerService;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/banner")
@CrossOrigin(value = "*")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<BannerResponse> result = bannerService.getAll().stream().map(banner -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/banner/image/")
                    .path(Long.toString(banner.getIdBanner()))
                    .toUriString();

            return new BannerResponse(
                    banner.getIdBanner(),
                    banner.getNameBanner(),
                    banner.getMessageBanner(),
                    banner.getInitialDateBanner(),
                    banner.getFinalDateBanner(),
                    banner.getNameImage(),
                    fileDownloadUri,
                    banner.getTypeImage(),
                    banner.getEvent(),
                    banner.getSituation());
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listActive")
    public ResponseEntity<Object> getAllActive() {
        List<BannerResponse> result = bannerService.findAllActive().stream().map(banner -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/banner/image/")
                    .path(Long.toString(banner.getIdBanner()))
                    .toUriString();

            return new BannerResponse(
                    banner.getIdBanner(),
                    banner.getNameBanner(),
                    banner.getMessageBanner(),
                    banner.getInitialDateBanner(),
                    banner.getFinalDateBanner(),
                    banner.getNameImage(),
                    fileDownloadUri,
                    banner.getTypeImage(),
                    banner.getEvent(),
                    banner.getSituation());
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Banner banner = bannerService.findById(id);
        String type = banner.getTypeImage();
        String[] parts = type.split("/");
        String extension = "";
        if (parts.length > 1) {
            extension = parts[1];
        }

        File file = new File(FILE_DIRECTORY + id + '-' + banner.getNameBanner() + '.' + extension);
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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + banner.getNameImage() + "\"")
                .body(imagemBytes);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(
            @RequestParam("nameBanner") String nameBanner,
            @RequestParam("messageBanner") String messageBanner,
            @RequestParam("initialDateBanner") LocalDate initialDateBanner,
            @RequestParam("finalDateBanner") LocalDate finalDateBanner,
            @RequestParam("imageBanner") MultipartFile imageBanner,
            @RequestParam("idEvent") Long idEvent) throws IOException {

        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setNameBanner(nameBanner);
        bannerDTO.setMessageBanner(messageBanner);
        bannerDTO.setInitialDateBanner(initialDateBanner);
        bannerDTO.setFinalDateBanner(finalDateBanner);
        bannerDTO.setImageBanner(imageBanner);
        bannerDTO.setIdEvent(idEvent);

        Banner banner = bannerService.save(bannerDTO);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/banner/image/")
                .path(Long.toString(banner.getIdBanner()))
                .toUriString();

        BannerResponse result = new BannerResponse(
                banner.getIdBanner(),
                banner.getNameBanner(),
                banner.getMessageBanner(),
                banner.getInitialDateBanner(),
                banner.getFinalDateBanner(),
                banner.getNameImage(),
                fileDownloadUri,
                banner.getTypeImage(),
                banner.getEvent(),
                banner.getSituation());

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idBanner}")
    public ResponseEntity<Object> delete(@PathVariable Long idBanner) {
        HashMap<String, Object> result = bannerService.delete(idBanner);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idBanner}")
    public ResponseEntity<Object> findById(@PathVariable Long idBanner) {
        Banner banner = bannerService.findById(idBanner);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/banner/image/")
                .path(Long.toString(banner.getIdBanner()))
                .toUriString();

        BannerResponse result = new BannerResponse(
                banner.getIdBanner(),
                banner.getNameBanner(),
                banner.getMessageBanner(),
                banner.getInitialDateBanner(),
                banner.getFinalDateBanner(),
                banner.getNameImage(),
                fileDownloadUri,
                banner.getTypeImage(),
                banner.getEvent(),
                banner.getSituation());

        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(
            @RequestParam("idBanner") Long idBanner,
            @RequestParam("nameBanner") String nameBanner,
            @RequestParam("messageBanner") String messageBanner,
            @RequestParam("initialDateBanner") LocalDate initialDateBanner,
            @RequestParam("finalDateBanner") LocalDate finalDateBanner,
            @RequestParam("imageBanner") MultipartFile imageBanner,
            @RequestParam("idEvent") Long idEvent) throws IOException {

        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setIdBanner(idBanner);
        bannerDTO.setNameBanner(nameBanner);
        bannerDTO.setMessageBanner(messageBanner);
        bannerDTO.setInitialDateBanner(initialDateBanner);
        bannerDTO.setFinalDateBanner(finalDateBanner);
        bannerDTO.setImageBanner(imageBanner);
        bannerDTO.setIdEvent(idEvent);

        Banner banner = bannerService.update(bannerDTO);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/banner/image/")
                .path(Long.toString(banner.getIdBanner()))
                .toUriString();

        BannerResponse result = new BannerResponse(
                banner.getIdBanner(),
                banner.getNameBanner(),
                banner.getMessageBanner(),
                banner.getInitialDateBanner(),
                banner.getFinalDateBanner(),
                banner.getNameImage(),
                fileDownloadUri,
                banner.getTypeImage(),
                banner.getEvent(),
                banner.getSituation());

        return ResponseEntity.ok().body(result);
    }
}
