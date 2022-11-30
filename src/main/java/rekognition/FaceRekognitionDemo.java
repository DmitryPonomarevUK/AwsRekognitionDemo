package rekognition;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceRekognitionDemo {
    public static String DIR_WITH_FACE_IMAGES_PATH = "C:\\ImageDataBase";
    public static String FACE_COLLECTION_NAME = "demoFaceCollection";

    public static void main(String[] args) {
        RekognitionClient rekClient = AwsRekognitionHelper.createRekognitionClient();

        AwsRekognitionHelper.createFaceCollection(rekClient, FACE_COLLECTION_NAME);

        List<String> imagesFilePaths = FilePathHelper.getImagesFilePaths(DIR_WITH_FACE_IMAGES_PATH);

        Map<String, String> faceIdToImageFilePath = new HashMap<>();

        for(String imagesFilePath : imagesFilePaths) {
            List<String> faceIds =
                    AwsRekognitionHelper.addFacesToCollectionFromImage(rekClient, FACE_COLLECTION_NAME, imagesFilePath);
            for (String faceId : faceIds) {
                faceIdToImageFilePath.put(faceId, imagesFilePath);
            }
        }

        String lookingForAFace = "C:\\Images\\MaskSearchQuery.jpg";

        List<String> foundFaceIds = AwsRekognitionHelper.searchFaceInCollection(
                rekClient,
                FACE_COLLECTION_NAME,
                lookingForAFace,
                10,
                70F);

        System.out.println("When looking for the face: " + lookingForAFace);
        System.out.println("Found these faces in our face data base: ");
        for (String foundFaceId : foundFaceIds) {
            String foundImagePath = faceIdToImageFilePath.get(foundFaceId);
            System.out.println("FaceId : " + foundFaceId + ", Image Path to it: " + foundImagePath);
        }

        //AwsRekognitionHelper.detectImageLabels(rekClient, "C:\\Images\\Cat.jpg");

        //AwsRekognitionHelper.recognizeAllCelebrities(rekClient, "C:\\Images\\MaskSearchQuery.jpg");
        rekClient.close();
    }
}