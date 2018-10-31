package com.app.modules.google;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("iamge")
public class GoogleImageController {
	// todo 重写上传图片with Google Cloud Storage的jar包
//	String bucket = "jtytest-190309.appspot.com";
//    // [START gcs]
//    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
//            .initialRetryDelayMillis(10)
//            .retryMaxAttempts(10)
//            .totalRetryPeriodMillis(15000)
//            .build());
//    // [END gcs]
//
//
//    @RequestMapping("/upload")
//    public void upload(HttpServletResponse  resp) throws Exception {
//        //[START original_image]
//        // Read the image.jpg resource into a ByteBuffer.
//        FileInputStream fileInputStream = new FileInputStream(new File("WEB-INF/image.jpg"));
//        FileChannel fileChannel = fileInputStream.getChannel();
//        ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
//        fileChannel.read(byteBuffer);
//
//        byte[] imageBytes = byteBuffer.array();
//
//        // Write the original image to Cloud Storage
//        gcsService.createOrReplace(
//                new GcsFilename(bucket, "image.jpeg"),
//                new GcsFileOptions.Builder().mimeType("image/jpeg").build(),
//                ByteBuffer.wrap(imageBytes));
//        //[END original_image]
//
//        //[START resize]
//        // Get an instance of the imagesService we can use to transform images.
//        ImagesService imagesService = ImagesServiceFactory.getImagesService();
//
//        // Make an image directly from a byte array, and transform it.
//        Image image = ImagesServiceFactory.makeImage(imageBytes);
//        Transform resize = ImagesServiceFactory.makeResize(100, 50);
//        Image resizedImage = imagesService.applyTransform(resize, image);
//
//        // Write the transformed image back to a Cloud Storage object.
//        gcsService.createOrReplace(
//                new GcsFilename(bucket, "resizedImage.jpeg"),
//                new GcsFileOptions.Builder().mimeType("image/jpeg").build(),
//                ByteBuffer.wrap(resizedImage.getImageData()));
//        //[END resize]
//
//        //[START rotate]
//        // Make an image from a Cloud Storage object, and transform it.
//        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
//        BlobKey blobKey = blobstoreService.createGsBlobKey("/gs/" + bucket + "/image.jpeg");
//        Image blobImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
//        Transform rotate = ImagesServiceFactory.makeRotate(90);
//        Image rotatedImage = imagesService.applyTransform(rotate, blobImage);
//
//        // Write the transformed image back to a Cloud Storage object.
//        gcsService.createOrReplace(
//                new GcsFilename(bucket, "rotatedImage.jpeg"),
//                new GcsFileOptions.Builder().mimeType("image/jpeg").build(),
//                ByteBuffer.wrap(rotatedImage.getImageData()));
//        //[END rotate]
//
//        // [START servingUrl]
//        // Create a fixed dedicated URL that points to the GCS hosted file
//        ServingUrlOptions options = ServingUrlOptions.Builder
//                .withGoogleStorageFileName("/gs/" + bucket + "/image.jpeg")
//                .imageSize(150)
//                .crop(true)
//                .secureUrl(true);
//        String url = imagesService.getServingUrl(options);
//        // [END servingUrl]
//
//        // Output some simple HTML to display the images we wrote to Cloud Storage
//        // in the browser.
//        PrintWriter out = resp.getWriter();
//        out.println("<html><body>\n");
//        out.println("<img src='//storage.cloud.google.com/" + bucket
//                + "/image.jpeg' alt='AppEngine logo' />");
//        out.println("<img src='//storage.cloud.google.com/" + bucket
//                + "/resizedImage.jpeg' alt='AppEngine logo resized' />");
//        out.println("<img src='//storage.cloud.google.com/" + bucket
//                + "/rotatedImage.jpeg' alt='AppEngine logo rotated' />");
//        out.println("<img src='" + url + "' alt='Hosted logo' />");
//        out.println("</body></html>\n");
//    }
}
