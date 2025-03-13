package com.restcrud.springboot.cruddemo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DatabaseBackupService {


    // Schedule the backup to run daily at 11 PM
    //@Scheduled(cron = "0 0 23 * * ?") // 0 seconds, 0 minutes, 23 hours, Day of the month (* means every day),  Month (* means runs every month), Lst is Day of the week — the ? means "no specific day of the week," so it doesn’t care what day it is, just the date.
    //@Scheduled(cron = "0,30 * * * * ?") //To test it you can try at 30 secounds - 0 and 30 seconds, every minute, every hour, every day
    public void backupDatabase() {
        System.out.println("Running Cron.. for DB BK..");
        // Generate a timestamp for the backup file name
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        // Define the backup directory and file path with timestamp
        String backupDir = "C:/database_backups";
        String backupFile = backupDir + "/employee_directory_" + timestamp + ".sql";

        // Create the backup directory if it doesn't exist
        File directory = new File(backupDir);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Backup directory created: " + backupDir);
            } else {
                System.err.println("Failed to create backup directory: " + backupDir);
                return;
            }
        }

        // Construct the MySQL dump command with full path
        String command = String.format(
                "\"C:/Program Files/MySQL/MySQL Server 8.3/bin/mysqldump\" -u%s -p%s --databases %s -r %s",
                "springstudent",      // MySQL username
                "springstudent",      // MySQL password
                "employee_directory", // Database name
                backupFile             // Output file
        );

        try {
            // Execute the command
            Process process = Runtime.getRuntime().exec(command);

            // Wait for the process to complete and capture the exit code
            int exitCode = process.waitFor();

            // Check if the backup was successful
            //Any non-zero exit code indicates that something went wrong
            if (exitCode == 0) {
                System.out.println("Database backup successful: " + backupFile);
                System.out.println("Cron DB BK Success..");
            } else {
                System.err.println("Database backup failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
