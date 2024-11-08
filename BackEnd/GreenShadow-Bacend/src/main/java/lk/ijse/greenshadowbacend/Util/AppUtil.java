package lk.ijse.greenshadowbacend.Util;

import java.util.UUID;

public class AppUtil {
    public static String generateUserId() {
        return "USER-" + UUID.randomUUID();
    }
    public static String generateStaffId() {
        return "STAFF-" + UUID.randomUUID();
    }
    public static String generateFieldId() {
        return "FIELD-" + UUID.randomUUID();
    }
    public static String generateCropId() {
        return "CROP-" + UUID.randomUUID();
    }
    public static String generateLogId() {
        return "LOG-" + UUID.randomUUID();
    }
    public static String generateVehicleId() {
        return "VEHICLE-" + UUID.randomUUID();
    }
    public static String generateEquipmentId() {
        return "EQUIP-" + UUID.randomUUID();
    }
}
