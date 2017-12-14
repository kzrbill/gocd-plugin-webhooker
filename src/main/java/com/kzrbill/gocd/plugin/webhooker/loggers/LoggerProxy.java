/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kzrbill.gocd.plugin.webhooker.loggers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Will Grange
 */
public class LoggerProxy {
    
    public void info(String message) throws IOException {
        Logger logger = Logger.getLogger("com.example.notification.loggers");
        Path path = FileSystems.getDefault().getPath("plugin-cd.pushy-hooker.log");
        FileHandler fh = new FileHandler(path.toString());
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.info(message);
    }
}
