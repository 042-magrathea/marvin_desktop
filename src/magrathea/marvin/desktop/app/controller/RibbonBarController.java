/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javax.imageio.ImageIO;
import magrathea.marvin.desktop.app.Main;

/**
 *
 * @author tricoman
 */
public class RibbonBarController {
    
    @FXML private ImageView homeImageView;
    @FXML private ToggleButton button;
    @FXML private BorderPane image;

    public RibbonBarController() {
        
    }
    
    public void initialice() {
        
        button.setGraphic(image);
  ///      Image image = new Image("file:/src/icons/trash.png");
//        this.homeImageView.setImage(image);
        
        
    }
    
}
