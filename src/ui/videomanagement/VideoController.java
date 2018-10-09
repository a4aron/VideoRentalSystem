package ui.videomanagement;
import controller.Auth;
import controller.ControllerInterface;
import controller.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Videos;
import ruleset.RuleException;
import ruleset.RuleSet;
import ruleset.RuleSetFactory;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class VideoController {
    @FXML
    private TextField tfID, tfMoviename, tfRating, tfGenre,tfDirector, tfRentalFee, tfPrice, tfStatus;
    @FXML
    private TextArea tfDescription;
    @FXML
    private ListView<String> videoListView;
    @FXML
    private Button  btn_updateVideo, btn_deleteVideo, btn_clearVideo;

    List<Videos> videoList;
    // Populate data for screen

    public void initialize() {
        if (SystemController.getInstance().currentAuth.equals(Auth.EMPLOYEE)) {
            btn_updateVideo.setVisible(false);
            btn_updateVideo.setVisible(false);
            btn_clearVideo.setVisible(false);
        }

        ControllerInterface controller = SystemController.getInstance();
        videoList = controller.getAllVideos();
        ArrayList<String> videos = new ArrayList<>();
        for (Videos v:  videoList) {
            videos.add(v.getName());
        }
        ObservableList<String> strList = FXCollections.observableArrayList(videos);

        videoListView.setItems(strList);
        videoListView.setOnMouseClicked(evt -> {
//                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                int position = videoListView.getSelectionModel().getSelectedIndex();
                tfID.setText(videoList.get(position).getId() + "");
                tfMoviename.setText(videoList.get(position).getName());
                tfRating.setText(videoList.get(position).getRating());
                tfGenre.setText(videoList.get(position).getGenre());
                tfDescription.setText(videoList.get(position).getDescription());
                tfDirector.setText(videoList.get(position).getDirector());

                //double
                tfRentalFee.setText(String.valueOf(videoList.get(position).getRentalFee()));
                tfPrice.setText(String.valueOf(videoList.get(position).getPrice()));
                tfStatus.setText(String.valueOf(videoList.get(position).getStatus()));
            }
        );
    }

    // Actions list
    @FXML
    public void updateVideo(ActionEvent actionEvent) {
        try {
            RuleSet videoRules = RuleSetFactory.getRuleSet(VideoController.this);
            videoRules.applyRules(VideoController.this);
            ControllerInterface controller = SystemController.getInstance();
//String name, String rating, String genre, String description, String director, double rentalFee, double price, boolean status
            if(!tfID.getText().isEmpty()) {
                if (controller.updateVideo(new Videos(Integer.parseInt(tfID.getText()), tfMoviename.getText(), tfRating.getText(), tfGenre.getText(), tfDescription.getText(),
                        tfDirector.getText(), Double.parseDouble(tfRentalFee.getText()), Double.parseDouble(tfPrice.getText()), Boolean.valueOf(tfStatus.getText()))));
                        initialize();
                        clearFields(actionEvent);
            }
            else {
                if (controller.addVideo(new Videos(-1,tfMoviename.getText(), tfRating.getText(), tfGenre.getText(), tfDescription.getText(),
                        tfDirector.getText(), Double.parseDouble(tfRentalFee.getText()), Double.parseDouble(tfPrice.getText()), Boolean.valueOf(tfStatus.getText())))) {
                    initialize();
                    clearFields(actionEvent);
                }
            }
        } catch (RuleException e) {
            Util.showError(e.getMessage());
        }
    }
    @FXML
    public void deleteVideo(ActionEvent actionEvent) {
        if (tfID.getText().isEmpty()) {
            Util.showError("No Video chosen");
            return;
        }
        ControllerInterface controller = SystemController.getInstance();
        if (controller.deleteVideo(Integer.parseInt(tfID.getText()))){
            initialize();
            clearFields(actionEvent);
        }
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        tfID.clear();
        tfMoviename.clear();
        tfRating.clear();
        tfGenre.clear();
        tfDirector.clear();
        tfRentalFee.clear();
        tfPrice.clear();
        tfStatus.clear();
        tfDescription.clear();
    }

    public String getTfMoviename() {
        return tfMoviename.getText();
    }

    public String getTfRating() {
        return tfRating.getText();
    }

    public String getTfGenre() {
        return tfGenre.getText();
    }

    public String getTfDirector() {
        return tfDirector.getText();
    }

    public String getTfRentalFee() {
        return tfRentalFee.getText();
    }

    public String getTfPrice() {
        return tfPrice.getText();
    }

    public String getTfStatus() {
        return tfStatus.getText();
    }
}
