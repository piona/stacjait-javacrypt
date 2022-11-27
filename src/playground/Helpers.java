package playground;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Helpers {
    private static final byte[] hexDigits = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hex = new byte[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i] & 0xff;
            hex[i * 2] = hexDigits[val >>> 4];
            hex[i * 2 + 1] = hexDigits[val & 0x0f];
        }
        return new String(hex, StandardCharsets.US_ASCII);
    }

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] bytes = new byte[len / 2 + len % 2];
        int i = 0;
        if (len % 2 == 1) {
            int lval = Character.digit(hex.charAt(0), 16);
            if (lval == -1) {
                throw new IllegalArgumentException("invalid hex digit");
            }
            bytes[0] = (byte) lval;
            i = 1;
        }
        for (; i < len; i += 2) {
            int hval = Character.digit(hex.charAt(i), 16);
            int lval = Character.digit(hex.charAt(i + 1), 16);
            if (hval == -1 || lval == -1) {
                throw new IllegalArgumentException("invalid hex digit");
            }
            bytes[i / 2 + len % 2] = (byte) (hval << 4 | lval);
        }
        return bytes;
    }

    public static String hexDump(byte[] bytes, int offset, int length) {
        final int lineWidth = 16;

        StringBuilder builder = new StringBuilder((length / 16 + 1) * (6 + 16 * 3 + 2 + 16));

        for (int rowOffset = offset; rowOffset < offset + length; rowOffset += lineWidth) {
            builder.append(String.format("%04x:%04x  ", rowOffset, Math.min(rowOffset + lineWidth - 1, offset + length - 1)));

            for (int index = 0; index < lineWidth; index++) {
                if (rowOffset + index < length) {
                    builder.append(String.format("%02x ", bytes[rowOffset + index]));
                } else {
                    builder.append("   ");
                }
            }

            if (rowOffset < length) {
                int width = Math.min(lineWidth, length - rowOffset);
                builder.append("  ");
                builder.append(new String(bytes, rowOffset, width, StandardCharsets.US_ASCII).replaceAll("[^\\x20-\\x7E]", "."));
            }

            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    public static String hexDump(byte[] bytes) {
        return hexDump(bytes, 0, bytes.length);
    }

    public static String hexDump(String header, byte[] bytes, int offset, int length) {
        return header + System.lineSeparator() + hexDump(bytes, offset, length);
    }

    public static String hexDump(String header, byte[] bytes) {
        return hexDump(header, bytes, 0, bytes.length);
    }

    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static String base64Dump(String header, byte[] bytes) {
        return header + System.lineSeparator() + bytesToBase64(bytes);
    }

    public static void fileDump(String fileName, byte[] bytes, int offset, int length) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(bytes, offset, length);
        }
    }

    public static void fileDump(String fileName, byte[] bytes) throws IOException {
        fileDump(fileName, bytes, 0, bytes.length);
    }

    public static byte[] readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllBytes(path);
    }
}
