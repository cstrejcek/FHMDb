package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Button btnDetail = new Button("Details");
    private final Button btnWatchlist = new Button("");
    private final ImageView imageView = new ImageView();
    private final HBox titleLayout = new HBox(title, btnDetail,btnWatchlist); // HBox for title and button
    private final VBox layout = new VBox(titleLayout, detail);
    private final HBox contentLayout = new HBox(imageView, layout);

    private List<Movie> noWatchlistButton;

    public MovieCell(String buttonText, ClickEventHandler<Movie> watchlistClicked, List<Movie> noWatchlistButton){
        //TODO set Button Text for Add/Remove Watchlist
        //TODO Callback for ButtonClick(add second parameter)
            btnWatchlist.setText(buttonText);
            btnWatchlist.setOnAction(event -> {
                watchlistClicked.onClick(getItem());
                //btnWatchlist.setVisible(false);
            });
            this.noWatchlistButton = noWatchlistButton;
        };

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);
        if (empty || movie == null ) {
            setText(null);
            setGraphic(null);
            contentLayout.setVisible(false);
        } else {
            contentLayout.setVisible(true);
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle() + " ");
            detail.setText(
                    movie.getDetail() != null
                            ? movie.getDetail()
                            : "No data available."
            );

            // Displaying image
            if (movie.getImgUrl() != null && !movie.getImgUrl().isEmpty()) {
                Image image = ImageCache.getImage(movie.getImgUrl());
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
                imageView.setImage(image);
            }

            // color scheme
            title.getStyleClass().add("text-blue");
            detail.getStyleClass().add("text-white");
            layout.setMinWidth(760.0);
            layout.setMaxWidth(760.0);
            layout.setFillWidth(true);
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            try {
                detail.setMaxWidth(this.getScene().getWidth() - 30);
            }catch (NullPointerException npe){

            }
            boolean setInvisible = false;
            for(Movie noMovie:noWatchlistButton) {
                if (movie.getId().equals(noMovie.getId())){
                    setInvisible = true;
                }
            }
            if(setInvisible)
                btnWatchlist.setVisible(false);
            else{
                btnWatchlist.setVisible(true);
            }
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            titleLayout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            btnDetail.setOnAction(event -> {
                Dialog<Void> dialog = new Dialog<>();
                dialog.setTitle("Movie Details - " + movie.getTitle());
                //dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("film.PNG"))));

                // Set the dialog content
                ImageView dialogImageView = new ImageView(ImageCache.getImage(movie.getImgUrl()));
                dialogImageView.setFitWidth(200); // Set the image width
                dialogImageView.setPreserveRatio(true);

                Label descriptionLabel = new Label(movie.getDetailLong());

                HBox dialogContent = new HBox(dialogImageView, descriptionLabel);
                dialogContent.setSpacing(5);
                descriptionLabel.setMaxWidth(this.getScene().getWidth() - 300);
                descriptionLabel.setWrapText(true);
                dialog.getDialogPane().setContent(dialogContent);

                // Add OK button
                ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk);
                dialog.showAndWait();
            });
            setGraphic(contentLayout);
        }
    }
}

