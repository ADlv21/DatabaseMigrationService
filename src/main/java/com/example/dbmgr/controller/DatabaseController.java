package com.example.dbmgr.controller;

import com.example.dbmgr.payloads.DBDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class DatabaseController {

    @PostMapping("/")
    private DBDTO postgreSQLMigration(@RequestBody DBDTO dbdto){

        String fileName = UUID.randomUUID().toString();
        String pathToSaveFileTo = fileName + ".sql";
        String commandDown = String.format("pg_dumpall --host %s --username %s --no-role-passwords --exclude-database=cloudsqladmin --exclude-database=rdsadmin > %s",dbdto.getBaseHostName(), dbdto.getBaseUserName() , pathToSaveFileTo);

        try {
            // Create a ProcessBuilder instance
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", commandDown);
            processBuilder.environment().put("PGPASSWORD", dbdto.getBasePassword());

            // Redirect the output to a file
            File outputFile = new File(pathToSaveFileTo);
            processBuilder.redirectOutput(outputFile);

            // Start the process
            System.out.println("Executing Command : " + commandDown);
            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Command executed successfully");
            } else {
                System.err.println("Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String commandUpload = String.format("psql --host %s --username %s --file %s",dbdto.getMigrationServerHostName(), dbdto.getMigrationServerUserName() , pathToSaveFileTo);

        try {
            // Create a ProcessBuilder instance
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", commandUpload);
            processBuilder.environment().put("PGPASSWORD", dbdto.getMigrationServerPassword());

            // Start the process
            System.out.println("Executing Command : " + commandUpload);
            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Command executed successfully");
            } else {
                System.err.println("Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        File myObj = new File(pathToSaveFileTo);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }

        return dbdto;
    }

}
