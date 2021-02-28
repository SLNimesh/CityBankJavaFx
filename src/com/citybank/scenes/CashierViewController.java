package com.citybank.scenes;

import com.citybank.model.UserContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class CashierViewController {

    private UserContext currentUser;

    @FXML
    private Label headerGreeting;

    @FXML
    private Label headerUsername;

    @FXML
    private Circle profilePic;

    @FXML
    private ImageView profileImage;

}
