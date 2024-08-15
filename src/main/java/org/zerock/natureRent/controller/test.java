//@PostMapping("/uploadAjax")
//public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {
//
//    List<UploadResultDTO> resultDTOList = new ArrayList<>();
//
//    for (MultipartFile uploadFile : uploadFiles) {
//
//        if (!Objects.requireNonNull(uploadFile.getContentType()).startsWith("image")) {
//            log.warn("This file is not an image type");
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        String originalName = uploadFile.getOriginalFilename();
//        assert originalName != null;
//        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//
//        log.info("fileName: {}", fileName);
//
//        String folderPath = makeFolder();
//        String uuid = UUID.randomUUID().toString();
//
//        // 수정된 경로 설정
//        Path savePath;
//        if (folderPath.startsWith(uploadPath)) {
//            savePath = Paths.get(folderPath, uuid + "_" + fileName);
//        } else {
//            savePath = Paths.get(uploadPath, folderPath, uuid + "_" + fileName);
//        }
//
//        log.info("Saving to: " + savePath.toString());
//
//        try {
//            if (!Files.exists(savePath.getParent())) {
//                Files.createDirectories(savePath.getParent());
//            }
//
//            // 파일 저장
//            uploadFile.transferTo(savePath.toFile());
//
//            // 섬네일 생성
//            String thumbnailSaveName = "s_" + uuid + "_" + fileName;
//            Path thumbnailPath = Paths.get(uploadPath, folderPath, thumbnailSaveName);
//            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailPath.toFile(), 288, 240);
//
//            resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
//
//        } catch (IOException e) {
//            log.error("File upload error: " + e.getMessage(), e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
//}
