package ru.mininn.util;

import android.util.Base64;
import android.util.Log;

import java.util.Arrays;

import ru.mininn.meterslog.data.model.Meter;

import static java.text.DateFormat.DEFAULT;

public class MeterParser {

    public static Meter parseToMeterData(byte[] blePackage, long timestamp) {

        if (checkPackege(blePackage)) {
            return new Meter(
                    false,
                    parsePacketCounter(blePackage),
                    parsePacketVersion(blePackage),
                    parseDeviceType(blePackage),
                    parseDeviceModel(blePackage),
                    parseDeviceSernum(blePackage),
                    timestamp,
                    parseDeviceValue(blePackage),
                    parseDeviceSecondValue(blePackage),
                    parseBatteryValue(blePackage),
                    parseTemperatureValue(blePackage),
                    0,
                    0,
                    0,
                    parsePackageData(blePackage),
                    false
            );
        } else {
            return null;
        }
    }

    public static Meter parseToMeterDataEncrypted(String macAddress, byte[] blePackage, long timestamp) {

        return new Meter(
                true,
                0,
                0,
                parseDeviceType(macAddress),
                parseDeviceModel(macAddress),
                parseDeviceSernum(macAddress),
                timestamp,
                0.0,
                0.0,
                0,
                0.0,
                0,
                0,
                0,
                parseEncryptedPackageData(blePackage),
                false

        );
    }

    private static int parsePacketCounter(byte[] blePackage) {
        return (blePackage[5] & 0xFF) + (blePackage[6] & 0xFF);
    }

    private static int parsePacketVersion(byte[] blePackage) {
        return blePackage[7];
    }

    private static int parseDeviceType(byte[] blePackage) {
        return blePackage[8];
    }

    private static int parseDeviceType(String macAddress) {
        return Integer.parseInt(macAddress.substring(6, 9).replaceAll(":", ""), 16);
    }

    private static int parseDeviceModel(byte[] blePackage) {
        return blePackage[9];
    }

    private static int parseDeviceModel(String macAddress) {
        return Integer.parseInt(macAddress.substring(3, 6).replaceAll(":", ""), 16);
    }

    private static int parseDeviceSernum(byte[] blePackage) {
        return (blePackage[10] & 0xFF) + ((blePackage[11] & 0xFF) << 8) + ((blePackage[12] & 0xFF) << 16);
    }

    private static int parseDeviceSernum(String macAddress) {
        return Integer.parseInt(macAddress.substring(9).replaceAll(":", ""), 16);
    }

    private static double parseDeviceValue(byte[] blePackage) {
        return ((blePackage[13] & 0xFF) + ((blePackage[14] & 0xFF) << 8) + ((blePackage[15] & 0xFF) << 16) + ((blePackage[16] & 0xFF) << 24)) / 10000d;
    }

    //TODO: make functional type of parseDeviceValue() method when we give new protocol for double tariff metr
    private static double parseDeviceSecondValue(byte[] blePackage) {
        return 0;
    }

    private static int parseBatteryValue(byte[] blePackage) {
        return (blePackage[17] & 0xFF);
    }

    private static double parseTemperatureValue(byte[] blePackage) {
        return (double) ((blePackage[18] & 0xFF) | ((blePackage[19] & 0xFF) << 8)) /100;
    }

    private static String parsePackageData(byte[] blePackage) {
        return Base64.encodeToString(new Crc32().add(Arrays.copyOfRange(blePackage, 0, 21)), DEFAULT);
    }

    private static String parseEncryptedPackageData(byte[] blePackage) {
        return Base64.encodeToString(Arrays.copyOfRange(blePackage, 0, 21), DEFAULT);
    }

    private static boolean checkPackege(byte[] blePackage) {
        return blePackage.length > 20 && blePackage[7] == 1;
    }
}   
    