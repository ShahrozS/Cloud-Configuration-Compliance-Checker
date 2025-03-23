package com.shahrozz.demo.IAC;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class IaCScanner {

    public JSONArray scanTerraform(String tfContent) throws Exception {
        Path tempDir = Files.createTempDirectory("tf-scan-");
        try {
            Path tfFile = tempDir.resolve("main.tf");
            Files.write(tfFile, tfContent.getBytes());

            CommandLine cmd = new CommandLine("checkov")
                    .addArgument("-d")
                    .addArgument(tempDir.toString())
                    .addArgument("-o")
                    .addArgument("json");

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(output);

            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(streamHandler);
            int exitCode = executor.execute(cmd);

            if(exitCode != 0) throw new RuntimeException("Scan failed");

            return new JSONObject(output.toString())
                    .getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONArray("failed_checks");
        } finally {
            FileUtils.deleteDirectory(tempDir.toFile());
        }
    }
}