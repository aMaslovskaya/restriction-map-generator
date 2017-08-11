package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private ArrayList<DNAView> views = new ArrayList<>();

    @FXML
    private CheckBox isNucleotidesColorized;

    @FXML
    private TextField text;

    @FXML
    private Button deleteButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button removeMap;

    @FXML
    private ComboBox fontsSelect;

    @FXML
    private ComboBox mapNumber;

    @FXML
    private ColorPicker backgroundColorPicker;

    @FXML
    private ColorPicker fontColorPicker;

    @FXML
    private ColorPicker DNAColorPicker;

    @FXML
    private Slider fontSizeSlider;

    @FXML
    private Text fontSizeText;

    @FXML
    private Button zoomIn;

    @FXML
    private Button zoomOut;

    private VBox box;

    @FXML
    private CheckBox underline;

    @FXML
    private CheckBox bold;

    @FXML
    private CheckBox italic;

    @FXML
    private CheckBox horizontal;

    private float scaleDelta = 2;

    @FXML
    private void onQuit(final ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onSave(final  ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(getCanvas().getScene().getWindow());

        if(file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) getCanvas().getWidth(), (int) getCanvas().getHeight());
                getCanvas().snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
            }
        }
    }

    public javafx.scene.canvas.Canvas getCanvas() {
        return views.get((Integer)mapNumber.getValue() - 1).getContext().getCanvas();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Character> nucleotides = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            switch (new Random().nextInt(4)) {
                case 0:
                    nucleotides.add('A');
                    break;
                case 1:
                    nucleotides.add('T');
                    break;
                case 2:
                    nucleotides.add('G');
                    break;
                default:
                    nucleotides.add('C');
            }
        }

        DNA dna = new DNA(nucleotides);
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));
        dna.addRestrictionSite(120, new RestrictionEnzyme("EcoR1", "abfbad"));


//        {
//            DNA: {
//                nucleotidesCount: number,
//                restrictionSites: [
//                    {
//                        nucleotideNumber: number,
//                        restrictionEnzyme: {
//                            name: string;
//                            sequence: string;
//                        }
//                    },
//                    ...
//                ]
//            }
//        }


        ////////////////////////////////////////////////////////////////////////////////////////////////////

        box = new VBox();
        for(int i = 0; i < 20; i++) {
            javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(1000, 380);
            views.add(new DNAView(dna, canvas.getGraphicsContext2D()));
            box.getChildren().addAll(canvas);
        }
        scrollPane.setContent(box);


//        views.add(new DNAView(dna, graphicsContext, canvas.getHeight(), canvas.getHeight() / 3 * 2));
//        views.add(new DNAView(dna, graphicsContext, canvas.getHeight() / 3 * 2, canvas.getHeight() / 3));
//        views.add(new DNAView(dna, graphicsContext, canvas.getHeight() / 3 , 0));

        /////////////////////////////////////////////////////////////////////////////////////////////////////

        fontSizeSlider.setMin(4);
        fontSizeSlider.setMax(40);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setMajorTickUnit(30);
        fontSizeSlider.setMinorTickCount(2);
        fontSizeSlider.setBlockIncrement(2);
        fontsSelect.getItems().addAll(javafx.scene.text.Font.getFamilies());
        fontsSelect.setPromptText("Fonts");
        ArrayList<Integer> mapNumbers = new ArrayList<>();
        for (int i = 0; i < views.size(); i++) {
            mapNumber.getItems().add(i + 1);
        }
        mapNumber.setPromptText("map");
        mapNumber.setValue(1);
        setPickersValue();
        setListeners();
    }

    private void setPickersValue() {
        DNAView view = views.get((Integer)mapNumber.getValue() - 1);
        backgroundColorPicker.setValue(view.getBackgroundColor());
        DNAColorPicker.setValue(view.getDNAColor());
        fontColorPicker.setValue(view.getInitFontColor());
        fontSizeSlider.setValue(view.getInitFontSize());
        fontsSelect.setValue(view.getInitFontFamily());
        text.setText(views.get((Integer)mapNumber.getValue() - 1).getText());
        bold.setSelected(false);
        italic.setSelected(false);
        underline.setSelected(false);
        isNucleotidesColorized.setSelected(false);
        horizontal.setSelected(false);
    }

    private void setListeners() {

        backgroundColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                views.get((Integer)mapNumber.getValue() - 1).setBackgroundColor(backgroundColorPicker.getValue());
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        DNAColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                views.get((Integer)mapNumber.getValue() - 1).setDNAColor(DNAColorPicker.getValue());
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        fontColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                views.get((Integer)mapNumber.getValue() - 1).setFontColor(fontColorPicker.getValue());
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        fontSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fontSizeText.setText(Integer.toString((int)fontSizeSlider.getValue()));
                views.get((Integer)mapNumber.getValue() - 1).setFontSize((int)fontSizeSlider.getValue());
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        mapNumber.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                scrollPane.setVvalue((double)((Integer)mapNumber.getValue() - 1) / (mapNumber.getItems().size() - 1));
                setPickersValue();
            }
        });

        horizontal.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                views.get((Integer)mapNumber.getValue() - 1).setIsHorizontalDirection(horizontal.isSelected());
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        isNucleotidesColorized.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                views.get((Integer)mapNumber.getValue() - 1).setNucliotidesColorized(isNucleotidesColorized.isSelected());
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        underline.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                views.get((Integer)mapNumber.getValue() - 1).setIsUnderline(underline.isSelected());
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        bold.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                views.get((Integer) mapNumber.getValue() - 1).setWeight(bold.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL);
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        italic.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                views.get((Integer)mapNumber.getValue() - 1).setPosture(italic.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR);
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        fontsSelect.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String t1) {
                views.get((Integer)mapNumber.getValue() - 1).setFontFamily(t1);
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                views.get((Integer)mapNumber.getValue() - 1).deleteSites();
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                views.get((Integer)mapNumber.getValue() - 1).setText(t1);
                for (DNAView view : views) {
                    view.update();
                }
            }
        });

        removeMap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                box.getChildren().remove((Integer)mapNumber.getValue() - 1);
                views.remove((Integer)mapNumber.getValue() - 1);
                mapNumber.getItems().remove(mapNumber.getItems().size() - 1);

            }
        });

        zoomIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DNAView view = views.get((Integer)mapNumber.getValue() - 1);

                view.setScale((float)(view.getScale() * scaleDelta));
                if(view.getCanvasX() == 0 && view.getCanvasY() == 0) {
                    view.setCanvasX(getCanvas().getWidth() * (1 - view.getScale()) / 2);
                    view.setCanvasY(getCanvas().getHeight() * (1 - view.getScale()) / 2);
                }
                else {
                    view.setCanvasX(view.getCanvasX() * scaleDelta);
                    view.setCanvasY(view.getCanvasY() * scaleDelta);
                }
                view.render();
            }
        });

        zoomOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DNAView view = views.get((Integer)mapNumber.getValue() - 1);
                double scale = view.getScale() / scaleDelta >= 1 ? view.getScale() / scaleDelta : 1;
                view.setScale((float)scale);
                if(scale != 1) {
                    view.setCanvasX(view.getCanvasX() / scaleDelta);
                    view.setCanvasY(view.getCanvasY() / scaleDelta);
                } else {
                    view.setCanvasX(getCanvas().getWidth() * (1 - scale) / 2);
                    view.setCanvasY(getCanvas().getHeight() * (1 - scale) / 2);
                }
                view.render();
            }
        });


    }
}
