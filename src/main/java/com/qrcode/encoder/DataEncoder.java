package main.java.com.qrcode.encoder;

import java.util.ArrayList;
import java.util.List;

public class DataEncoder {
    
    private static final int BYTE_MODE = 0b0100; //4 bits
    private static final int MAX_BYTES_VERSION_1 = 17; //version 1 capacity
    
    public static List<Boolean> encodeUrl(String url) 
    {
        //return a List<Boolean> where each Boolean = 1 bit
        List<Boolean> bits = new ArrayList<>();

        //add mode bits: 0100
        bits.add(false); // 0
        bits.add(true);  // 1  
        bits.add(false); // 0
        bits.add(false); // 0
        
        byte[] urlBytes = url.getBytes();

        if (urlBytes.length > MAX_BYTES_VERSION_1) 
        {
            throw new IllegalArgumentException("URL too long for Version 1 QR code");
        }

        addBits(bits, (byte) urlBytes.length); //add count bits

        for (byte b : urlBytes) addBits(bits, b); //add data bits

        for (int loop = 0; loop < 4; loop++) bits.add(false); //add terminator bits: 0000

        return bits;
    }

    private static void addBits(List<Boolean> bits, byte value) 
    {
        String binary = Integer.toBinaryString(value & 0xFF); //convert to binary string
        String binaryPadded = String.format("%8s", binary).replace(' ', '0'); //make sure the length is 8
        for (char c : binaryPadded.toCharArray()) bits.add(c == '1'); //add bits to list in order
    }

    public static void main(String[] args) 
    {
        List<Boolean> bits = encodeUrl("hi");
        System.out.println("Total bits: " + bits.size());
        
        // Print first 20 bits to see the structure
        System.out.print("First 20 bits: ");
        for (int i = 0; i < Math.min(20, bits.size()); i++) System.out.print(bits.get(i) ? "1" : "0");
        System.out.println();
    }
}
