package com.akivamu.gdx.exe;

import com.akivamu.gdx.TextureEncryptor;
import com.akivamu.gdx.crypto.Crypto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Main {
    private static final String DEFAULT_OUTPUT_DIR_NAME = "encrypted";
    protected final List<String> ignoreExtensions = new ArrayList<>();

    private final String outputDirName;
    private final File[] inputFiles;
    private final Crypto crypto;

    protected Main(String[] args) throws IllegalArgumentException {
        if (args.length != 2 && args.length != 3) {
            printHelp();
            throw new IllegalArgumentException("Insufficient parameters");
        } else {
            // Output
            if (args.length == 3) {
                outputDirName = args[2];
            } else {
                outputDirName = DEFAULT_OUTPUT_DIR_NAME;
            }

            // Input
            File input = new File(args[1]);
            if (!input.exists()) throw new IllegalArgumentException("Input not exists");
            File[] inputFiles = new File[1];
            if (input.isDirectory()) {
                inputFiles = input.listFiles();
            } else {
                inputFiles[0] = input;
            }
            if (inputFiles == null || inputFiles.length == 0) throw new IllegalArgumentException("Input is empty");
            this.inputFiles = inputFiles;

            // Build crypto
            crypto = buildCrypto(args[0]);
        }
    }

    protected abstract Crypto buildCrypto(String key) throws IllegalArgumentException;

    protected final void doEncrypt() {
        File outputDir = new File(outputDirName);
        outputDir.mkdirs();

        for (File file : inputFiles) {
            if (file.isDirectory() || isIgnored(file.getName())) continue;
            String inputPath = file.getAbsolutePath();
            String outputPath = outputDir.getAbsolutePath() + "/" + file.getName();
            TextureEncryptor.encryptToFile(crypto, inputPath, outputPath);
            System.out.println(inputPath + " -> " + outputPath);
        }
    }

    private boolean isIgnored(String fileName) {
        for (String ignoreExt : ignoreExtensions) {
            if (fileName.endsWith("." + ignoreExt)) return true;
        }
        return false;
    }

    protected static void onError(String text) {
        System.out.println(text);
        System.exit(1);
    }

    protected void printHelp() {
        System.out.println("Command line arguments:");
        System.out.println("   <key>       (required) key to encrypt");
        System.out.println("   <input>     (required) input file/folder");
        System.out.println("   [output]    (optional) output folder");
    }
}
