/**
 * Class that adds the instance of the five new Levels on the menu  and their instruction on how to play.
 * 
 * @version 1.0 4/12/2017
 * 
 * @author Enrique Javier
 * @author Luis Rivera
 * 
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class NewMemoryFrame extends MemoryFrame 
{

	public NewMemoryFrame() {
		super();
		JMenuBar menuBar = this.getJMenuBar();
		JMenu memoryMenu = null;
		JMenu helpMenu = null;


		for (int i = 0; i < menuBar.getMenuCount(); i++) {
			if (menuBar.getMenu(i).getText().equals("Memory")) {
				memoryMenu = menuBar.getMenu(i);
				break;
			}
		}
		for (int i = 0; i < menuBar.getMenuCount(); i++) {
			if (menuBar.getMenu(i).getText().equals("Help")) {
				helpMenu = menuBar.getMenu(i);
				break;
			}
		}


		/**
		 * ActionListener method for receiving user input
		 * 
		 */
		ActionListener menuHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(e.getActionCommand().equals("Flush Level")) newGame("flush");
					else if(e.getActionCommand().equals("Straight Level")) newGame("straight");
					else if(e.getActionCommand().equals("Combo Level")) newGame("combo");
					else if(e.getActionCommand().equals("New Equal Pair Level")) newGame("new equal pair");
					else if(e.getActionCommand().equals("New Rank Trio Level")) newGame("new rank trio");
					else if(e.getActionCommand().equals("How To Play New Levels")) showInstructions();

				} catch (IOException e2) {
					e2.printStackTrace(); throw new RuntimeException("IO ERROR");
				}
			} // ends actionPerformed method 
		};

		//Creates Item in menu
		JMenuItem newEqualPairLevelMenuItem = new JMenuItem("New Equal Pair Level");
		newEqualPairLevelMenuItem.addActionListener(menuHandler);
		memoryMenu.add(newEqualPairLevelMenuItem);

		JMenuItem newRankTrioLevelMenuItem = new JMenuItem("New Rank Trio Level");
		newRankTrioLevelMenuItem.addActionListener(menuHandler);
		memoryMenu.add(newRankTrioLevelMenuItem);

		JMenuItem flushLevelMenuItem = new JMenuItem("Flush Level");
		flushLevelMenuItem.addActionListener(menuHandler);
		memoryMenu.add(flushLevelMenuItem);

		JMenuItem straightLevelMenuItem = new JMenuItem("Straight Level");
		straightLevelMenuItem.addActionListener(menuHandler);
		memoryMenu.add(straightLevelMenuItem);

		JMenuItem comboLevelMenuItem = new JMenuItem("Combo Level");
		comboLevelMenuItem.addActionListener(menuHandler);
		memoryMenu.add(comboLevelMenuItem);

		// Creates Item in Help menu
		JMenuItem howToPlayNewLevels = new JMenuItem("How To Play New Levels");
		howToPlayNewLevels.addActionListener(menuHandler);
		helpMenu.add(howToPlayNewLevels);
	}

	/**
	 * Prepares a new game (first game or non-first game)
	 * @throws IOException 
	 */

	public void newGame(String difficultyMode) throws IOException
	{
		// Reset the turn counter label
		this.getTurnCounterLabel().reset();

		// make a new card field with cards, and add it to the window
		// check if Flush Level is selected
		if(difficultyMode.equalsIgnoreCase("flush")) {
			this.setGameLevel(new FlushLevel(this.getTurnCounterLabel(), this));
			this.getLevelDescriptionLabel().setText("Flush Level");
			this.getTurnCounterLabel().reset();

			// clear out the content pane (removes turn counter label and card field)
			BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
			this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
			this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

			// show the window (in case this is the first game)
			this.setVisible(true);

		}else  //check if Straight Level is selected
			if(difficultyMode.equalsIgnoreCase("straight")) {
				this.setGameLevel(new StraightLevel(this.getTurnCounterLabel(), this));
				this.getLevelDescriptionLabel().setText("Straight Level");
				this.getTurnCounterLabel().reset();

				// clear out the content pane (removes turn counter label and card field)
				BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
				this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
				this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

				// show the window (in case this is the first game)
				this.setVisible(true);

			}else  //check if Straight Level is selected
				if(difficultyMode.equalsIgnoreCase("combo")) {
					this.setGameLevel(new ComboLevel(this.getTurnCounterLabel(), this));
					this.getLevelDescriptionLabel().setText("Combo Level");
					this.getTurnCounterLabel().reset();

					// clear out the content pane (removes turn counter label and card field)
					BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
					this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
					this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

					// show the window (in case this is the first game)
					this.setVisible(true);
				}else  //check if Straight Level is selected
					if(difficultyMode.equalsIgnoreCase("new equal pair")) {
						this.setGameLevel(new NewEqualsPairLevel(this.getTurnCounterLabel(), this));
						this.getLevelDescriptionLabel().setText("New Equal Pair Level");
						this.getTurnCounterLabel().reset();

						// clear out the content pane (removes turn counter label and card field)
						BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
						this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
						this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

						// show the window (in case this is the first game)
						this.setVisible(true);
					}else  //check if Straight Level is selected
						if(difficultyMode.equalsIgnoreCase("new rank trio")) {
							this.setGameLevel(new NewRankTrioLevel(this.getTurnCounterLabel(), this));
							this.getLevelDescriptionLabel().setText("New Rank Trio Level");
							this.getTurnCounterLabel().reset();

							// clear out the content pane (removes turn counter label and card field)
							BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
							this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
							this.getContentPane().add(showCardDeck(), BorderLayout.CENTER);

							// show the window (in case this is the first game)
							this.setVisible(true);
						}

						else 
						{
							super.newGame(difficultyMode);
						}
	}


	/**
	 * Method for displaying the instructions on how to play the new levels
	 * 
	 */
	private void showInstructions()
	{
		dprintln("MemoryGame.showInstructions()");
		final String HOWTOPLAYTEXT = 
				"FLUSH LEVEL\r\n"
						+ "The game consists of a grid of distinct cards.  At the start of the game,"+
						"every card is face down. \r\n" +  ""
						+ "The object is to find all the quintent \r\n"+
						"of cards with the same suit and turn them face up. \r\n"+
						"\r\n"+
						"Click on five cards to turn them face up. If all the cards have the \r\n"+
						"same suit, then you have discovered a flush.  The flush will remain\r\n"+
						"turned up.  If the cards are different, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the flushes. The game\r\n"+
						"is won when all cards are face up. \r\n\r\n"+
						"STRAIGHT LEVEL\r\n"
						+ "The game consists of a grid of distinct cards.  At the start of the game,"+
						"every card is face down.\r\n" + "" 
						+ "The object is to find quintents \r\n"+
						"of cards with the ascending ranks and turn them face up.\r\n"+
						"\r\n"+
						"Click on five cards to turn them face up. If all the cards can be put \r\n"+
						"in ascending ranks, then you have discovered a straight. The straight will remain\r\n"+
						"turned up.  If the cards cannot be put in ascending ranks, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the straights. The game\r\n"+
						"is won when all cards are face up.\r\n\r\n" + 
						"COMBO LEVEL \r\n" + "The object of the game is to find Flushes, Straights or Royal Flushes \r\n"
						+"and obtain the highest score possible. After you pick the fifth card \r\n"+
						"you will be ask what hand you are trying to build. \r\n"+ 
						"If you pick a hand and the combination of cards does not satisfy the requirements, you will lose five points.\r\n" + 
						"You can select the 'Pass' option from the drop-down menu to not lose points and start a fresh hand. ";

		JOptionPane.showMessageDialog(this, HOWTOPLAYTEXT
				, "How To Play", JOptionPane.PLAIN_MESSAGE);
	}

}