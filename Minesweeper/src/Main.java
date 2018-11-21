import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent>{
	
	boolean buttonPressed = false;
	GridPane grid;
	HBox hbox;
	Board field;
	Button reset;
	int buttonCount;
	int bombCount;
	AnimationTimer timer;
	Label lblTime = new Label("000");
	Label lblBombs;
	int seconds;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Minesweeper");
		BorderPane border = new BorderPane();
		this.grid = new GridPane();
		this.grid.setMinSize(30, 30);
		this.hbox = new HBox();
		field = new Board(16, 16, 40);
		this.bombCount = field.getBombs();
		this.lblBombs = new Label(Integer.toString(bombCount));
		this.lblBombs.setFont(new Font(40));
		this.lblBombs.setPadding(new Insets(0, (field.getWidth() * 30) / 2 - 65, 0, 0));
		this.buildBoard();
		border.setCenter(grid);
		this.reset = new Button();
		this.reset.setOnAction(this);
		this.reset.setText("🙂");
		this.reset.setFont(new Font(21));
		this.reset.setTextFill(Color.ORANGE);
		this.reset.setPrefSize(50, 50);
		this.lblTime.setFont(new Font(40));
		this.lblTime.setPadding(new Insets(0, 0, 0, (field.getWidth() * 30) / 2 - 95));
		timer = new AnimationTimer() {
			long lastTime = 0;
			
			@Override
			public void handle(long now) {
				if (lastTime != 0) {
	                if (now > lastTime + 1_000_000_000) {
	                    seconds++;
	                    if(seconds < 10) {
	                    	lblTime.setText("00" + Integer.toString(seconds));
	                    } else if (seconds < 100) {
	                    	lblTime.setText("0" + Integer.toString(seconds));
	                    } else {
	                    	lblTime.setText(Integer.toString(seconds));
	                    }
	                    lastTime = now;
	                }
	            } else {
	                lastTime = now;
	            }
			}
			
			@Override
	        public void stop() {
	            super.stop();
	            lastTime = 0;
	            seconds = 0;
	        }
		};
		this.hbox.getChildren().add(lblBombs);
		this.hbox.getChildren().add(reset);
		this.hbox.getChildren().add(lblTime);
		border.setTop(this.hbox);
		Scene scene = new Scene(border, field.getWidth() * 30, field.getHeight() * 30 + 60);
		scene.getStylesheets().add("button.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void buildBoard() {
		for (int i = 0; i < field.getWidth(); i++) {
			for (int j = 0; j < field.getHeight(); j++) {
				Button btn = new Button();
				btn.setMinSize(30, 30);
				GridPane.setConstraints(btn, i, j);
				grid.getChildren().add(btn);
				btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
					 
		            @Override
		            public void handle(MouseEvent event) {
		                MouseButton mouseButton = event.getButton();
		                if(mouseButton==MouseButton.PRIMARY){
		                	if(!buttonPressed) {
		                		field.initialize(GridPane.getColumnIndex(btn), GridPane.getRowIndex(btn));
		                		buttonCount = field.getWidth() * field.getHeight() - field.getBombs();
		                		for (int a = 0; a < field.getWidth(); a++) {
		                			for (int b = 0; b < field.getHeight(); b++) {
		                				Label number;
		                				switch (field.getField()[a][b]) {
		                					case -1:	number = new Label("💣");
		                								break;
		                					case 0:		number = new Label("⬜");
		                								break;
		                					case 1:		number = new Label("" + field.getField()[a][b]);
		                								number.setTextFill(Color.BLUE);
		                								break;
		                					case 2:		number = new Label("" + field.getField()[a][b]);
            											number.setTextFill(Color.GREEN);
            											break;
		                					case 3:		number = new Label("" + field.getField()[a][b]);
            											number.setTextFill(Color.RED);
            											break;
		                					case 4:		number = new Label("" + field.getField()[a][b]);
            											number.setTextFill(Color.PURPLE);
            											break;
		                					case 5:		number = new Label("" + field.getField()[a][b]);
            											number.setTextFill(Color.BROWN);
            											break;
		                					case 6:		number = new Label("" + field.getField()[a][b]);
            											number.setTextFill(Color.TURQUOISE);
            											break;
		                					default:	number = new Label("" + field.getField()[a][b]);
		                				}
		                				number.setPrefSize(30, 30);
		                				number.setFont(new Font(20));
		                				number.setAlignment(Pos.CENTER);
		                				GridPane.setConstraints(number, a, b);
		                				grid.getChildren().add(0, number);
		                			}
		                		}
		                		buttonPressed = true;
		                		timer.start();
		                	}
		                	if(!btn.getText().equals("🚩") && field.getField()[GridPane.getColumnIndex(btn)][GridPane.getRowIndex(btn)] == -1) {
		                		grid.getChildren().remove(field.getWidth() * field.getHeight(), grid.getChildren().size());
		                		((Label) grid.getChildren().get(field.getHeight() * field.getWidth() - 1 - (GridPane.getColumnIndex(btn))* field.getHeight() - GridPane.getRowIndex(btn))).setTextFill(Color.RED);
		                		reset.setText("😵");
		                		timer.stop();
		                	} else if (!btn.getText().equals("🚩") && field.getField()[GridPane.getColumnIndex(btn)][GridPane.getRowIndex(btn)] == 0) {
		                		int btnIndex = grid.getChildren().indexOf(btn);
		                		Label placeholder = new Label("");
		                		grid.getChildren().add(btnIndex, placeholder);
		                		grid.getChildren().remove(btn);
		                		buttonCount--;
		                		if(buttonCount == 0) {
			                		reset.setText("😎");
			                		timer.stop();
			                	}
		                		if(btnIndex - 1 > grid.getChildren().size()/2 - 1 && grid.getChildren().get(btnIndex - 1) instanceof Button && GridPane.getRowIndex(grid.getChildren().get(btnIndex -1)) != field.getHeight() - 1) {
		                			((Button) grid.getChildren().get(btnIndex - 1)).fireEvent(event);
		                		}
		                		if(btnIndex + 1 < grid.getChildren().size() && grid.getChildren().get(btnIndex + 1) instanceof Button && GridPane.getRowIndex(grid.getChildren().get(btnIndex + 1)) != 0) {
		                			((Button) grid.getChildren().get(btnIndex + 1)).fireEvent(event);
		                		}
		                		if(btnIndex - field.getHeight() > grid.getChildren().size()/2 && grid.getChildren().get(btnIndex - field.getHeight()) instanceof Button) {
		                			((Button) grid.getChildren().get(btnIndex - field.getHeight())).fireEvent(event);
		                		}
		                		if(btnIndex + field.getHeight() < grid.getChildren().size() && grid.getChildren().get(btnIndex + field.getHeight()) instanceof Button) {
		                			((Button) grid.getChildren().get(btnIndex + field.getHeight())).fireEvent(event);
		                		}
		                		if(btnIndex - field.getHeight() - 1 > grid.getChildren().size()/2 && grid.getChildren().get(btnIndex - field.getHeight() - 1) instanceof Button && GridPane.getRowIndex(grid.getChildren().get(btnIndex - field.getHeight() - 1)) != field.getHeight() - 1) {
		                			((Button) grid.getChildren().get(btnIndex - field.getHeight() - 1)).fireEvent(event);
		                		}
		                		if(btnIndex - field.getHeight() + 1 > grid.getChildren().size()/2 && grid.getChildren().get(btnIndex - field.getHeight() + 1) instanceof Button && GridPane.getRowIndex(grid.getChildren().get(btnIndex - field.getHeight() + 1)) != 0) {
		                			((Button) grid.getChildren().get(btnIndex - field.getHeight() + 1)).fireEvent(event);
		                		}
		                		if(btnIndex + field.getHeight() - 1 < grid.getChildren().size() && grid.getChildren().get(btnIndex + field.getHeight() - 1) instanceof Button && GridPane.getRowIndex(grid.getChildren().get(btnIndex + field.getHeight() - 1)) != field.getHeight() - 1) {
		                			((Button) grid.getChildren().get(btnIndex + field.getHeight() - 1)).fireEvent(event);
		                		}
		                		if(btnIndex + field.getHeight() + 1 < grid.getChildren().size() && grid.getChildren().get(btnIndex + field.getHeight() + 1) instanceof Button && GridPane.getRowIndex(grid.getChildren().get(btnIndex + field.getHeight() + 1)) != 0) {
		                			((Button) grid.getChildren().get(btnIndex + field.getHeight() + 1)).fireEvent(event);
		                		}
		                	} else if(!btn.getText().equals("🚩") && field.getField()[GridPane.getColumnIndex(btn)][GridPane.getRowIndex(btn)] > 0) {
		                		Label placeholder = new Label("");
		                		grid.getChildren().add(grid.getChildren().indexOf(btn), placeholder);
		                		grid.getChildren().remove(btn);
		                		buttonCount--;
		                		if(buttonCount == 0) {
			                		reset.setText("😎");
			                		timer.stop();
			                	}
		                	}
		                }else if(mouseButton==MouseButton.SECONDARY){
		                	if(btn.getText() == "🚩") {
		                		btn.setText("");
		                		bombCount++;
		                		if(bombCount < 10) {
			                    	lblBombs.setText("0" + Integer.toString(bombCount));
			                    } else {
			                    	lblBombs.setText(Integer.toString(bombCount));
			                    }
		                	} else {
		                    btn.setText("🚩");
		                    btn.setTextFill(Color.RED);
		                    bombCount--;
		                    if(bombCount < 10) {
		                    	lblBombs.setText("0" + Integer.toString(bombCount));
		                    } else {
		                    	lblBombs.setText(Integer.toString(bombCount));
		                    }
		                	}
		                } else if(mouseButton==MouseButton.MIDDLE){
		                }
		            }
		        });
			}
		}
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == reset) {
			this.timer.stop();
            this.lblTime.setText("000");
			this.grid.getChildren().clear();
			this.field = new Board(16, 16, 40);
			reset.setText("🙂");
			bombCount = this.field.getBombs();
			lblBombs.setText(Integer.toString(bombCount));
			this.buttonPressed = false;
			this.buildBoard();
		}		
	}
}
