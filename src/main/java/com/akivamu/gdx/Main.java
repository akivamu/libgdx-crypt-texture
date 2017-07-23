package com.akivamu.gdx;

import com.akivamu.gdx.crypto.Crypto;
import com.akivamu.gdx.crypto.SimpleXorCrypto;

import java.io.File;

public class Main {
    private static final String OUTPUT_DIR_NAME = "encrypted";

    public static void main(String[] args) {
        if (args.length < 2) {
            printHelp();
        } else {

            // Output
            String outputDirName = OUTPUT_DIR_NAME;
            if (args.length == 3) {
                outputDirName = args[2];
            }

            // Key
            byte key = 0;
            try {
                key = Byte.parseByte(args[0]);
            } catch (NumberFormatException e) {
                shutdown("Invalid key. Must be a number from 0-255");
            }

            // Input
            File input = new File(args[1]);

            if (!input.exists()) shutdown("Input not exists");

            File[] inputFiles = new File[1];
            if (input.isDirectory()) {
                inputFiles = input.listFiles();
            } else {
                inputFiles[0] = input;
            }
            if (inputFiles == null || inputFiles.length == 0) shutdown("Input is empty");

            // Do encrypt
            File outputDir = new File(outputDirName);
            outputDir.mkdirs();
            Crypto crypto = new SimpleXorCrypto(key);
            for (File file : inputFiles) {
                if (file.isDirectory()) continue;
                String inputPath = file.getAbsolutePath();
                String outputPath = outputDir.getAbsolutePath() + "/" + file.getName();
                TextureEncryptor.encryptToFile(crypto, inputPath, outputPath);
                System.out.println(inputPath + " -> " + outputPath);
            }
        }
    }


    private static void shutdown(String text) {
        System.out.println(text);
        System.exit(1);
    }

    private static void printHelp() {
        System.out.println("Encrypt file by simple xor crypto.");
        System.out.println("Command line arguments:");
        System.out.println("   <key>       (required) key to encrypt (0-255)");
        System.out.println("   <input>     (required) input file/folder");
        System.out.println("   [output]    (optional) output folder");
    }
}
