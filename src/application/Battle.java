
package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Audio.Music;
import entity.Character;
import entity.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Battle
{
	private BorderPane pane;
	private Player player;
	private Character enemy;
	private GridPane actions;
	private ProgressBar eh;
	private ProgressBar ph;
	private ProgressBar pm;
	private ProgressBar xp;
	private Label level;
	private Label phInfo;
	private Label ehInfo;
	private Label pmInfo;
	private int lastLevel;
	private Image playerImage;
	private Image enemyImage;
	private ImageView playerImageView;
	ImageView enemyImageView;
	/**
	 * Constructor
	 * @param player the player in the battle
	 * @param enemy the enemy in the battle
	 * @param lastLevel the level to return to after the battle is done
	 */
	public Battle(Player player, Character enemy, int lastLevel)
	{
		this.player = player;
		this.enemy = enemy;
		this.lastLevel = lastLevel;
		pane = new BorderPane();
		
		ImageView bg = new ImageView();
		Image bgImg;
		try
		{
			bgImg = new Image(new FileInputStream("assets/img/bg" + lastLevel + ".png"));
			bg.setImage(bgImg);
			pane.getChildren().add(bg);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		setUpAttacksActions();
		eh = new ProgressBar();
		eh.setStyle("-fx-accent : red;");
		ph = new ProgressBar();
		ph.setStyle("-fx-accent : green;");
		pm = new ProgressBar();
		xp = new ProgressBar();
		xp.setStyle("-fx-accent : purple;");
		phInfo = new Label(player.getHp() + " / " + player.getMaxHP());
		ehInfo = new Label(enemy.getHp() + " / " + enemy.getMaxHP());
		pmInfo = new Label(player.getMana() + " / " + player.getMaxMana());
		level = new Label();
		phInfo.setStyle("-fx-background-color: grey; -fx-text-fill: yellow;");
		ehInfo.setStyle("-fx-background-color: grey; -fx-text-fill: yellow;");
		pmInfo.setStyle("-fx-background-color: grey; -fx-text-fill: yellow;");
		level.setStyle("-fx-background-color: grey; -fx-text-fill: yellow;");
		

		GridPane main = new GridPane();
		for (int i = 0; i < 2; i++)
		{
			RowConstraints rc = new RowConstraints();
			rc.setVgrow(Priority.ALWAYS);
			rc.setFillHeight(true);
			main.getRowConstraints().add(rc);
		}
		for (int i = 0; i < 2; i++)
		{
			ColumnConstraints cc = new ColumnConstraints();
			cc.setHgrow(Priority.ALWAYS);
			cc.setFillWidth(true);
			main.getColumnConstraints().add(cc);
		}
		HBox playerBox = new HBox();
		playerBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		playerBox.setAlignment(Pos.CENTER);
		playerImage = player.getBattleIcon().getImage();
		playerImageView = new ImageView(playerImage);
		playerImageView.setFitHeight(150);
		;
		playerImageView.setPreserveRatio(true);
		playerBox.getChildren().add(playerImageView);
		main.add(playerBox, 0, 1);

		HBox enemyBox = new HBox();
		enemyBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		enemyBox.setAlignment(Pos.CENTER);
		enemyImage = enemy.getBattleIcon().getImage();
		enemyImageView = new ImageView(enemyImage);
		enemyImageView.setFitHeight(150);
		enemyImageView.setPreserveRatio(true);
		enemyBox.getChildren().add(enemyImageView);
		main.add(enemyBox, 1, 0);

		VBox playerInfo = new VBox();
		playerInfo.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		playerInfo.setAlignment(Pos.CENTER);
		playerInfo.getChildren().add(ph);
		playerInfo.getChildren().add(phInfo);
		playerInfo.getChildren().add(pm);
		playerInfo.getChildren().add(pmInfo);
		playerInfo.getChildren().add(xp);
		playerInfo.getChildren().add(level);

		main.add(playerInfo, 1, 1);

		VBox enemyInfo = new VBox();
		enemyInfo.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		enemyInfo.setAlignment(Pos.CENTER);
		enemyInfo.getChildren().add(eh);
		enemyInfo.getChildren().add(ehInfo);

		updateBars();
		main.add(enemyInfo, 0, 0);

		pane.setCenter(main);

	}

	/**
	 * ai to allow enemy to attack, and check if anyone has won
	 */
	private void doEnemyAttack()
	{
		Timeline atkDelay = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				if (!enemy.isDead())
				{

					Timeline atk = new Timeline(new KeyFrame(Duration.millis(66.666), new EventHandler<ActionEvent>()
					{

						@Override
						public void handle(ActionEvent event)
						{
							enemyImageView.setImage(enemy.getBattleIcon().getNextFrame());
						}
					}));
					atk.setCycleCount(enemy.getBattleIcon().getAmount());
					atk.play();
					enemy.playAttack();

					player.setHp(player.getHp() - enemy.getDamage());
					pane.setBottom(actions);
					player.setMana(player.getMana() + 5);
					updateBars();
					if (player.isDead())
					{
						// handle player lost
						Timeline die = new Timeline(
								new KeyFrame(Duration.millis(66.666), new EventHandler<ActionEvent>()
								{

									@Override
									public void handle(ActionEvent event)
									{
										playerImageView.setImage(player.getDeathIcon().getNextFrame());
									}
								}));
						die.setCycleCount(player.getDeathIcon().getAmount() - 1);
						die.play();
						player.playDeath();
						Timeline loose = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
						{

							@Override
							public void handle(ActionEvent event)
							{
								Alert a = new Alert(AlertType.WARNING);
								a.setContentText("You died");
								a.show();
								Main.reset();
							}
						}));
						loose.play();
						

					}
				} else
				{
					// handle player won
					System.out.println("You win");
					Music music = new Music("assets/audio/win.wav");
					music.play();
					enemy.playDeath();
					player.addXp(enemy.getXp());
					player.setHp(player.getMaxHP());
					updateBars();
					Timeline die = new Timeline(new KeyFrame(Duration.millis(66.666), new EventHandler<ActionEvent>()
					{

						@Override
						public void handle(ActionEvent event)
						{
							enemyImageView.setImage(enemy.getDeathIcon().getNextFrame());
						}
					}));
					die.setCycleCount(enemy.getDeathIcon().getAmount() - 1);
					die.play();
					if (enemy.isBoss())
					{

						Timeline win = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
						{

							@Override
							public void handle(ActionEvent event)
							{
								Alert a = new Alert(AlertType.CONFIRMATION);
								a.setContentText("You Win!!!");
								a.show();
								Main.reset();
							}
						}));
						win.play();

					} else
					{
						goBack();
					}
				}
			}
		}));
		atkDelay.play();
	}

	/**
	 * returns the pane holding teh battle
	 * @return the pane
	 */
	public Node getPane()
	{
		return pane;
	}

	/**
	 * adds the button, and action listeners to the pane
	 */
	private void setUpAttacksActions()
	{
		actions = new GridPane();
		for (int i = 0; i < 2; i++)
		{
			RowConstraints rc = new RowConstraints();
			rc.setVgrow(Priority.ALWAYS);
			rc.setFillHeight(true);
			actions.getRowConstraints().add(rc);
		}
		for (int i = 0; i < 2; i++)
		{
			ColumnConstraints cc = new ColumnConstraints();
			cc.setHgrow(Priority.ALWAYS);
			cc.setFillWidth(true);
			actions.getColumnConstraints().add(cc);
		}
		Button atk = new Button("attack");
		atk.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		actions.add(atk, 0, 0);
		atk.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				pane.setBottom(null);
				enemy.setHp(enemy.getHp() - player.getDamage());
				updateBars();
				player.playAttack();
				Timeline atk = new Timeline(new KeyFrame(Duration.millis(66.666), new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent event)
					{
						playerImageView.setImage(player.getBattleIcon().getNextFrame());
					}
				}));
				atk.setCycleCount(player.getBattleIcon().getAmount());
				atk.play();

				doEnemyAttack();
			}
		});
		Button run = new Button("attempt to run");
		run.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		actions.add(run, 1, 1);
		pane.setBottom(actions);
		run.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				pane.setBottom(null);
				if (player.getLvl() > enemy.getLvl())
				{

					Timeline atk = new Timeline(new KeyFrame(Duration.millis(66.666), new EventHandler<ActionEvent>()
					{

						@Override
						public void handle(ActionEvent event)
						{
							player.setLeft();
							playerImageView.setImage(player.getMapIcon().getNextFrame());
						}
					}));
					atk.setCycleCount(player.getMapIcon().getAmount());
					atk.play();
					goBack();

				} else
				{
					doEnemyAttack();
				}
			}
		});
		Button heal = new Button("heal");
		heal.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		actions.add(heal, 0, 1);
		heal.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				pane.setBottom(null);
				if (player.canCastHeal())
				{
					player.playSpell();
					Timeline atk = new Timeline(new KeyFrame(Duration.millis(66.666), new EventHandler<ActionEvent>()
					{

						@Override
						public void handle(ActionEvent event)
						{
							playerImageView.setImage(player.getSpellIcon().getNextFrame());
						}
					}));
					atk.setCycleCount(player.getSpellIcon().getAmount());
					atk.play();
					player.setHp(player.getHp() + player.getHealAmnt());
					player.setMana(player.getMana() - player.getHealCost());
					updateBars();
				}
				doEnemyAttack();
			}
		});
		Button spell = new Button("spell");
		spell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		actions.add(spell, 1, 0);
		spell.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				pane.setBottom(null);
				if (player.canCast())
				{
					player.playSpell();
					Timeline atk = new Timeline(new KeyFrame(Duration.millis(66.666), new EventHandler<ActionEvent>()
					{
						@Override
						public void handle(ActionEvent event)
						{
							playerImageView.setImage(player.getSpellIcon().getNextFrame());
						}
					}));
					atk.setCycleCount(player.getSpellIcon().getAmount());
					atk.play();
					enemy.setHp(enemy.getHp() - player.getSpellDmg());
					player.setMana(player.getMana() - player.getSpellCost());
					updateBars();

				}
				doEnemyAttack();
			}
		});
	}

	/**
	 * updates all stat bars for player and enemy
	 */
	private void updateBars()
	{
		ph.setProgress((double) player.getHp() / player.getMaxHP());
		eh.setProgress((double) enemy.getHp() / enemy.getMaxHP());
		pm.setProgress((double) player.getMana() / player.getMaxMana());
		xp.setProgress((double) player.getXp() / (player.getLvl() * 10.0));
		phInfo.setText("Health: " + player.getHp() + " / " + player.getMaxHP());
		ehInfo.setText(enemy.getName() + "'s health: " + enemy.getHp() + " / " + enemy.getMaxHP());
		pmInfo.setText("Mana: " + player.getMana() + " / " + player.getMaxMana());
		level.setText("Level: " + player.getLvl() + ": " + player.getXp() + " / " + (player.getLvl() * 10));
	}

	/**
	 * returns to the map
	 */
	private void goBack()
	{
		Timeline goBack = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{

				try
				{
					Map m;
					m = new Map(player, lastLevel);
					Main.getPane().setCenter(m.getPane());
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}));
		goBack.play();
	}
}
