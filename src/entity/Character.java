
package entity;

import Audio.Music;
/**
 * a class for the character
 *
 */
public class Character
{
	private String name;
	private int maxHP;
	private int hp;
	private int damage;
	// private String file;
	private int lvl;
	private int xp;
	private AnimatedSprite mapIcon;
	private AnimatedSprite battleIcon;
	private AnimatedSprite deathIcon;
	private String baseFile;
	private boolean isBoss = false;
	private Music atkSound;
	private Music deathSound;

	/**
	 * constructor for character
	 * @param name name
	 * @param maxHP max hp
	 * @param damage damage
	 * @param lvl level
	 * @param xp xp
	 * @param file file for the animated sprite
	 * @param frames amount of frames in the sprite
	 * @param fileBattle file for the sprite to be used in battle
	 * @param battleFrames frames for the battle sprite
	 * @param fileDeath file for the death sprite
	 * @param deathFrames frames in the death sprite
	 * @param atk sound file for attacking
	 * @param death sound file for dying
	 */
	public Character(String name, int maxHP, int damage, int lvl, int xp, String file, int frames, String fileBattle,
			int battleFrames, String fileDeath, int deathFrames, String atk, String death)
	{
		this.name = name;
		this.maxHP = maxHP;
		this.hp = maxHP;
		this.damage = damage;
		this.lvl = lvl;
		this.xp = xp;
		this.baseFile = file.substring(0, file.lastIndexOf('.'));
		this.mapIcon = new AnimatedSprite(file, frames);
		this.battleIcon = new AnimatedSprite(fileBattle, battleFrames);
		this.deathIcon = new AnimatedSprite(fileDeath, deathFrames);
		this.atkSound = new Music(atk);
		this.deathSound = new Music(death);
	}

	/**
	 * gets hp
	 * @return current hp
	 */
	public int getHp()
	{
		return hp;
	}

	/**
	 * sets hp
	 * @param hp hp to set
	 */
	public void setHp(int hp)
	{
		this.hp = hp;
		if (this.hp < 0)
		{
			this.hp = 0;
		}
		if (this.hp > maxHP)
		{
			this.hp = maxHP;
		}
	}

	/**
	 * gets damage
	 * @return damage
	 */
	public int getDamage()
	{
		return damage;
	}

	/**
	 * sets damage
	 * @param damage new damage
	 */
	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	/**
	 * gets name
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * gets max hp
	 * @return max hp
	 */
	public int getMaxHP()
	{
		return maxHP;
	}

	/**
	 * checks if player is dead
	 * @return true if pplayer is dead
	 */
	public boolean isDead()
	{
		return hp <= 0;
	}

	/**
	 * gets the map image
	 * @return map sprite
	 */
	public AnimatedSprite getMapIcon()
	{
		return mapIcon;
	}

	/**
	 * gets battle image
	 * @return battle sprite
	 */
	public AnimatedSprite getBattleIcon()
	{
		return battleIcon;
	}

	/**
	 * gets death image
	 * @return death sprite
	 */
	public AnimatedSprite getDeathIcon()
	{
		return deathIcon;
	}

	/**
	 * gets level
	 * @return level
	 */
	public int getLvl()
	{
		return lvl;
	}

	/**
	 * increases level by 1, also updates stats
	 */
	public void addLvl()
	{
		this.lvl += 1;
		this.maxHP += 20;
		this.damage += 2;
	}

	/**
	 * adds a certain amount of levels
	 * @param amount the levels to add
	 */
	public void addLvl(int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			addLvl();
		}
	}

	/**
	 * gets xp
	 * @return xp
	 */
	public int getXp()
	{
		return xp;
	}

	/**
	 * adds xp, and levels up if needed
	 * @param xp xp to add
	 * @return amount of levels increased
	 */
	public int addXp(int xp)
	{
		this.xp += xp;
		int levles = 0;
		while (this.xp >= 10 * lvl)
		{
			this.xp -= 10 * lvl;
			addLvl();
			levles++;
		}
		return levles;
	}

	/**
	 * sets direction left
	 */
	public void setLeft()
	{
		double height = mapIcon.getHeight();
		double width = mapIcon.getWidth();
		mapIcon.setImage(baseFile + "Left.png");
		mapIcon.setSize(width, height);
	}
	/**
	 * sets direction right
	 */
	public void setRight()
	{
		double height = mapIcon.getHeight();
		double width = mapIcon.getWidth();
		mapIcon.setImage(baseFile + "Right.png");
		mapIcon.setSize(width, height);
	}
	/**
	 * sets direction up
	 */
	public void setUp()
	{
		double height = mapIcon.getHeight();
		double width = mapIcon.getWidth();
		mapIcon.setImage(baseFile + "Up.png");
		mapIcon.setSize(width, height);
	}
	/**
	 * sets direction down
	 */
	public void setDown()
	{
		double height = mapIcon.getHeight();
		double width = mapIcon.getWidth();
		mapIcon.setImage(baseFile + "Down.png");
		mapIcon.setSize(width, height);
	}

	/**
	 * sets character to be a boss or not
	 * @param ans is boss
	 */
	public void setBoss(Boolean ans)
	{
		isBoss = ans;
	}

	/**
	 * is a boss
	 * @return is a boss
	 */
	public boolean isBoss()
	{
		return isBoss;
	}

	/**
	 * plays the attack sound
	 */
	public void playAttack()
	{
		atkSound.reset();
		atkSound.play();
	}

	/**
	 * plays the death sound
	 */
	public void playDeath()
	{
		deathSound.reset();
		deathSound.play();
	}

}
