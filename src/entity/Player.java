
package entity;

import Audio.Music;

/**
 * class for the player
 *
 */
public class Player extends Character
{
	private int maxMana;
	private int mana;
	private int spellDmg;
	private int spellCost;
	private int healAmnt;
	private int healCost;
	private AnimatedSprite spellIcon;
	private Music spellSound;

	/**
	 * constructor for player
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
	 * @param mana max mana
	 * @param spellDmg spell damage
	 * @param spellCost spell cost
	 * @param healAmnt healing amount
	 * @param healCost healing spell cost
	 * @param fileSpell spell animation file
	 * @param spellFrames spell animation frame amount
	 * @param spell spell sound file
	 */
	public Player(String name, int maxHP, int damage, int lvl, int xp, String file, int frames, String fileBattle,
			int battleFrames, String fileDeath, int deathFrames, String atk, String death, int mana, int spellDmg,
			int spellCost, int healAmnt, int healCost, String fileSpell, int spellFrames, String spell)
	{
		super(name, maxHP, damage, lvl, xp, file, frames, fileBattle, battleFrames, fileDeath, deathFrames, atk, death);
		this.maxMana = mana;
		this.mana = mana;
		this.spellDmg = spellDmg;
		this.spellCost = spellCost;
		this.healAmnt = healAmnt;
		this.healCost = healCost;
		this.spellIcon = new AnimatedSprite(fileSpell, spellFrames);
		this.spellSound = new Music(spell);
	}

	/**
	 * gets mana
	 * @return mana
	 */
	public int getMana()
	{
		return mana;
	}

	/**
	 * sets mana
	 * @param mana new mana
	 */
	public void setMana(int mana)
	{
		this.mana = mana;
		if (this.mana > maxMana)
		{
			this.mana = maxMana;
		}
		if (this.mana < 0)
		{
			this.mana = 0;
		}
	}

	/**
	 * gets spell damage
	 * @return spell damage
	 */
	public int getSpellDmg()
	{
		return spellDmg;
	}

	/**
	 * sets spell damage
	 * @param spellDmg new damage
	 */
	public void setSpellDmg(int spellDmg)
	{
		this.spellDmg = spellDmg;
	}

	/**
	 * gets spell cost
	 * @return spell cost
	 */
	public int getSpellCost()
	{
		return spellCost;
	}

	/**
	 * sets spell cost
	 * @param spellCost new spell cost
	 */
	public void setSpellCost(int spellCost)
	{
		this.spellCost = spellCost;
	}

	/**
	 * get heal amount
	 * @return amount to heal
	 */
	public int getHealAmnt()
	{
		return healAmnt;
	}

	/**
	 * sets heal amount
	 * @param healAmnt amount to heal
	 */
	public void setHealAmnt(int healAmnt)
	{
		this.healAmnt = healAmnt;
	}

	/**
	 * get cost of healing
	 * @return heal cost
	 */
	public int getHealCost()
	{
		return healCost;
	}

	/**
	 * sets heal cost
	 * @param healCost new heal cost
	 */
	public void setHealCost(int healCost)
	{
		this.healCost = healCost;
	}

	/**
	 * get max mana
	 * @return max mana
	 */
	public int getMaxMana()
	{
		return maxMana;
	}

	/**
	 * sets maximum mana
	 * @param maxMana new max mana
	 */
	public void setMaxMana(int maxMana)
	{
		this.maxMana = maxMana;
	}

	/**
	 * chekcs if player can cast
	 * @return if they can cast
	 */
	public boolean canCast()
	{
		return mana >= spellCost;
	}

	/**
	 * checks if player can cast healing spell
	 * @return if they can cast
	 */
	public boolean canCastHeal()
	{
		return mana >= healCost;
	}

	/**
	 * adds xp
	 * @param xp the xp to add
	 */
	public int addXp(int xp)
	{
		int levels = super.addXp(xp);
		this.maxMana += 10 * levels;
		this.spellDmg += 5 * levels;
		this.healAmnt += 5 * levels;
		if (levels > 0)
		{
			this.mana = this.maxMana;
		}
		return levels;
	}

	/**
	 * gets the casting sprite
	 * @return casting sprite
	 */
	public AnimatedSprite getSpellIcon()
	{
		return spellIcon;
	}

	/**
	 * plays spell sound
	 */
	public void playSpell()
	{
		spellSound.reset();
		spellSound.play();
	}
}
