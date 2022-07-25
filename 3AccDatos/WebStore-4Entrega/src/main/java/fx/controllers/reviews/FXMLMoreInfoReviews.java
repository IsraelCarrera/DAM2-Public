package fx.controllers.reviews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Review;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMoreInfoReviews implements Initializable {


    @FXML
    private Label lbCustomer;
    @FXML
    private Label lbItem;
    @FXML
    private Label lbTittle;
    @FXML
    private Label lbDescription;
    @FXML
    private Label lbRate;
    @FXML
    private Label lbDate;

    public void cargaDatos(Review r){
        lbCustomer.setText(r.getPurchase().getCustomer().toString());
        lbItem.setText(r.getPurchase().getItem().toString());
        lbTittle.setText(r.getTitle());
        lbDescription.setText(r.getDescription());
        lbRate.setText(String.valueOf(r.getRating()));
        lbDate.setText(r.getDate().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
